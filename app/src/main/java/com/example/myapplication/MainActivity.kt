package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.myapplication.http.HttpHelper
import com.example.myapplication.model.Dentista
import com.example.myapplication.model.LoginResponseAPi
import com.example.myapplication.model.Usuario
import com.example.myapplication.model.dto.UsuarioDTO
import com.example.myapplication.util.SharedPreferToken
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.share
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val gson = Gson()
    val http = HttpHelper()
    var compartilha: SharedPreferToken ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         compartilha =  SharedPreferToken(this)

        btn_entrar.setOnClickListener(this)
        btn_cadastre_abre.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        if(id == R.id.btn_entrar){
            val user = UsuarioDTO()
            user.login = txtEmail.text.toString()
            user.senha = textSenha.text.toString()
            var userJson = gson.toJson(user)
            doAsync {

                var resposta = http.logar(userJson)
                var login = LoginResponseAPi()
                uiThread {
                    login = gson.fromJson(resposta, LoginResponseAPi::class.java)
                    if(login.jwt != null && login.jwt != ""){
                        println("salve")
                        println(login.jwt)

                        compartilha?.setToken(login.jwt)
                        var userLogado = login.dentista
                        changeListart()
                    }
                }

            }
        }
        else if (id == R.id.btn_cadastre_abre){
            var intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

    }
    fun changeListart(){
        var intent = Intent(this, Listar::class.java)
        startActivity(intent)
    }

}
