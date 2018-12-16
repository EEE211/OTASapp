package oti.otasapp

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class NewAccount : AppCompatActivity() {

    private var task: ServerConnection? = null
    private var textView: TextView? = null
    // wordを入れる
    private var editText: EditText? = null
    private var editText2: EditText? = null
    private var editText3: EditText? = null
    //internal var params = arrayOfNulls<String>(3)


    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newaccount)

        editText = findViewById(R.id.uriname)//プログラムで設定できるようにする
        editText2 = findViewById(R.id.uriname2)//プログラムで設定できるようにする
        editText3 = findViewById(R.id.uriname3)//プログラムで設定できるようにする

        val post = findViewById<Button>(R.id.post)//プログラムで設定できるようにする

        // ボタンをタップして非同期処理を開始
        post.setOnClickListener {
            val params = arrayOfNulls<String>(3)
            val params1 = editText!!.text.toString() //文字列をString型にする
            val params2 = editText2!!.text.toString() //文字列をString型にする
            val params3 = editText3!!.text.toString() //文字列をString型にする
            params[0] = params1
            params[1] = params2
            params[2] = params3



            if (params[0]!!.length != 0 && params[1]!!.length != 0 && params[2]!!.length != 0) {//文字があるかないか
                task = ServerConnection()
                task!!.setListener(createListener())
                task!!.execute("http://54.95.87.76/~morioka/TrueTest.php", *params)//実行
            }
        }

        textView = findViewById(R.id.text_view)
    }


    override fun onDestroy() {
        task!!.setListener(null)
        super.onDestroy()
    }

    private fun createListener(): ServerConnection.Listener {
        return object : ServerConnection.Listener {
            override fun onSuccess(result: String) {
                textView!!.setText(result)
            }
        }
    }
}