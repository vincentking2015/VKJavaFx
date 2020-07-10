package com.vk;

import com.jfoenix.controls.JFXDecorator;
import com.vk.controller.IndexController;
import com.vk.controller.MainController;
import com.vk.core.javafx.FxApp;
import com.vk.core.javafx.dialog.FxAlerts;
import com.vk.core.javafx.helper.FxmlHelper;
import com.vk.core.utils.JavaFxViewUtil;
import com.vk.core.utils.StageUtils;
import com.vk.core.utils.SystemInfoUtil;
import com.vk.utils.Config;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ResourceBundle;

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
public class Main extends Application {

    public static final String LOGO_PATH = "/images/icon.jpg";

    public static final ResourceBundle RESOURCE_BUNDLE =
            ResourceBundle.getBundle("locale.Menu", Config.defaultLocale);

    private static Stage stage;

    public static void main(String[] args) {

        SystemInfoUtil.initSystemLocal();    // 初始化本地语言
        SystemInfoUtil.addJarByLibs();       // 添加外部jar包,方法内为空

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        // 初始化 JavaFX 全局设置
        FxApp.init(primaryStage, LOGO_PATH);
        FxApp.setupIcon(primaryStage);
        FxApp.styleSheets.add(Main.class.getResource("/css/jfoenix-main.css").toExternalForm());
        FxApp.styleSheets.add(getClass().getResource("/css/defaultTheme.css").toExternalForm());

        primaryStage.setResizable(true);
        primaryStage.setTitle(RESOURCE_BUNDLE.getString("Title") + Config.xJavaFxToolVersions);
        primaryStage.setOnCloseRequest(this::confirmExit);

        FXMLLoader fXMLLoader = new FXMLLoader(MainController.class.getResource("/com/vk/fxml/Main.fxml"));
        Parent root = fXMLLoader.load();
        primaryStage.setScene(new Scene(root));

        StageUtils.loadPrimaryStageBound(primaryStage);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    private void loadNewUI(Stage primaryStage) {
        FxmlHelper.loadIntoStage("/com/vk/fxml/newui/main.fxml", primaryStage).show();
    }

    private void loadClassicUI(Stage primaryStage) throws IOException {
        FXMLLoader fXMLLoader = IndexController.getFXMLLoader();
        Parent root = fXMLLoader.load();

        JFXDecorator decorator = JavaFxViewUtil.getJFXDecorator(
                primaryStage,
                RESOURCE_BUNDLE.getString("Title") + Config.xJavaFxToolVersions,
                LOGO_PATH,
                root
        );
        decorator.setOnCloseButtonAction(() -> confirmExit(null));

        Scene scene = JavaFxViewUtil.getJFXDecoratorScene(decorator);
        primaryStage.setScene(scene);
    }


    private void confirmExit(Event event) {
        if (Config.getBoolean(Config.Keys.ConfirmExit, true)) {
            if (FxAlerts.confirmYesNo("退出应用", "确定要退出吗？")) {
                doExit();
            } else if (event != null) {
                event.consume();
            }
        } else {
            doExit();
        }
    }

    private void doExit() {
        StageUtils.savePrimaryStageBound(stage);
        Platform.exit();
        System.exit(0);
    }

}

