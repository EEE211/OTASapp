package oti.otasapp

import android.os.AsyncTask
import android.os.Build
import android.support.annotation.RequiresApi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
class ServerConnection(callback: (String?) -> Unit) : AsyncTask<String, Unit, String>()  {

    private var listener: Listener? = null

    var callback = callback

    override fun doInBackground(vararg params: String): String? {
        val urlSt = params[0]   //引数のURLを代入
        var httpConn: HttpURLConnection? = null
        var responseData: String? = null

        try {
            var url = URL(urlSt)
            httpConn = url.openConnection() as HttpURLConnection
            //httpConn.requestMethod = "POST"
            httpConn.instanceFollowRedirects = false
            httpConn.requestMethod = "POST"
            httpConn.doOutput = true
            httpConn.doInput = true
            httpConn.readTimeout = 10000
            httpConn.connectTimeout = 20000
            httpConn.connect()  //通信開始

            var outStream: OutputStream? = null

            if(params[1] != null){

                val user = params[1]
                val pass = params[2]
                val Email = params[3]

                try{
                    outStream = httpConn.outputStream
                    outStream!!.write(user.toByteArray())
                    outStream.write(pass.toByteArray())
                    outStream.write(Email.toByteArray())
                    outStream.flush()
                }catch(e: IOException){
                    e.printStackTrace()
                }finally {
                    outStream?.close()
                }
            }

            val status = httpConn.responseCode

            //通信成功ならデータ取得
            if (status == HttpURLConnection.HTTP_OK && params[1] == null) {



                val br = BufferedReader(InputStreamReader(httpConn.inputStream))

                var line: String? = null
                val sb = StringBuilder()

                for (line in br.readLines()) {
                    line?.let { sb.append(line) }
                }

                br.close()
                responseData = sb.toString()

                return responseData     //取得したデータを返す

            }else if(status == HttpURLConnection.HTTP_OK && params[1] != null){
                responseData = "登録完了"
            } else {
                println("ERROR ${status}")
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            httpConn?.disconnect()
        }
        return null
    }

    //取得したデータを使うクラスに返す用
    override fun onPostExecute(result: String) {
        super.onPostExecute(result)

        if(listener != null){
            listener!!.onSuccess(result)
        }else {
            callback(result)
        }

    }

    internal fun setListener(listener: Listener?){
        this.listener = listener
    }

    internal interface Listener{
        fun onSuccess(result: String)
    }
}