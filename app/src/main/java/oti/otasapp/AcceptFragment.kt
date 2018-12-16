package oti.otasapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class AcceptFragment : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept_fragment)


        //buttonの定義
        val generate_button = findViewById(R.id.generate) as Button
        val read_button = findViewById(R.id.read) as Button

        //QR生成の画面へ遷移
        generate_button.setOnClickListener {
            val intent = Intent(this, PrintQRActivity::class.java)
            startActivity(intent)
        }

        //QR読取の画面へ遷移
        read_button.setOnClickListener {
            val intent = Intent(this, ReadQRActivity::class.java)
            startActivity(intent)
        }
    }
}


