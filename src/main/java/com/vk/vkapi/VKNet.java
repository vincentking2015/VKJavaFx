package com.vk.vkapi;

import com.vk.core.utils.LogUtil;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
public class VKNet {

//    public static void main(String[] args) {
//        // 示例展示
//        VKNet.request(APIManager.getInstance().bindUid("sdad", "dsadas"), new VKNetCallback<User>() {
//
//            @Override
//            public void onSuccess(User data) {
//                TooltipUtil.showToast("成功了");
//            }
//
//            @Override
//            public void onFail(int code, String msg) {
//                TooltipUtil.showToast("失败了");
//            }
//        });
//
//    }

    /**
     * 单个请求
     * @param observable
     * @param callback
     * @param <T>
     * @return
     */
    public static <T>Disposable request(Observable<ResponseData<T>> observable,VKNetCallback<T> callback){
        return observable.subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .onErrorReturn(new Function<Throwable, ResponseData<T>>() {
                    @Override
                    public ResponseData<T> apply(@NonNull Throwable throwable) throws Exception {
                        LogUtil.info(throwable.getMessage());
                        callback.onFail(444,"网络请求错误："+throwable.getMessage());
                        return null;
                    }
                })
                .subscribe(new Consumer<ResponseData<T>>() {
                    @Override
                    public void accept(ResponseData<T> tResponseData) throws Exception {
                        if (tResponseData.getCode()==APIInterface.successCode){
                            callback.onSuccess(tResponseData.getData());
                        }else {
                            LogUtil.info(tResponseData.getMsg());
                            callback.onFail(tResponseData.getCode(),"错误："+tResponseData.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.info(throwable.getMessage());
                        callback.onFail(444,"网络请求错误："+throwable.getMessage());
                    }
                });

    }
}
