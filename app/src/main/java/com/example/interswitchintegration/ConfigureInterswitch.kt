package com.example.interswitchintegration

import android.app.Application
import com.interswitchng.iswmobilesdk.IswMobileSdk
import com.interswitchng.iswmobilesdk.shared.models.core.Environment
import com.interswitchng.iswmobilesdk.shared.models.core.IswSdkConfig


class ConfigureInterswitch : Application() {
    override fun onCreate() {
        super.onCreate()
        // configure sdk
        configureSDK()
    }

    fun configureSDK() {
        // use provided configuration for your merchant account
        val merchantId = "<your merchantId>"
        val merchantCode = "<your merchantCode>"
        val merchantKey = "<your merchantKey>"

        // create sdk configuration
        val config = IswSdkConfig(
            merchantId,
            merchantKey, merchantCode, "566"
        )

        // uncomment to set environment, default is Environment.TEST
        // config.setEnv(Environment.PRODUCTION);

        // initialize sdk at boot of application
        IswMobileSdk.initialize(this, config)


    }
}