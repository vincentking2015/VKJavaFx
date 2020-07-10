package com.vk.core.utils;

import com.vk.core.javafx.helper.DropContentHelper;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

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
public class FileChooserUtil {

    public static final File HOME_DIRECTORY = FileSystemView.getFileSystemView().getHomeDirectory();

    public static File chooseFile() {
        return chooseFile(null);
    }

    public static File chooseFile(FileChooser.ExtensionFilter... extensionFilter) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择文件");
        fileChooser.setInitialDirectory(HOME_DIRECTORY);

        if (extensionFilter != null) {
            fileChooser.getExtensionFilters().addAll(extensionFilter);
        }

        return fileChooser.showOpenDialog(null);
    }

    ///////////////////////////////////////////////////////////////

    public static File chooseSaveFile(FileChooser.ExtensionFilter... extensionFilter) {
        return chooseSaveFile(null, extensionFilter);
    }

    public static File chooseSaveFile(String fileName) {
        return chooseSaveFile(fileName, null);
    }

    public static File chooseSaveFile(String fileName, FileChooser.ExtensionFilter... extensionFilter) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(HOME_DIRECTORY);

        if (fileName != null) {
            fileChooser.setInitialFileName(fileName);
        }

        if (extensionFilter != null) {
            fileChooser.getExtensionFilters().addAll(extensionFilter);
        }

        return fileChooser.showSaveDialog(null);
    }

    public static File chooseSaveCommonImageFile(String fileName) {
        return chooseSaveFile(fileName,
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"));
    }

    public static File chooseSaveImageFile(String fileName) {
        return chooseSaveFile(fileName,
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("gif", "*.gif"),
                new FileChooser.ExtensionFilter("jpeg", "*.jpeg"),
                new FileChooser.ExtensionFilter("bmp", "*.bmp"),
                new FileChooser.ExtensionFilter("ICO", "*.ico"),
                new FileChooser.ExtensionFilter("RGBE", "*.rgbe")
        );
    }

    ///////////////////////////////////////////////////////////////

    public static File chooseDirectory() {
        return chooseDirectory(null);
    }

    public static File chooseDirectory(File initialDirectory) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        if (initialDirectory != null) {
            directoryChooser.setInitialDirectory(initialDirectory);
        }
        return directoryChooser.showDialog(null);
    }

    ///////////////////////////////////////////////////////////////

    public static void setOnDrag(TextField textField, FileType fileType) {
        DropContentHelper.accept(textField,

                dragboard -> dragboard.hasFiles() &&
                        dragboard.getFiles().stream().anyMatch(fileType::match),

                (__, dragboard) -> textField.setText(
                        dragboard.getFiles().stream()
                                .filter(fileType::match)
                                .map(File::getAbsolutePath)
                                .findFirst().orElse("")
                )
        );
    }


    public static void setOnDragByOpenFile(TextInputControl textField) {
        DropContentHelper.accept(textField,

                dragboard -> dragboard.hasFiles() &&
                        dragboard.getFiles().stream().anyMatch(File::isFile),

                (__, dragboard) -> {
                    textField.setText(
                            dragboard.getFiles().stream()
                                    .filter(File::isFile)
                                    .map(FileUtil::readText)
                                    .findFirst().orElse("")
                    );

                }
        );

    }

    public enum FileType {
        FILE, FOLDER;

        public boolean match(File file) {
            return
                    (this == FILE && file.isFile()) ||
                            (this == FOLDER && file.isDirectory());
        }
    }
}
