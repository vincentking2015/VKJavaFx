package com.vk.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

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
public class LogUtil {

    public enum Level {
        ERROR,
        WARN,
        INFO,
        DEBUG,
        TRACE;
    }

    private static LogUtil stdOutErr = new LogUtil(new PrintWriter(System.out), new PrintWriter(System.err));
    private static ThreadLocal<LogUtil> loggers = new ThreadLocal<>();

    protected PrintWriter err; // Used for error and warning messages
    protected PrintWriter out; // Used for other messages
    protected LogUtil.Level level = LogUtil.Level.INFO;

    public LogUtil(Writer out, Writer err) {
        this.out = out == null ? null : new PrintWriter(out, true);
        this.err = err == null ? null : new PrintWriter(err, true);
    }

    public static void setLogForCurrentThread(LogUtil log) {
        loggers.set(log);
    }

    public static void setLogLevel(String l) {
        setLogLevel(LogUtil.Level.valueOf(l.toUpperCase(Locale.US)));
    }

    public static void setLogLevel(LogUtil.Level l) {
        get().level = l;
    }

    static public void trace(String msg) {
        log(LogUtil.Level.TRACE, msg);
    }

    static public void debug(String msg) {
        log(LogUtil.Level.DEBUG, msg);
    }

    static public void info(String msg) {
        log(LogUtil.Level.INFO, msg);
    }

    static public void warn(String msg) {
        log(LogUtil.Level.WARN, msg);
    }

    static public void error(String msg) {
        log(LogUtil.Level.ERROR, msg);
    }

    static public void error(Throwable t) {
        log(LogUtil.Level.ERROR, t);
    }

    static public void log(LogUtil.Level l, String msg) {
        get().printLogMsg(l, msg);
    }

    public static void debug(Throwable t) {
        log(LogUtil.Level.DEBUG, t);
    }

    public static void log(LogUtil.Level l, Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        log(l, sw.toString());
    }

    static public boolean isDebugging() {
        return get().isLevelLogged(LogUtil.Level.DEBUG);
    }

    protected boolean isLevelLogged(LogUtil.Level l) {
        return l.ordinal() <= level.ordinal();
    }

    public static LogUtil get() {
        LogUtil log = loggers.get();
        return log != null ? log : stdOutErr;
    }

    protected void printLogMsg(LogUtil.Level msgLevel, String msg) {
        if (isLevelLogged(msgLevel)) {
            PrintWriter pw = msgLevel.ordinal() <= LogUtil.Level.WARN.ordinal() ? err : out;
            pw.println(msg);
        }
    }
}
