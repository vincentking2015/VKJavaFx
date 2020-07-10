package com.vk.core.utils;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

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
public class CacheUtil {
    private static final String LOG = CacheUtil.class.getSimpleName();

    //定义一个静态的cache管理器
    private static CacheManager mCacheManager = null;

    private static net.sf.ehcache.Cache netCache = null;

    private volatile static CacheUtil mInstance = null;

    //
    private CacheUtil(){
        mCacheManager = CacheManager.create("./src/main/resources/conf/ehcache.xml");
        netCache = mCacheManager.getCache("VKCache");
    }

    //
    private CacheUtil(String cacheName){
        mCacheManager = CacheManager.create("./src/main/resources/conf/ehcache.xml");
        netCache = mCacheManager.getCache(cacheName);
    }

    /**
     * 获取默认的cache器
     * @return
     */
    public static CacheUtil getDefaultInstance(){
        if (null==mInstance){
            synchronized (CacheUtil.class){
                if (null==mInstance){
                    mInstance = new CacheUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取指定的cache器
     * @param cacheName
     * @return
     */
    public static CacheUtil getInstance(String cacheName) {

        if (null==mInstance){
            synchronized (CacheUtil.class){
                if (null==mInstance){
                    mInstance = new CacheUtil(cacheName);
                }
            }
        }
        return mInstance;
    }


    /**
     * 储存
     * @param key
     * @param value
     */
    public void put(String key,Object value){
        Element element = new Element(key, value);

        netCache.put(element);
    }

    /**
     * 获取值，觉得没啥用
     * @param key
     * @return
     */
    public Object get(String key){
        Element element = netCache.get(key);
        if (element==null){
            return null;
        }else {
            return element.getObjectValue();
        }
    }

    /**
     * 获取字符串的值
     * @param key
     * @return
     */
    public String getStringValue(String key){
        Object a = get(key);
        return get(key) instanceof String?(String) get(key):"";
    }

    /**
     * 获取带有默认值的字符串
     * @param key
     * @param defaultValue
     * @return
     */
    public String getStringValue(String key,String defaultValue){
        return get(key) instanceof String?(String) get(key):defaultValue;
    }

    /**
     * 获取bool类型的cache值
     * @param key
     * @return
     */
    public Boolean getBooleanValue(String key){
        return get(key) instanceof Boolean?(Boolean) get(key):false;
    }

    /**
     * 带有默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public Boolean getDefaultBooleanValue(String key,Boolean defaultValue){
        return get(key) instanceof Boolean?(Boolean) get(key):defaultValue;
    }

    /**
     *
     * @param key
     * @return
     */
    public int getIntValue(String key){
        return get(key) instanceof Integer?(int) get(key):0;
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public int getDefaulIntValue(String key,int defaultValue){
        return get(key) instanceof Integer?(int) get(key):defaultValue;
    }

    /**
     *
     * @param key
     * @return
     */
    public Double getDoubleValue(String key){
        return get(key) instanceof Integer?(Double) get(key):0;
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public Double getDefaulDoubleValue(String key,Double defaultValue){
        return get(key) instanceof Double?(int) get(key):defaultValue;
    }
}
