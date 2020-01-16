package com.aliyun.iot.aep.sdk;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;


public class PushManager {

    static final String BIND = "/uc/bindPushChannel";
    static final String UN_BIND = "/uc/unbindPushChannel";


    private static class SingletonHolder {
        private static final PushManager INSTANCE = new PushManager();
    }

    private PushManager(){
    }

    public static final PushManager getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public void init(Application app) {
        initPush(app);
    }

    public void unbindUser() {
        request(UN_BIND);
    }

    public void bindUser() {
        request(BIND);
    }


    private void initPush(final Application app) {
        PushServiceFactory.init(app);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.setSecurityGuardAuthCode("114d");
//        String lang = EnvConfigure.getEnvArg("language");
//        if (TextUtils.isEmpty(lang)) {
//            lang = "zh-CN";
//        }
//        final String finalLang = lang;

        pushService.register(app, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("aep-demo", "init cloudchannel success");
//                String path = LoginBusiness.isLogin() ? BIND : UN_BIND;
//                request(path);

                // set device id
                String deviceId = PushServiceFactory.getCloudPushService().getDeviceId();
                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = "没有获取到";
                }
                EnvConfigure.putEnvArg(EnvConfigure.KEY_DEVICE_ID, deviceId);

                if (LoginBusiness.isLogin()) {
                    UnitTools unitTools = new UnitTools(app);
                    unitTools.readLanguage();
                    if("zh".equals(unitTools.readLanguage())){
                        IoTAPIClientImpl.getInstance().setLanguage("zh-CN"); // 全局配置，设置后立即起效
                    }else if("en".equals(unitTools.readLanguage())){
                        IoTAPIClientImpl.getInstance().setLanguage("en-US"); // 全局配置，设置后立即起效
                    }else if("fr".equals(unitTools.readLanguage())){
                        IoTAPIClientImpl.getInstance().setLanguage("fr-FR"); // 全局配置，设置后立即起效
                    }else if("de".equals(unitTools.readLanguage())){
                        IoTAPIClientImpl.getInstance().setLanguage("de-DE"); // 全局配置，设置后立即起效
                    }else if("es".equals(unitTools.readLanguage())){
                        IoTAPIClientImpl.getInstance().setLanguage("es-ES"); // 全局配置，设置后立即起效
                    }else {
                        IoTAPIClientImpl.getInstance().setLanguage("en-US"); // 全局配置，设置后立即起效
                    }
                    request(BIND);
                }
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d("aep-demo", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

        // bind user id
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LoginBusiness.LOGIN_CHANGE_ACTION);
        LocalBroadcastManager lb = LocalBroadcastManager.getInstance(app);
        lb.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (LoginBusiness.isLogin()) {
                    UnitTools unitTools = new UnitTools(context);
                    unitTools.readLanguage();
                    if("zh".equals(unitTools.readLanguage())){
                        IoTAPIClientImpl.getInstance().setLanguage("zh-CN"); // 全局配置，设置后立即起效
                    }else if("en".equals(unitTools.readLanguage())){
                        IoTAPIClientImpl.getInstance().setLanguage("en-US"); // 全局配置，设置后立即起效
                    }else if("fr".equals(unitTools.readLanguage())){
                        IoTAPIClientImpl.getInstance().setLanguage("fr-FR"); // 全局配置，设置后立即起效
                    }else if("de".equals(unitTools.readLanguage())){
                        IoTAPIClientImpl.getInstance().setLanguage("de-DE"); // 全局配置，设置后立即起效
                    }else if("es".equals(unitTools.readLanguage())){
                        IoTAPIClientImpl.getInstance().setLanguage("es-ES"); // 全局配置，设置后立即起效
                    }else {
                        IoTAPIClientImpl.getInstance().setLanguage("en-US"); // 全局配置，设置后立即起效
                    }

                    request(BIND);
                }
            }
        }, intentFilter);
    }

    void request(String path) {
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        String deviceId = pushService.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            return;
        }
        String apiVersion = "1.0.2";
        IoTRequestBuilder builder = new IoTRequestBuilder()
                .setAuthType("iotAuth")
                .setScheme(Scheme.HTTPS)
                .setPath(path)
                .setApiVersion(apiVersion)
                .addParam("deviceType", "ANDROID")
                .addParam("deviceId", deviceId);
        IoTRequest request = builder.build();
        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();
        ioTAPIClient.send(request, new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {
                e.printStackTrace();
                Log.d("Bind --->>>", "Failure");
            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                Log.d("Bind --->>>", "Success");
            }
        });
    }
}
