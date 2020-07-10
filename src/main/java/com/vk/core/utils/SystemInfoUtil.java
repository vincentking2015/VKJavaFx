package com.vk.core.utils;

import com.vk.utils.Config;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

/**
 * 作者:   王恒
 * Author:VINCE
 * 日期:   2020-07-06
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
public class SystemInfoUtil {
    /**
     * 获取Hosts文件路径
     */
    public static String getHostsFilePath(){
        String fileName = null;
        // 判断系统
        if ("linux".equalsIgnoreCase(System.getProperty("os.name"))) {
            fileName = "/etc/hosts";
        } else {
            fileName = "C://WINDOWS//system32//drivers//etc//hosts";
        }
        LogUtil.info("获取hosts文件路径:"+fileName);
        return fileName;
    }

    /**
     * 判断系统是否为windows
     */
    public static boolean getIsWindows(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
            return true;
        }
        return false;
    }


    /**
     * @Title: initSystemLocal
     * @Description: 初始化本地语言
     */
    public static void initSystemLocal() {
        try {
            String localeString = Config.get(Config.Keys.Locale, "");
            if (StringUtils.isNotEmpty(localeString)) {
                String[] locale1 = localeString.split("_");
                Config.defaultLocale = new Locale(locale1[0], locale1[1]);
            }
        } catch (Exception e) {
            LogUtil.error("初始化本地语言失败"+ e);
        }
    }


    /**
     * 插件管理，二期开发
     * @Title: addJarByLibs
     * @Description: 添加libs中jar包到系统中
     */
    public static void addJarByLibs() {
//        try {
//            // 系统类库路径
//            File libPath = new File("libs/");
//            // 获取所有的.jar和.zip文件
//            File[] jarFiles = libPath.listFiles(
//                    (dir, name) -> name.endsWith(".jar")
//            );
//            if (jarFiles != null) {
//                for (File file : jarFiles) {
//                    if (!PluginManageService.isPluginEnabled(file.getName())) {
//                        continue;
//                    }
//                    addJarClass(file);
//                }
//            }
//            PluginManager.getInstance().loadLocalPlugins();
//        } catch (Exception e) {
//            LogUtil.error("添加libs中jar包到系统中异常:"+ e);
//        }
    }

    /**
     * @Title: addJarClass
     * @Description: 添加jar包到系统中
     */
    public static void addJarClass(File jarFile) {
        try {
            LogUtil.info("Reading lib file: " + jarFile.getName());
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true); // 设置方法的访问权限
            // 获取系统类加载器
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            URL url = jarFile.toURI().toURL();
            method.invoke(classLoader, url);
        } catch (Exception e) {
            LogUtil.error("添加libs中jar包到系统中异常:"+e);
        }
    }
}
