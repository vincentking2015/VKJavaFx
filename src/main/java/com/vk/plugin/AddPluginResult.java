package com.vk.plugin;

import com.vk.model.PluginJarInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPluginResult {

    private PluginJarInfo pluginJarInfo;

    private boolean newPlugin;
}
