package com.example.interswitchpaymentlibrary;

import android.app.Application;

import com.interswitchng.iswmobilesdk.IswMobileSdk;
import com.interswitchng.iswmobilesdk.shared.models.core.IswSdkConfig;

public class Configure extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // configure sdk
        configureSDK();
    }

    public void configureSDK() {
        // use provided configuration for your merchant account
        String clientId = "<your clientId>";
        String merchantCode = "<your merchantCode>";
        String clientSecret = "<your clientSecret>";

        // create sdk configuration
        IswSdkConfig config = new IswSdkConfig(clientId,
                clientSecret, merchantCode, "566");

        // uncomment to set environment, default is Environment.TEST
        // config.setEnv(Environment.SANDBOX);

        // initialize sdk at boot of application
        IswMobileSdk.initialize(this, config);
    }
}
