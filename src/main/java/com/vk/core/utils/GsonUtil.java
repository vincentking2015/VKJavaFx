package com.vk.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;


import java.io.StringReader;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class GsonUtil {
    private GsonUtil() {
    }

    // 日志 TAG
    private static final String TAG = GsonUtil.class.getSimpleName();
    // Object 转 JSON 字符串
    private static final Gson TO_GSON = createGson(true).create();
    // JSON 字符串转 T Object
    private static final Gson FROM_GSON = createGson(true).create();
    // JSON 缩进
    private static final Gson INDENT_GSON = createGson(true).setPrettyPrinting().create();

    // ============
    // = 转换方法 =
    // ============

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @return JSON String
     */
    public static String toJson(final Object object) {
        return toJson(object, TO_GSON);
    }

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @param gson   {@link Gson}
     * @return JSON String
     */
    public static String toJson(final Object object, final Gson gson) {
        if (gson != null) {
            try {
                return gson.toJson(object);
            } catch (Exception e) {
                LogUtil.info(e.getMessage());
                LogUtil.info(e.getMessage());
            }
        }
        return null;
    }

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param <T>      泛型
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Class<T> classOfT) {
        return fromJson(json, classOfT, FROM_GSON);
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param gson     {@link Gson}
     * @param <T>      泛型
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Class<T> classOfT, final Gson gson) {
        if (gson != null) {
            try {
                return gson.fromJson(json, classOfT);
            } catch (Exception e) {
                LogUtil.info(e.getMessage());
            }
        }
        return null;
    }

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json    JSON String
     * @param typeOfT {@link Type} T
     * @param <T>     泛型
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Type typeOfT) {
        return fromJson(json, typeOfT, FROM_GSON);
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json    JSON String
     * @param typeOfT {@link Type} T
     * @param gson    {@link Gson}
     * @param <T>     泛型
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Type typeOfT, final Gson gson) {
        if (gson != null) {
            try {
                return gson.fromJson(json, typeOfT);
            } catch (Exception e) {
                LogUtil.info(e.getMessage());
            }
        }
        return null;
    }

    // ============
    // = 其他方法 =
    // ============

    /**
     * 判断字符串是否 JSON 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isJSON(final String json) {
        JsonElement jsonElement;
        try {
            jsonElement = JsonParser.parseString(json);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @return JSON String
     */
    public static String toJsonIndent(final String json) {
        return toJsonIndent(json, INDENT_GSON);
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @param gson {@link Gson}
     * @return JSON String
     */
    public static String toJsonIndent(final String json, final Gson gson) {
        if (gson != null) {
            try {
                JsonReader reader = new JsonReader(new StringReader(json));
                reader.setLenient(true);
                JsonElement jsonElement = JsonParser.parseReader(reader);
                return gson.toJson(jsonElement);
            } catch (Exception e) {
                LogUtil.info(e.getMessage());
            }
        }
        return null;
    }

    // =

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param object {@link Object}
     * @return JSON String
     */
    public static String toJsonIndent(final Object object) {
        return toJsonIndent(object, INDENT_GSON);
    }

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param object {@link Object}
     * @param gson   {@link Gson}
     * @return JSON String
     */
    public static String toJsonIndent(final Object object, final Gson gson) {
        if (gson != null) {
            try {
                return gson.toJson(object);
            } catch (Exception e) {
                LogUtil.info(e.getMessage());
            }
        }
        return null;
    }

    // ========
    // = Gson =
    // ========

    /**
     * 创建 GsonBuilder
     * @param serializeNulls 是否序列化 null 值
     * @return {@link GsonBuilder}
     */
    public static GsonBuilder createGson(final boolean serializeNulls) {
        GsonBuilder builder = new GsonBuilder();
        if (serializeNulls) {
            builder.serializeNulls();
        }
        return builder;
    }

    /**
     * 创建过滤指定修饰符字段 GsonBuilder
     * @param builder   {@link GsonBuilder}
     * @param modifiers 需过滤不处理的字段修饰符 {@link Modifier}
     * @return {@link GsonBuilder}
     */
    public static GsonBuilder createGsonExcludeFields(final GsonBuilder builder, final int... modifiers) {
        if (builder != null) {
            return builder.excludeFieldsWithModifiers(modifiers);
        }
        return null;
    }

    // ========
    // = Type =
    // ========

    /**
     * 获取 Array Type
     * @param type Bean.class
     * @return Bean[] Type
     */
    public static Type getArrayType(final Type type) {
        return TypeToken.getArray(type).getType();
    }

    /**
     * 获取 List Type
     * @param type Bean.class
     * @return List<Bean> Type
     */
    public static Type getListType(final Type type) {
        return TypeToken.getParameterized(List.class, type).getType();
    }

    /**
     * 获取 Set Type
     * @param type Bean.class
     * @return Set<Bean> Type
     */
    public static Type getSetType(final Type type) {
        return TypeToken.getParameterized(Set.class, type).getType();
    }

    /**
     * 获取 Map Type
     * @param keyType   Key.class
     * @param valueType Value.class
     * @return Map<Bean> Type
     */
    public static Type getMapType(final Type keyType, final Type valueType) {
        return TypeToken.getParameterized(Map.class, keyType, valueType).getType();
    }

    /**
     * 获取 Type
     * @param rawType       raw type
     * @param typeArguments type arguments
     * @return Type
     */
    public static Type getType(final Type rawType, final Type... typeArguments) {
        return TypeToken.getParameterized(rawType, typeArguments).getType();
    }
}
