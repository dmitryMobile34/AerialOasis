package com.appxplore.clawstar

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.*

class FilterAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        val txtEr: TextView = findViewById(R.id.inviteTxt)
        runBlocking {

            val job: Job = GlobalScope.launch(Dispatchers.IO) {
                getAsync(applicationContext)
            }
            job.join()
            val jsoup: String? = Hawk.get(Constants.asyncResult, "")
            Log.d("cora", "cora $jsoup")

            txtEr.text = jsoup

            if (jsoup == "b7S2") {
                Intent(applicationContext, StartAct::class.java).also { startActivity(it) }
            } else {
                Intent(applicationContext, WebView::class.java).also { startActivity(it) }
            }
            finish()
        }
    }
    private suspend fun getAsync(context: Context) {
        val asyncKey = Jsoup(context)
        val asyncResult = asyncKey.getDocSecretKey()
        Hawk.put(Constants.asyncResult, asyncResult)
    }
}