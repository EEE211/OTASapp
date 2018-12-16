package oti.otasapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.google.zxing.integration.android.IntentIntegrator

class ReadQRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_qr)

        val integrator = IntentIntegrator(this@ReadQRActivity)
        integrator.setOrientationLocked(false)
        integrator.initiateScan()


        //IntentIntegrator(this@readActivity).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null) {
            Log.d("readQR", intentResult.getContents());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        val code_text = findViewById(R.id.code) as TextView
        code_text.text = intentResult.getContents()

    }
}
