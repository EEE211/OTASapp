package oti.otasapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class PrintQRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_qr)

        //通信するクラスUploadTaskを呼び出す
        ServerConnection().execute("http://54.95.87.76/~morioka/qr.php")  //ここから非同期通信開始、終了後、{}内の処理に移る
        //execute(メソッド名, サーバー側のプログラムのURL)
    }
}
