package com.vk.controller.index;

import com.vk.core.utils.LogUtil;
import com.vk.utils.Config;
import com.vk.utils.Config.Keys;
import com.vk.view.SystemSettingView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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
public class SystemSettingController extends SystemSettingView {
    private Stage newStage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    private void initView() {
        try {
            exitShowAlertCheckBox.setSelected(Config.getBoolean(Keys.ConfirmExit, true));
            addNotepadCheckBox.setSelected(Config.getBoolean(Keys.NotepadEnabled, true));
            saveStageBoundCheckBox.setSelected(Config.getBoolean(Keys.RememberWindowLocation, true));
            chkNewLauncher.setSelected(Config.getBoolean(Keys.NewLauncher, false));
        } catch (Exception e) {
            LogUtil.error("加载配置失败："+ e);
        }
    }

    public void applySettings() {
        try {
            Config.set(Keys.ConfirmExit, exitShowAlertCheckBox.isSelected());
            Config.set(Keys.NotepadEnabled, addNotepadCheckBox.isSelected());
            Config.set(Keys.RememberWindowLocation, saveStageBoundCheckBox.isSelected());
            Config.set(Keys.NewLauncher, chkNewLauncher.isSelected());

            if (newStage != null) {
                newStage.close();
            }
        } catch (Exception e) {
            LogUtil.error("保存配置失败："+ e);
        }
    }
}
