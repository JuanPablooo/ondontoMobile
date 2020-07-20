package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.http.HttpHelper
import com.example.myapplication.model.Dentista
import com.example.myapplication.model.LoginResponseAPi
import com.example.myapplication.model.dto.UsuarioDTO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cadastro.*
import org.jetbrains.anko.doAsync

class CadastroActivity : AppCompatActivity(), View.OnClickListener {
    val dentista = Dentista()
    val userDTO = UsuarioDTO()
    var gson = Gson()
    val http = HttpHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        btn_cadastrar_dentista.setOnClickListener(this)
    }
    fun RedirectToCadastroFoto(dentistaJson: String){
        var intent = Intent(this, CadastroFotoActivity::class.java)
        intent.putExtra("dentistaJson", dentistaJson)
        startActivity(intent)
    }

    override  fun onClick(view: View){
        val id = view.id
        if(id == R.id.btn_cadastrar_dentista){
            var email = txtLogin.text.toString()
            var senha = textSenha.text.toString()
            userDTO.login = email
            userDTO.senha = senha

            var nome = txtNome.text.toString()
            var telefone = textTelefone.text.toString()
            var cro = textCro.text.toString()

            dentista.nome = nome
            dentista.cro = cro
            dentista.telefone = telefone
            dentista.usuario = userDTO

            var dentistaJson = gson.toJson(dentista)


            doAsync {
                var resposta  = http.enviarDentista(dentistaJson)
                print("respostaaa")
                println(resposta)
                var login = LoginResponseAPi()
                login = gson.fromJson(resposta, LoginResponseAPi::class.java)
                saveDataShared(login.jwt)
                RedirectToCadastroFoto(gson.toJson(login.dentista))
//                var dentista2 = Dentista()
//                dentista2 = gson.fromJson(resposta, Dentista::class.java)

            }
//            var dentista2 = Dentista()
//            dentista2 = gson.fromJson(dentistaJson, Dentista::class.java)
        }
    }
    fun saveDataShared(token:String){
        val shared = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.apply{
            putString("token_key", token)
        }.apply()
    }
    fun getDataShared() : String? {
        val shared = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedStringg = shared.getString("token_key", "")
        return  savedStringg
    }
}
