package com.vk.core.utils;

import javafx.scene.Node;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

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

/**
 * CSS属性操作
 */
public class CssProps {
    private Map<String, String> props = new HashMap<>();

    public CssProps put(String propName, String propValue) {
        if (isNotBlank(propName) && isNotBlank(propValue)) {
            this.props.put(propName, propValue);
        }
        return this;
    }

    public CssProps concat(CssProps cssProps) {
        CssProps result = new CssProps();
        result.props.putAll(this.props);
        result.props.putAll(cssProps.props);
        return result;
    }

    public void applyTo(Node node) {
        CssProps currentCssProps = CssProps.parse(node.getStyle());
        CssProps resultProps = currentCssProps.concat(this);
        node.setStyle(resultProps.toStyleString());
    }

    private String toStyleString() {
        return this.props.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining(";"));
    }

    public boolean isEmpty() {
        return this.props.isEmpty();
    }

    private static CssProps parse(String style) {
        CssProps cssProps = new CssProps();
        if (isNotBlank(style)) {
            Stream.of(style.split(";"))
                    .filter(StringUtils::isNotBlank)
                    .forEach(cssInstr -> {
                        String[] split = cssInstr.split(":");
                        cssProps.put(split[0].trim(), split[1].trim());
                    });
        }
        return cssProps;
    }
}
