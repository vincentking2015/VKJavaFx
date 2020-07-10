package com.vk.core.utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

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
public class EnumUtil {
    /**
     * 将枚举类的所有值转化为 KeyValue 列表
     */
    public static <T extends Enum<T>> List<KeyValue<String, T>> toKeyValueList(Class<T> enumType) {
        return toKeyValueList(enumType, Enum::name);
    }

    /**
     * 将枚举类的所有值转化为 KeyValue 列表
     */
    public static <T extends Enum<T>> List<KeyValue<String, T>> toKeyValueList(
            Class<T> enumType, Function<T, String> toString
    ) {
        List<KeyValue<String, T>> list = new ArrayList<>();
        EnumSet.allOf(enumType).forEach(value -> list.add(new KeyValue<>(toString.apply(value), value)));
        return list;
    }

    /**
     * 将枚举类的指定值转化为 KeyValue 列表
     */
    public static <T extends Enum<T>> List<KeyValue<String, T>> toKeyValueList(T... values) {
        List<KeyValue<String, T>> list = new ArrayList<>();
        Stream.of(values).forEach(value -> list.add(new KeyValue<>(value.name(), value)));
        return list;
    }
}
