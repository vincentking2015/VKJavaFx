package com.vk.core.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DecimalFormat;

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
public class FileUtil {
    /**
     * 读取文件内容并返回为字符串
     *
     *
     */
    public static String readText(File file, Charset charset) {
        try {
            return FileUtils.readFileToString(file,charset);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 读取文件内容，按默认编码解析，并返回为字符串
     *
     *
     */
    public static String readText(File file) {
        return readText(file, Charset.defaultCharset());
    }

    /**
     * 读取文件内容，按指定编码解析，并返回为字符串
     *
     *
     */
    public static String readText(File file,String charset) {
        try {
            return FileUtils.readFileToString(file,charset);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将文件名拆分成名字部分和扩展名部分
     *
     */
    public static String[] getFileNames(File file) {
        String fileName = file.getName();
//        return FilenameUtils.getExtension(fileName);
        return new String[]{
                FilenameUtils.getBaseName(fileName),
                FilenameUtils.getExtension(fileName)
        };
    }

    /**
     *
     */
    public static String getFileName(File file) {
        return getFileNames(file)[0];
    }

    /**
     *
     */
    public static String getFileSuffixName(File file) {
        return getFileNames(file)[1];
    }

    /**
     *
     */
    public static String getFileSuffixNameAndDot(File file) {
        String suffixName = getFileNames(file)[1];
        if (!"".equals(suffixName)) {
            suffixName = "." + suffixName;
        }
        return suffixName;
    }

    public static String getRandomFileName(File file) {
        String[] fileNames = getFileNames(file);
        String fileName = fileNames[0] + ("" + System.currentTimeMillis()).substring(9);
        if (!"".equals(fileNames[1])) {
            fileName += ("." + fileNames[1]);
        }
        return fileName;
    }

    /**
     * 转换文件的大小以B,KB,M,G等计算
     *
     * @deprecated 使用 {@link FileUtils#byteCountToDisplaySize(java.math.BigInteger)}
     */
    public static String formatFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.000");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
