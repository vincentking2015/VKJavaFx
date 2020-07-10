package com.vk.view;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTreeView;
import com.vk.model.ListMenuItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.ResourceBundle;

@Getter
@Setter
public abstract class MainView implements Initializable {

    protected ResourceBundle bundle;

    @FXML
    public ImageView logoImg;

    @FXML
    public Pane titlePane;

    @FXML
    public Label title;

    @FXML
    public Label path;

    @FXML
    public JFXHamburger hamburger;

    @FXML
    public Button closeBtn;

    @FXML
    public Button maxBtn;

    @FXML
    public Button minBtn;

    @FXML
    public JFXDrawer leftDrawer;

    @FXML
    public JFXTreeView<ListMenuItem> menuTree;

    @FXML
    public TabPane mTabPane;

}
