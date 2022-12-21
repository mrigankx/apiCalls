package com.example.apicalls

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get data
        val textView = findViewById<TextView>(R.id.textview)
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/posts/1"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                println(response)
                textView.text = "Response is: $response"
            },
            {
                println(it)
                textView.text = "That didn't work!" })
        queue.add(stringRequest)

//        post data
        val postDataBtn: Button = findViewById(R.id.idBtnPost)
        val responseTV: TextView = findViewById(R.id.idTVResponse)
        postDataBtn.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = "https://private-4c0e8-simplestapi3.apiary-mock.com/message"

            val requestBody = "id=1" + "&msg=test_msg"
            val stringReq : StringRequest =
                object : StringRequest(Method.POST, url,
                    Response.Listener { response ->
                        // response
                        val strResp = response.toString()
                        responseTV.text = strResp
                    },
                    Response.ErrorListener { error ->
                        Log.d("API", "error => $error")
                    }
                ){
                    override fun getBody(): ByteArray {
                        return requestBody.toByteArray(Charset.defaultCharset())
                    }
                }
            queue.add(stringReq)
        }

//        PUT data
        val putbutton : Button = findViewById(R.id.putbutton)
        val putText: TextView = findViewById(R.id.puttext)
        val urlput = "https://jsonplaceholder.typicode.com/posts/1"
        putbutton.setOnClickListener {
            val putRequest: StringRequest = object : StringRequest(
                Method.PUT, urlput,
                Response.Listener { response -> // response
                    putText.text = response
                },
                Response.ErrorListener {response->
                    Log.d("Error.Response", response.toString())
                    putText.text = response.toString()
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    val params: MutableMap<String, String> = HashMap()
                    params["title"] = "passu"
                    params["body"] = "http://itsalif.info"
                    return params
                }
            }

            queue.add(putRequest)
        }

//        delete data
        val delbutton : Button = findViewById(R.id.delBtn)
        val delurl = "https://jsonplaceholder.typicode.com/posts/1"
        delbutton.setOnClickListener {
            val dr = StringRequest(
                Request.Method.DELETE, delurl,
                { response -> // response
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                }
            ) {
                // error.
            }
            queue.add(dr)
        }
    }

}