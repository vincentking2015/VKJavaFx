package com.vk.core.utils;

import com.vk.core.exception.VKException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

import java.io.IOException;

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
public class FxmlUtil {
    public static FXMLLoader loadFxmlFromResource(String resourcePath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    FxmlUtil.class.getResource(resourcePath)
            );
            fxmlLoader.load();
            return fxmlLoader;
        } catch (IOException e) {
            throw new VKException(e);
        }
    }


    /**
     * Load fxml without throwing exceptions
     */
    public static Parent load(String source) {
        try {
            return FXMLLoader.load(FxmlUtil.class.getResource(source));
        } catch (IOException e) {
            LogUtil.error(""+ e);
            new Alert(Alert.AlertType.ERROR, "Unable to load " + source + "\n" + e.toString(), ButtonType.OK).show();
            return new Pane();
        }
    }
}
