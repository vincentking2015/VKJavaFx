package com.vk.core.vksql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.core.utils.GsonUtil;
import com.vk.core.vksql.exception.VKSqlException;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * 作者:   王恒
 * Author:VINCE
 * 日期:   2020-06-19
 * Des:   VINCE 个人独创
 * <p>
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无bug             #
 * #                                                   #
 */

/**
 * 此工具类是桌面连接sqlite轻量级数据库
 * 是为了桌面本地持久化储存的解决方案之一
 */
public class SqliteUtil {

    private static Connection connection;

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public synchronized static Connection getConnection() throws SQLException {
        //如果 当前练
        if (connection == null) {
            try {
                String driverClass = "org.sqlite.JDBC";
                Class.forName(driverClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String url = "jdbc:sqlite:src/main/resources/db/vk.db";
            return connection = DriverManager.getConnection(url);
        } else {
            return connection;
        }
    }

    /**
     * 关闭连接
     */
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException("连接未开启！");
        }
    }

    /**
     * 程序第一次启动时创建相关的数据
     * @throws SQLException
     * @throws IOException
     */
    public static void createDatabases() throws SQLException, IOException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
//        statement.execute(DB_CONFIG);
//        statement.execute(DB_COOKIE);
//        statement.execute(DB_LINK);
//        statement.execute(DB_USER);
        close();
    }


    public <E extends VkModel> void createAllFromJson(Class<E> clazz, JSONArray json) {
        if (clazz == null || json == null) {
            return;
        }
        checkIfValid();

        for (int i = 0; i < json.size(); i++) {
            try {
                insertOrUpdateFromJson(json.getString(i),clazz);
            } catch (JSONException e) {
                throw new VKSqlException("无法识别是一个JSON", e);
            }
        }
    }

    private <E extends VkModel> void insertOrUpdateFromJson(String json, Class<E> clazz) {
        // 将json转换成object，然后存储
        E e = GsonUtil.fromJson(json, clazz);
        // 一个个储存

    }

    private void checkIfValid() {
        // 这个里面检测一些配置或者当前环境是否可以直接操作
        // 如果不能操作就抛出异常
    }

    protected static void checkClass(Class<? extends VkModel> clazz) {
        if (clazz == null) {
            throw new NullPointerException("A class extending RealmObject must be provided");
        }
    }


    protected static VKSqlException getMissingProxyClassException(Class<? extends VkModel> clazz) {
        return new VKSqlException(
                String.format("'%s' is not part of the schema for this Realm.", clazz.toString()));
    }


}
