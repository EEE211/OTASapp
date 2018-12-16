package oti.otasapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AndroidRuntimeException
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class PrintQRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print_qr)

        //通信するクラスUploadTaskを呼び出す
        ServerConnection({
            if (it == null) {
                println("connection error")
                return@ServerConnection
            }

            // QRコード生成するための文字列を代入
            val data = it   //　itにphpから取得した乱数が返される

            val size = 500  //　QRコードを表示する幅

            try {
                //bitmapでQRコードを生成
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, size, size)

                //生成したQRコードを画面上のImageViewの部分に配置
                val imageViewQrCode = findViewById<ImageView>(R.id.imageView)
                imageViewQrCode.setImageBitmap(bitmap)

            } catch (e: WriterException) {
                throw AndroidRuntimeException("Barcode Error.", e)
            }

        }).execute("http://54.95.87.76/~morioka/qr.php")  //ここから非同期通信開始、終了後、{}内の処理に移る
        //execute(メソッド名, サーバー側のプログラムのURL)
    }
}
