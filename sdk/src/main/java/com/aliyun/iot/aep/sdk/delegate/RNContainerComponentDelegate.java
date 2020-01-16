package com.aliyun.iot.aep.sdk.delegate;

import android.app.Application;
import android.text.TextUtils;

import com.aliyun.iot.aep.sdk.EnvConfigure;
import com.aliyun.iot.aep.sdk.base.delegate.APIGatewaySDKDelegate;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKConfigure;
import com.aliyun.iot.aep.sdk.framework.sdk.SimpleSDKDelegateImp;

import java.util.Map;


public class RNContainerComponentDelegate extends SimpleSDKDelegateImp {

    static final public String KEY_RN_CONTAINER_PLUGIN_ENV = "KEY_RN_CONTAINER_PLUGIN_ENV";

    private final static String TAG = "RNContainerComponentDelegate";

    @Override
    public int init(final Application application, SDKConfigure sdkConfigure, Map<String, String> map) {
        // plugin env
        String pluginEnv = "test";
        pluginEnv = map.get(KEY_RN_CONTAINER_PLUGIN_ENV);
        if (TextUtils.isEmpty(pluginEnv)) {
            pluginEnv = "test";
        }
        map.put(KEY_RN_CONTAINER_PLUGIN_ENV, pluginEnv);

        // server env
        String serverEnv = map.get(APIGatewaySDKDelegate.ENV_KEY_API_CLIENT_API_ENV);
        if (TextUtils.isEmpty(serverEnv)) {
            serverEnv = "production"; // default is "production"
        } else if ("release".equalsIgnoreCase(serverEnv)) {
            serverEnv = "production";
        } else if ("pre".equalsIgnoreCase(serverEnv)) {
            serverEnv = "test";
        } else if ("test".equalsIgnoreCase(serverEnv)) {
            serverEnv = "development";
        }

        // language
        String language = map.get(EnvConfigure.KEY_LANGUAGE);
        if (TextUtils.isEmpty(language)) {
            language = "zh-CN";
        }

        return 0;
    }

}
