package com.vk.newui;

import com.vk.core.exception.VKException;
import com.vk.model.PluginJarInfo;
import com.vk.plugin.PluginLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 新界面中负责与插件操作相关的逻辑
 */
@Slf4j
public class NewLauncherService {

    private static final NewLauncherService instance = new NewLauncherService();

    private NewLauncherController newLauncherController;

    private Map<Tab, PluginJarInfo> jarInfoMap = new HashMap<>();

    private PluginItemController currentPluginItem;

    public static NewLauncherService getInstance() {
        return instance;
    }

    private NewLauncherService() {

    }

    public void setController(NewLauncherController newLauncherController) {
        this.newLauncherController = newLauncherController;
    }

    public void setCurrentPluginItem(PluginItemController currentPluginItem) {
        this.currentPluginItem = currentPluginItem;
    }

    public PluginItemController getCurrentPluginItem() {
        return currentPluginItem;
    }

    public void loadPlugin(PluginJarInfo pluginJarInfo) {
        log.info("加载插件 {}: {}", pluginJarInfo.getName(), pluginJarInfo.getFile().getAbsolutePath());

        TabPane tabPane = this.newLauncherController.getTabPane();

        for (Map.Entry<Tab, PluginJarInfo> entry : jarInfoMap.entrySet()) {
            if (entry.getValue() == pluginJarInfo) {
                tabPane.getSelectionModel().select(entry.getKey());
                return;
            }
        }

        Tab tab;
        String controllerType = pluginJarInfo.getControllerType();

        if (controllerType.equals("Node")) {
            tab = PluginLoader.loadIsolatedPluginAsTab(pluginJarInfo, tabPane);
        } else if (controllerType.equals("WebView")) {
            tab = PluginLoader.loadWebViewAsTab(pluginJarInfo, tabPane);
        } else {
            throw new VKException("找不到 controllerType=" + controllerType + " 的加载方式");
        }

        if (tab != null) {
            tab.setOnClosed(event -> this.jarInfoMap.remove(tab));
            jarInfoMap.put(tab, pluginJarInfo);
        }
    }


}
