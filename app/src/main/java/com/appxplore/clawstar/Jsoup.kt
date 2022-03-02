package com.appxplore.clawstar

import android.content.Context
import com.appxplore.clawstar.Constants.Campaign1
import com.appxplore.clawstar.Constants.DeepLink1
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class Jsoup (val context: Context) {
    private var jsoup: String? = "null"
    private val hawk : String? = Hawk.get(Campaign1)
    private val hawkAppLink: String? = Hawk.get(DeepLink1)

    private var forJsoupSetNaming: String = Constants.FilterLink + Constants.subber1 + hawk
    private var forJsoupSetAppLnk: String = Constants.FilterLink + Constants.subber1 + hawkAppLink
    suspend fun getDocSecretKey(): String?{
        withContext(Dispatchers.IO){
            if(hawk!=null) {
                val doc = Jsoup.connect(forJsoupSetNaming).get()
                jsoup = doc.text().toString()
            } else {
                val doc = Jsoup.connect(forJsoupSetAppLnk).get()
                jsoup = doc.text().toString()
            }
        }
        return jsoup
    }
}