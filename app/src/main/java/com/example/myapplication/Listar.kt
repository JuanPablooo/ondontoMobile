package com.example.myapplication

import android.content.Context
import android.graphics.LinearGradient
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.DentistaRecyclerAdapter
import com.example.myapplication.http.HttpHelper
import com.example.myapplication.util.SharedPreferToken
import kotlinx.android.synthetic.main.activity_listar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.sql.DataSource

class Listar : AppCompatActivity() {
    lateinit var dentistaRecyclerAdapter: DentistaRecyclerAdapter
    var http = HttpHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var compartilha =  SharedPreferToken(this)
//        compartilha =  SharedPreferToken(this)
        inicializarRecyclerView(compartilha)
        setContentView(R.layout.activity_listar)
    }
    private fun inicializarRecyclerView(compartilha : SharedPreferToken){
        var token = compartilha.getToken()
        doAsync {
            var lista = http.getDentistas(token)
            uiThread{
                rvDentistas.layoutManager = LinearLayoutManager(applicationContext,
                    LinearLayoutManager.VERTICAL, false)
                dentistaRecyclerAdapter = DentistaRecyclerAdapter(lista)
                rvDentistas.adapter = dentistaRecyclerAdapter
            }
        }

    }
}
