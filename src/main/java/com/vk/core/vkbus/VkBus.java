package com.vk.core.vkbus;

import com.vk.core.vkbus.busbean.BusData;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 作者:   王恒
 * Author:VINCE
 * 日期:   2020-06-24
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
 * 使用了双检锁模式单例
 */
public class VkBus {
    private static volatile VkBus mInstance = null;

    private Map<Class, List<Disposable>> subscriptionsByEventType = new HashMap<>();

    private Map<Object,List<Class>> eventTypesBySubscriber = new HashMap<>();

    private Map<Class,List<SubscriberMethod>> subscriberMethodByEventType = new HashMap<>();

    private static Subject<Object> bus;

    private VkBus(){
        bus = PublishSubject.create().toSerialized();
    }

    /**
     * 使用了双检锁的单例模式
     * @return
     */
    public static VkBus getmInstance(){
        if (null==mInstance){
            synchronized (VkBus.class)
            {
                if (null==mInstance){
                    mInstance = new VkBus();
                }
            }
        }
        return mInstance;
    }

    /**
     *
     * @param eventType
     * @param <T>
     * @return
     */
    private <T> Flowable<T> toObservable(Class<T> eventType){
        return bus.toFlowable(BackpressureStrategy.BUFFER).ofType(eventType);
    }

    /**
     *
     * @param code
     * @param eventType
     * @param <T>
     * @return
     */
    private <T> Flowable<T> toObservable(int code,Class<T> eventType){
        return bus.toFlowable(BackpressureStrategy.BUFFER)
                .ofType(Message.class)
                .filter(new Predicate<Message>() {
                    @Override
                    public boolean test(@NonNull Message t) throws Exception {
                        return t.getCode()==code && eventType.isInstance(t.getObject());
                    }
                })
                .map(new Function<Message, Object>() {
                    @Override
                    public Object apply(@NonNull Message message) throws Exception {
                        return message.getObject();
                    }
                }).cast(eventType);
    }

