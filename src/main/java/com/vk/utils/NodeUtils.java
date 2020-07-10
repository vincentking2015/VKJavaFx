package com.vk.utils;

import javafx.scene.Node;
import javafx.stage.Window;

import java.util.HashMap;
import java.util.Map;

public class NodeUtils {
    public static <T> T getUserData(Node node, String key) {
        return node.getUserData() == null ? null :
                !(node.getUserData() instanceof Map) ? null :
                        (T) ((Map<String, Object>) node.getUserData()).get(key);
    }

    public static void setUserData(Node node, String key, Object value) {
        Map<String, Object> map = (Map<String, Object>) node.getUserData();
        if (map == null) {
            map = new HashMap<>();
            node.setUserData(map);
        }
        map.put(key, value);
    }

    public static <T> T getUserData(Window window, String key) {
        return window.getUserData() == null ? null :
                !(window.getUserData() instanceof Map) ? null :
                        (T) ((Map<String, Object>) window.getUserData()).get(key);
    }

    public static void setUserData(Window window, String key, Object value) {
        Map<String, Object> map = (Map<String, Object>) window.getUserData();
        if (map == null) {
            map = new HashMap<>();
            window.setUserData(map);
        }
        map.put(key, value);
    }
}
