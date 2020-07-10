package com.vk.core.utils;

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
public class AttributeConvertUtil {
    /**
     * 获取XMl属性名
     * @param str
     * @return
     */
    public static String getAttributeNameByXml(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = str.split("_");
        for (int i = 0; i < strings.length; i++) {
            if (i == 0) {
                stringBuilder.append(Character.toLowerCase(strings[i].charAt(0)));
            } else {
                stringBuilder.append(Character.toUpperCase(strings[i].charAt(0)));
            }
            stringBuilder.append(strings[i].substring(1));
        }
        return stringBuilder.toString();
    }

    /**
     * 获取属性值
     * @param str
     * @return
     */
    public static String getAttributeSetNameByXml(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = str.split("_");
        for (int i = 0; i < strings.length; i++) {
            stringBuilder.append(Character.toUpperCase(strings[i].charAt(0)));
            stringBuilder.append(strings[i].substring(1));
        }
        return stringBuilder.toString();
    }

    /**
     * @Title: humpToLine
     * @Description: 驼峰转下横线写法互转
     */
    public static String humpToLine(String param) {
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(param.charAt(0));
        for (int i = 1; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && Character.isLowerCase(param.charAt(i-1))) {
                sb.append("_");
            }
            sb.append(c);
        }
        return sb.toString().toUpperCase();
    }
}