    /**
     * 注册
     * @param subscriber
     */
    public void register(Object subscriber){
        // 获取类
        Class<?> clazz = subscriber.getClass();
        // 获取声明的方法
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method:methods) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] types = method.getParameterTypes();

                if (types!=null&&types.length==1){
                    Class eventType =  types[0];
                    addEventTypeToMap(subscriber,eventType);
                    Subscribe sub = method.getAnnotation(Subscribe.class);
                    int code = sub.code();
                    ThreadMode threadMode = sub.threadMode();

                    SubscriberMethod subscriberMethod = new SubscriberMethod(method, threadMode,
                            eventType, subscriber, code);

                    addSubscriberToMap(eventType,subscriberMethod);

                    addSubscriber(subscriberMethod);
                }else if (types==null||types.length==0){
                    Class eventType = BusData.class;
                    addEventTypeToMap(subscriber,eventType);
                    Subscribe sub = method.getAnnotation(Subscribe.class);
                    int code = sub.code();
                    ThreadMode threadMode = sub.threadMode();
                    SubscriberMethod subscriberMethod = new SubscriberMethod(method, threadMode,
                            eventType, subscriber, code);

                    addSubscriberToMap(eventType,subscriberMethod);
                    addSubscriber(subscriberMethod);
                }
            }
        }

    }

    /**
     *
     * @param subscriberMethod
     */
    private void addSubscriber(SubscriberMethod subscriberMethod) {
        Flowable flowable;
        if (subscriberMethod.code==-1){
            flowable = toObservable(subscriberMethod.eventType);
        }else {
            flowable = toObservable(subscriberMethod.code,subscriberMethod.eventType);
        }
        Disposable subscription = postToObservable(flowable,subscriberMethod)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        callEvent(subscriberMethod,o);
                    }
                });
        addSubscriptionToMap(subscriberMethod.subscriber.getClass(),subscription);
    }

    /**
     *
     * @param subscriberMethod
     * @param object
     */
    private void callEvent(SubscriberMethod subscriberMethod, Object object) {
        Class<?> clazz = object.getClass();
        List<SubscriberMethod> subscriberMethodList = subscriberMethodByEventType.get(clazz);

        if (subscriberMethodList!=null&&subscriberMethodList.size()>0){
            for (SubscriberMethod method:subscriberMethodList) {
                Subscribe sub = method.method.getAnnotation(Subscribe.class);
                if (sub.code()==subscriberMethod.code
                        &&subscriberMethod.subscriber.equals(method.subscriber)
                        &&subscriberMethod.method.equals(method.method)){
                    method.invoke(object);
                }
            }
        }
    }

    /**
     * 取消注册
     * @param subscriber
     */
    public void unRegister(Object subscriber){
        List<Class> list = eventTypesBySubscriber.get(subscriber);
        if (list!=null){
            for (Class eventType: list) {
                unSubscriberByEventType(eventType);
                unSubscriberMethodByEventType(subscriber,eventType);
            }

            eventTypesBySubscriber.remove(subscriber);
        }

    }

    /**
     *
     * @param subscriber
     * @param eventType
     */
    private void unSubscriberMethodByEventType(Object subscriber, Class eventType) {
        List<SubscriberMethod> subscriberMethodList = subscriberMethodByEventType.get(eventType);
        if (subscriberMethodList!=null){
            Iterator<SubscriberMethod> iterator = subscriberMethodList.iterator();
            if (iterator.hasNext()){
                SubscriberMethod method = iterator.next();
                if (method.subscriber.equals(subscriber)){
                    iterator.remove();
                }
            }
        }
    }

    /**
     *
     * @param eventType
     */
    private void unSubscriberByEventType(Class eventType) {
        List<Disposable> disposableList = subscriptionsByEventType.get(eventType);
        if (disposableList!=null){
            Iterator<Disposable> iterator = disposableList.iterator();
            if (iterator.hasNext()){
                Disposable next = iterator.next();
                if (next!=null&&!next.isDisposed()){
                    next.dispose();
                    iterator.remove();
                }
            }
        }
    }


    /**
     *
     * @param observable
     * @param subscriberMethod
     * @return
     */
    private Flowable postToObservable(Flowable observable,SubscriberMethod subscriberMethod){
        Scheduler scheduler;
        switch (subscriberMethod.threadMode){
            case CURRENT_THREAD:
                scheduler = Schedulers.trampoline();
                break;
            case MAIN:
                scheduler = JavaFxScheduler.platform();
                break;
            case NEW_THREAD:
                scheduler = Schedulers.newThread();
                break;
            default:
                throw new IllegalStateException("未知的的线程类型："+subscriberMethod.threadMode);
        }
        return observable.observeOn(scheduler);
    }

    /**
     *
     * @param eventType
     * @param subscriberMethod
     */
    private void addSubscriberToMap(Class eventType, SubscriberMethod subscriberMethod) {
        List<SubscriberMethod> subscriberMethodList = subscriberMethodByEventType.get(eventType);
        if (subscriberMethodList==null){
            subscriberMethodList = new ArrayList<>();
            subscriberMethodByEventType.put(eventType,subscriberMethodList);
        }
        if (!subscriberMethodList.contains(subscriberMethod)){
            subscriberMethodList.add(subscriberMethod);
        }
    }

    /**
     *
     * @param subscriber
     * @param eventType
     */
    private void addEventTypeToMap(Object subscriber, Class eventType) {
        List<Class> eventTypes = eventTypesBySubscriber.get(subscriber);
        if (eventTypes==null){
            eventTypes = new ArrayList<>();
            eventTypesBySubscriber.put(subscriber,eventTypes);
        }
        if (!eventTypes.contains(eventType)){
            eventTypes.add(eventType);
        }
    }

    /**
     *
     * @param eventType
     * @param subscription
     */
    private void addSubscriptionToMap(Class<?> eventType, Disposable subscription) {
        List<Disposable> disposableList = subscriptionsByEventType.get(eventType);
        if (disposableList==null){
            disposableList = new ArrayList<>();
            subscriptionsByEventType.put(eventType,disposableList);
        }
        if (!disposableList.contains(subscription)){
            disposableList.add(subscription);
        }
    }

    public void send(int code,Object o){
        bus.onNext(new Message(code,o));
    }

    public void send(Object o){
        bus.onNext(o);
    }

    public void send(int code){
        bus.onNext(new Message(code,new BusData()));
    }

    private class Message{
        private int code;

        private Object object;

        public Message() {
        }

        public Message(int code, Object object) {
            this.code = code;
            this.object = object;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }

}


