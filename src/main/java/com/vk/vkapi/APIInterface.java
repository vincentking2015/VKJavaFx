package com.vk.vkapi;

import com.vk.model.User;
import io.reactivex.Observable;
import retrofit2.http.*;

import java.util.List;

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

/**
 *
 */
public interface APIInterface {

    /**
     * 网络请求的根目录
     */
    public static String baseUrl = "http://im.airable.club/";

    /**
     * 约定的正确请求成功的code
     */
    public static int successCode = 0;

    /**
     * POST 示例
     * @param token
     * @param client_id
     */
    @POST("app/user/bindUid")
    @FormUrlEncoded
    Observable<ResponseData<User>>  bindUid(@Header("token") String token, @Field("client_id")String client_id);

    /**
     * GET 示例
     * @param token
     * @return
     */
    @GET("app/message/getMsgList")
    Observable<ResponseData<List<User>>> getMsgList(@Header("token") String token, @Query("id")int id);
}
