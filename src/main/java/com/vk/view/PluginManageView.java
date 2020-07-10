package com.vk.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Map;
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
@Getter
@Setter
public class PluginManageView implements Initializable {
    @FXML
    protected TextField selectPluginTextField;

    @FXML
    protected Button selectPluginButton;

    @FXML
    protected Button addLocalPluginButton;

    @FXML
    protected TableView<Map<String, String>> pluginDataTableView;

    @FXML
    protected TableColumn<Map<String, String>, String> nameTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> synopsisTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> versionTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> isDownloadTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> isEnableTableColumn;

    @FXML
    protected TableColumn<Map<String, String>, String> downloadTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
