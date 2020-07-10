package com.vk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者:   王恒
 * Author:VINCE
 * 日期:   2020-07-07
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

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolFxmlLoaderConfiguration {
    /**
     * 资源url
     */
    private String url;

    /**
     * 国际化资源文件
     */
    private String resourceBundleName;

    /**
     * class名称
     */
    private String className;

    /**
     * 标题（配合国际化资源文件，无则默认显示原字符）
     */
    private String title;

    /**
     * 图标路径
     */
    private String iconPath;

    /**
     * 是否在启动时自动加载
     */
    private Boolean isDefaultShow = false;

    /**
     * 菜单id
     */
    private String menuId;

    /**
     * 菜单父id
     */
    private String menuParentId;

    /**
     * 是否为菜单
     */
    private Boolean isMenu = false;

    /**
     * 内容类型
     */
    private String controllerType = "Node";

}
