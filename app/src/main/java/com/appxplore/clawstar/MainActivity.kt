package com.appxplore.clawstar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appxplore.clawstar.Constants.DeepLink1
import com.appxplore.clawstar.Constants.DeepLink2
import com.appxplore.clawstar.Constants.DeepLink3
import com.facebook.applinks.AppLinkData
import com.orhanobut.hawk.Hawk
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var MString: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("ActivityPREF", MODE_PRIVATE)

        if (prefs.getBoolean("activity_exec", false)) {
            Intent(this, FilterAct::class.java).also { startActivity(it) }
            finish()
        } else {
            val exec = prefs.edit()
            exec.putBoolean("activity_exec", true)
            exec.apply()
        }
    }

    override fun onResume() {
        super.onResume()
        appsdk()

    }

    private fun appLnkDeferred() {
        AppLinkData.fetchDeferredAppLinkData(
            this
        ) { appLinkData: AppLinkData? ->
            appLinkData?.let {
                val params =
                    appLinkData.targetUri.pathSegments
                Log.d("D11PL", "$params")
                val conjoined = TextUtils.join("/", params)
                val tokenizer = StringTokenizer(conjoined, "/")
                val firstLink = tokenizer.nextToken()
                val secondLink = tokenizer.nextToken()
                val thirdLink = tokenizer.nextToken()
                Hawk.put(DeepLink1, firstLink)
                Hawk.put(DeepLink2, secondLink)
                Hawk.put(DeepLink3, thirdLink)

                invHander()
                finish()
            }
            if (appLinkData == null){
                Log.d("FB_TEST:", "Params = null")
                invHander()
                finish()
            }
        }
    }

    private fun appsdk() {
        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: Map<String, Any>) {
                Log.d("TESTING_ZONE", "af stat is " + data["af_status"])
                MString = (data["campaign"] as String?)!!
                Log.d("NAMING", "campaign attributed: $MString")

                val tokenizer = StringTokenizer(MString, "_")
                val one = tokenizer.nextToken()
                val two = tokenizer.nextToken()
                val three = tokenizer.nextToken()

                Hawk.put(Constants.Campaign1, one)
                Hawk.put(Constants.Campaign2, two)
                Hawk.put(Constants.Campaign3, three)
                appLnkDeferred()
                invHander()
                finish()
            }

            override fun onConversionDataFail(error: String?) {
                Log.e("LOG_TAG", "error onAttributionFailure :  $error")

                appLnkDeferred()

                invHander()
                finish()
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                data?.map {
                    Log.d("LOG_TAG", "onAppOpen_attribute: ${it.key} = ${it.value}")
                }
            }

            override fun onAttributionFailure(error: String?) {
                Log.e("LOG_TAG", "error onAttributionFailure :  $error")
            }
        }
        AppsFlyerLib.getInstance().init(Constants.AF_DEV_KEY, conversionDataListener, applicationContext)
        AppsFlyerLib.getInstance().start(this)

    }

    fun invHander() {
        Intent(this, FilterAct::class.java)
            .also { startActivity(it) }
        finish()
    }
}