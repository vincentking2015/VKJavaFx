package com.vk.service.index;

import com.vk.controller.index.SystemSettingController;
import com.vk.core.javafx.FxApp;
import com.vk.core.javafx.dialog.FxDialog;
import javafx.scene.control.ButtonType;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class SystemSettingService {
    public static void openSystemSettings(String title) {

        FxDialog<SystemSettingController> dialog = new FxDialog<SystemSettingController>()
                .setTitle(title)
                .setBodyFxml("/com/vk/fxml/index/SystemSetting.fxml")
                .setOwner(FxApp.primaryStage)
                .setButtonTypes(ButtonType.OK, ButtonType.CANCEL);

        SystemSettingController controller = dialog.show();

        dialog
                .setButtonHandler(ButtonType.OK, (actionEvent, stage) -> {
                    controller.applySettings();
                    stage.close();
                })
                .setButtonHandler(ButtonType.CANCEL, (actionEvent, stage) -> stage.close());
    }
}
