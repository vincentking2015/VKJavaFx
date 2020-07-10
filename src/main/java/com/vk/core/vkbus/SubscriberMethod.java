package com.vk.core.vkbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作者:   王恒
 * Author:VINCE
 * 日期:   2020-06-24
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
public class SubscriberMethod {

    public Method method;

    public ThreadMode threadMode;

    public Class<?> eventType;

    public Object subscriber;

    public int code;

    public SubscriberMethod(Method method, ThreadMode threadMode, Class<?> eventType, Object subscriber, int code) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
        this.subscriber = subscriber;
        this.code = code;
    }


    public void invoke(Object o){
        try {
            Class<?>[] types = method.getParameterTypes();
            if (types.length==1){
                    method.invoke(subscriber,o);
            }else if(types.length==0){
                method.invoke(subscriber);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
