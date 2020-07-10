package com.vk.controller;

import com.jfoenix.controls.JFXDecorator;
import com.vk.core.javafx.FxApp;
import com.vk.core.javafx.dialog.FxAlerts;
import com.vk.core.javafx.dialog.FxDialog;
import com.vk.core.javafx.dialog.FxProgressDialog;
import com.vk.core.javafx.dialog.ProgressTask;
import com.vk.core.utils.*;
import com.vk.core.vkbus.Subscribe;
import com.vk.core.vkbus.VkBus;
import com.vk.model.ListMenuItem;
import com.vk.utils.Config;
import com.vk.view.MainView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static javafx.stage.WindowEvent.WINDOW_SHOWN;

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
public class MainController extends MainView {

    ContextMenu contextMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //注册VkBus
        VkBus.getmInstance().register(this);


        initData();
        initView();
        initListener();
        initDashboard();
    }

    private void initDashboard() {
        //主页加入
        ListMenuItem item = new ListMenuItem();
        item.setHidden(false);
        item.setName("主      页");
        item.setPath("DashboardController");
        openTab(item);
    }


    /**
     * 控件事件监听初始化
     */
    private void initListener() {
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (FxAlerts.confirmYesNo("退出应用", "确定要退出吗？")) {
                    doExit();
                } else if (actionEvent != null) {
                    actionEvent.consume();
                }
            }
        });

        minBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FxApp.primaryStage.setIconified(true);
            }
        });

        maxBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!FxApp.primaryStage.isMaximized()){
                    FxApp.primaryStage.setMaximized(true);
                }else {
                    FxApp.primaryStage.setMaximized(false);
                }

            }
        });

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                leftDrawer.toggle();
            }
        });

        leftDrawer.setOnDrawerOpening((event) -> {
            //创建时间轴
            final Timeline timeline=new Timeline();
            timeline.setCycleCount(1);//设置周期运行循环往复
            //
            final KeyValue kv=new KeyValue(leftDrawer.prefWidthProperty(), 250);
            final KeyFrame kf=new KeyFrame(Duration.millis(200), kv);
            final KeyValue kv2=new KeyValue(titlePane.prefWidthProperty(), 190);
            final KeyFrame kf2=new KeyFrame(Duration.millis(200), kv2);
            //将关键帧加到时间轴中
            timeline.getKeyFrames().add(kf);
            timeline.getKeyFrames().add(kf2);
            timeline.play();//运行
            title.setVisible(true);

        });

        leftDrawer.setOnDrawerClosing(event -> {
            //创建时间轴
            final Timeline timeline=new Timeline();
            timeline.setCycleCount(1);//设置周期运行循环往复
            //
            final KeyValue kv=new KeyValue(leftDrawer.prefWidthProperty(), 0);
            final KeyFrame kf=new KeyFrame(Duration.millis(200), kv);
            final KeyValue kv2=new KeyValue(titlePane.prefWidthProperty(), 0);
            final KeyFrame kf2=new KeyFrame(Duration.millis(200), kv2);
            //将关键帧加到时间轴中
            timeline.getKeyFrames().add(kf);
            timeline.getKeyFrames().add(kf2);
            timeline.play();//运行
            title.setVisible(false);
        });


        menuTree.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                TreeItem<ListMenuItem> selectedItem = menuTree.getSelectionModel().getSelectedItem();
                if (selectedItem!=null){
                    if (selectedItem.isLeaf()){
                        //叶节点
                        openTab(selectedItem.getValue());
//                        LogUtil.info(selectedItem.valueProperty().get().getName());
                    }else {
                        menuTree.getSelectionModel().clearSelection();
                        selectedItem.setExpanded(!selectedItem.isExpanded());
                    }
                }
            }
        });

    }

    /**
     * 视图初始化
     */
    private void initView() {
        leftDrawer.open();
        logoImg.setImage(new Image(getClass().getResourceAsStream("/images/icon.jpg")));
        menuTree.setCellFactory(new Callback<TreeView<ListMenuItem>, TreeCell<ListMenuItem>>() {
            @Override
            public TreeCell<ListMenuItem> call(TreeView<ListMenuItem> menuItemTreeView) {
                return new TemlpCell();
            }
        });

        // 右键菜单===================================================================
        // 创建右键菜单
        contextMenu = new ContextMenu();

        // 菜单项
        MenuItem blackBg = new MenuItem("博客--浏览器");
        blackBg.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                WebView browser = new WebView();
                WebEngine webEngine = browser.getEngine();
                webEngine.load("http://blog.airable.club");

                Tab tab = new Tab("博客");

                tab.setContent(browser);
                mTabPane.getTabs().add(tab);
                mTabPane.getSelectionModel().select(tab);
            }
        });

        // 多级菜单项
        Menu otherBg = new Menu("更多的选项");
        // 二级菜单项
        MenuItem regBg = new MenuItem("弹出Alter框");
        regBg.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FxAlerts.info("温馨提示","这是ContextMenu弹出来的");
            }
        });
        // 二级菜单项
        MenuItem blueBg = new MenuItem("弹出Dialog框");
        blueBg.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FxProgressDialog.create(FxApp.primaryStage.getOwner(), new ProgressTask() {
                    @Override
                    protected void execute() throws Exception {
                        Thread.sleep(2000);
                    }
                },"进度条").showAndWait();
            }
        });
        otherBg.getItems().addAll(regBg, blueBg);

        // 将菜单项添加进右键菜单
        contextMenu.getItems().addAll(blackBg, otherBg);
        // 右键菜单===================================================================
        logoImg.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                Node node = contextMenuEvent.getPickResult().getIntersectedNode();
                contextMenu.show(node, Side.BOTTOM,0,0);
            }
        });
    }

    /**
     * 初始化一些数据
     */
    private void initData() {
        String router = FileUtil.readText(new File(getClass().getResource("/router/router.json").getPath()));
        ListMenuItem root = GsonUtil.fromJson(router, ListMenuItem.class);

        TreeItem<ListMenuItem> rootItem = buildMenu(root);
        menuTree.setShowRoot(false);
        menuTree.setRoot(rootItem);

    }

    /**
     * 左侧菜单栏
     * @param root
     * @return
     */
    private TreeItem<ListMenuItem> buildMenu(ListMenuItem root) {

        // TreeItem名字和图标
        TreeItem<ListMenuItem> rootItem = new TreeItem<> (root);
        rootItem.setExpanded(false);
        // 每个Item下又可以添加新的Item
        for (int i = 0; i < root.getChildren().size(); i++) {
            ListMenuItem item1 = root.getChildren().get(i);
            if (item1.getChildren()!=null&&item1.getChildren().size()>0){
                TreeItem<ListMenuItem> item = buildMenu(item1);
                rootItem.getChildren().add(item);
            }else {
                TreeItem<ListMenuItem> item = new TreeItem<> (root.getChildren().get(i));
                rootItem.getChildren().add(item);
            }
        }
        return rootItem;
    }

    /**
     * 退出的封装
     */
    private void doExit() {
        //注册RxBus
        VkBus.getmInstance().unRegister(this);

        Platform.exit();
        System.exit(0);
    }

    /**
     * 封装的VKBus，任何controller，任何线程都可以传递事件
     */
    @Subscribe(code = 1111)
    public void openTab(ListMenuItem data){
        try {
            FilteredList<Tab> filtered = mTabPane.getTabs().filtered(new Predicate<Tab>() {
                @Override
                public boolean test(Tab tab) {
                    return tab.getText().equals(data.getName());
                }
            });

            if (filtered.size()<=0) {
                Tab tab = new Tab(data.getName());
                if (StringUtils.isNotEmpty(data.getIcon())) {
                    ImageView imageView = new ImageView(new Image(data.getIcon()));
                    imageView.setFitHeight(18);
                    imageView.setFitWidth(18);
                    tab.setGraphic(imageView);
                }

                String packageName = getClass().getPackageName();
                Class<?> controller = Class.forName(packageName + "." + data.getPath());
                Method getFXMLLoader = controller.getDeclaredMethod("getFXMLLoader");
                FXMLLoader fXMLLoader = (FXMLLoader)getFXMLLoader.invoke(null);
                Parent root = fXMLLoader.load();

                tab.setContent(root);
                if ("主      页".equals(data.getName())){
                    tab.setClosable(false);
                }
                mTabPane.getTabs().add(tab);
                mTabPane.getSelectionModel().select(tab);
            }else {
                mTabPane.getSelectionModel().select(filtered.get(0));
            }
        }catch (Exception e){
            LogUtil.info(e.getMessage());
            e.printStackTrace();
        }


    }

    /**
     * 左侧菜单模板
     */
    static class TemlpCell extends TreeCell<ListMenuItem> {

        @Override
        protected void updateItem(ListMenuItem menuItem, boolean b) {
            super.updateItem(menuItem, b);
            if (!b){

                Pane pane = new Pane();
                pane.setPrefSize(200,30);
                // icon
//                ImageView head = new ImageView();
//                head.setFitWidth(30);
//                head.setFitHeight(30);
//                head.setLayoutX(5);
//                head.setLayoutY(5);
                // 菜单名称
                Label title = new Label();
                title.setText(menuItem.getName());
                title.setLayoutX(10);
                title.setLayoutY(5);
                title.setFont(new Font(16));
                title.setMaxWidth(110);
                pane.getChildren().add(title);
                this.setGraphic(pane);
            }else {
                setGraphic(null);
            }

        }
    }

}
