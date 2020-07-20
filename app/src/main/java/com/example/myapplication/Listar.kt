package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.DentistaRecyclerAdapter
import com.example.myapplication.http.HttpHelper
import com.example.myapplication.util.SharedPreferToken
import kotlinx.android.synthetic.main.activity_listar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class Listar : AppCompatActivity() {
    lateinit var dentistaRecyclerAdapter: DentistaRecyclerAdapter
    var http = HttpHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var compartilha =  SharedPreferToken(this)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar()?.setHomeButtonEnabled(true);
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
    override fun onBackPressed() { //Botão BACK padrão do android
        startActivity(
            Intent(
                this,
                MainActivity::class.java
            )
        ) //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity() //Método para matar a activity e não deixa-lá indexada na pilhagem
        return
    }
}
