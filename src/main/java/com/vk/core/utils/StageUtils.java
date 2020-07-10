package com.vk.core.utils;

import com.sun.javafx.tk.TKStage;
import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import com.vk.utils.Config;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.lang.reflect.Method;

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
public class StageUtils {
    static interface ExtUser32 extends StdCallLibrary, User32 {

        ExtUser32 INSTANCE = (ExtUser32) Native.loadLibrary("user32", ExtUser32.class, W32APIOptions.DEFAULT_OPTIONS);

        WinDef.LRESULT CallWindowProcW(Pointer lpWndProc, Pointer hWnd, int msg, WinDef.WPARAM wParam, WinDef.LPARAM lParam);

        int SetWindowLong(HWND hWnd, int nIndex, com.sun.jna.Callback wndProc) throws LastErrorException;
    }

    // update default javafx stage style
    public static void updateStageStyle(Stage stage) {
        if (Platform.isWindows()) {
            Pointer pointer = getWindowPointer(stage);
            WinDef.HWND hwnd = new WinDef.HWND(pointer);

            final User32 user32 = User32.INSTANCE;
            int oldStyle = user32.GetWindowLong(hwnd, WinUser.GWL_STYLE);
            int newStyle = oldStyle | 0x00020000; // WS_MINIMIZEBOX
            user32.SetWindowLong(hwnd, WinUser.GWL_STYLE, newStyle);
        }
    }

    /**
     * jdk1.8   之后，这种方法已经无效了
     * @param stage
     */
    private static Pointer getWindowPointer(Stage stage) {
        try {

            Window window = stage.getOwner();
            Method getPeer = window.getClass().getDeclaredMethod("getPeer");
            getPeer.setAccessible(true);
            Object tkStage = getPeer.invoke(window);

            Method getRawHandle = tkStage.getClass().getDeclaredMethod("getRawHandle");
            getRawHandle.setAccessible(true);
            Object nativeHandle = getRawHandle.invoke(tkStage);
            return new Pointer((Long) nativeHandle);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    //加载Stage边框位置
    public static void loadPrimaryStageBound(Stage stage) {
        try {
            if (!Config.getBoolean(Config.Keys.RememberWindowLocation, true)) {
                return;
            }

            double left = Config.getDouble(Config.Keys.MainWindowLeft, -1);
            double top = Config.getDouble(Config.Keys.MainWindowTop, -1);
            double width = Config.getDouble(Config.Keys.MainWindowWidth, -1);
            double height = Config.getDouble(Config.Keys.MainWindowHeight, -1);

            if (left > 0) {
                stage.setX(left);
            }else {
                stage.setX(0);
            }
            if (top > 0) {
                stage.setY(top);
            }else {
                stage.setY(0);
            }
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

            stage.setWidth(width>bounds.getWidth()||width<0?bounds.getWidth():width);
            stage.setHeight(height>bounds.getHeight()||height<0?bounds.getHeight():height);

        } catch (Exception e) {
            LogUtil.error("初始化界面位置失败："+e);
        }
    }

    //保存Stage边框位置
    public static void savePrimaryStageBound(Stage stage) {
        if (!Config.getBoolean(Config.Keys.RememberWindowLocation, true)) {
            return;
        }
        if (stage == null || stage.isIconified()) {
            return;
        }
        try {
            Config.set(Config.Keys.MainWindowLeft, stage.getX());
            Config.set(Config.Keys.MainWindowTop, stage.getY());
            Config.set(Config.Keys.MainWindowWidth, stage.getWidth());
            Config.set(Config.Keys.MainWindowHeight, stage.getHeight());
        } catch (Exception e) {
            LogUtil.error("初始化界面位置失败："+e);
        }
    }
}
