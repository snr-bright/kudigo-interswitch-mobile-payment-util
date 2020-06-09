package com.example.interswitchintegration

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class MyContract : ActivityResultContract<Int, String>() {
    override fun createIntent(context: Context, input: Int?): Intent {

    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {

    }

}
