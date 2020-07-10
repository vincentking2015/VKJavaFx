package com.vk.core.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.Imaging;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
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
public class ImageUtil {
    /**
     * 获取图片BufferedImage
     * @param path 图片路径
     */
    public static BufferedImage getBufferedImage(String path) {
        return getBufferedImage(new File(path));
    }

    public static BufferedImage getBufferedImage(File file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = Imaging.getBufferedImage(file);
        } catch (Exception e) {
            try {
                bufferedImage = ImageIO.read(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return bufferedImage;
    }

    /**
     * 获取javafx图片
     * @param path 图片路径
     */
    public static Image getFXImage(String path) {
        return getFXImage(new File(path));
    }

    public static Image getFXImage(File file) {
        Image image = null;
        try {
            image = SwingFXUtils.toFXImage(Imaging.getBufferedImage(file), null);
        } catch (Exception e) {
            image = new Image("file:" + file.getAbsolutePath());
        }
        return image;
    }

    public static Image getFXImage(byte[] bytes) {
        Image image = null;
        try {
            image = SwingFXUtils.toFXImage(Imaging.getBufferedImage(bytes), null);
        } catch (Exception e) {
            image = new Image(new ByteArrayInputStream(bytes));
        }
        return image;
    }


    /**
     * 保存图片
     * @param image
     * @param file
     */
    public static void writeImage(Image image, File file) throws Exception{
        writeImage(SwingFXUtils.fromFXImage(image, null),file);
    }

    public static void writeImage(BufferedImage bufferedImage, File file) throws Exception{
        try {
            Imaging.writeImage(bufferedImage,file, ImageFormats.valueOf(FileUtil.getFileSuffixName(file).toUpperCase()),null);
        } catch (Exception e) {
            e.printStackTrace();
            ImageIO.write(bufferedImage, FileUtil.getFileSuffixName(file),file);
        }
    }

}
