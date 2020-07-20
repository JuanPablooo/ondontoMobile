package com.example.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.http.HttpHelper
import com.example.myapplication.model.Dentista
import com.example.myapplication.model.Imagem
import com.example.myapplication.util.bitMapToBase64
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cadastro_foto.*
import org.jetbrains.anko.doAsync

class CadastroFotoActivity : AppCompatActivity(), View.OnClickListener  {
    var dentista = Dentista()
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_foto)
        val dentisaJson:String = intent.getStringExtra("dentistaJson")
        dentista = gson.fromJson(dentisaJson, Dentista::class.java)
        cro.text = dentista.cro
        nome.text = dentista.nome
        faFoto.setOnClickListener(this)
        buttonSalvar.setOnClickListener(this)
    }
    override fun onClick(view: View) {
        val id = view.id
        if (id ==  R.id.buttonSalvar){
            print("salba")
            enviarFoto()
        }
        else if (id == R.id.faFoto){
            abrirGaleriaDeFotos()
        }

    }
    fun enviarFoto(){
        val imagem = Imagem()
        imagem.mimeType = "image/jpg"
        imagem.base64 = bitMapToBase64(imageFoto.drawable.toBitmap())

        println(imagem.base64)
        val gson = Gson()
        val imagemJson = gson.toJson(imagem)
//        println(imagemJson)

        doAsync {
            var http = HttpHelper()
            var resposta = http.enviarImagem(imagemJson, dentista.id)
            println("enviouuuu")
            println(resposta.toString())

        }
    }

    private fun abrirGaleriaDeFotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Selecionar Imagem"), 4587)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == -1 && data != null){
            val input = contentResolver.openInputStream(data.data!!)
            var bitmap = BitmapFactory.decodeStream(input)
            imageFoto.setImageBitmap(bitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
