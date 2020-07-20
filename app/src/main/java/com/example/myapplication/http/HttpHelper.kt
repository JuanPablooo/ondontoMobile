package com.example.myapplication.http

import com.example.myapplication.model.Dentista
import com.example.myapplication.model.dto.UsuarioDTO
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONArray

class HttpHelper {
    private val gson = Gson()
    private val URL = "http://192.168.15.12:8080/dentistas";
    private val headerHttp = MediaType.parse("application/json; charset=utf-8")
    private var client = OkHttpClient()

    fun getDentistas(token :String) : ArrayList<Dentista>{
        //Pegar o token
        var request = Request.Builder().url(URL).addHeader("Authorization", "Bearer " + token).get().build()
        var resposta = makeRequest(request).body()
        var listaDentista = ArrayList<Dentista>()
        if(resposta != null){
            var dentistaJson =  resposta.string()
            var dentistasArray = JSONArray(dentistaJson)
            for (i in 0 until  dentistasArray.length()){
                val dentistaJson = dentistasArray.getJSONObject(i)
                val dentista = Dentista(
                    ""+dentistaJson.optInt("id"),
                    dentistaJson.optString("cro"),
                    dentistaJson.optString("telefone"),
                    dentistaJson.optString("urlFotoPerfil"),
                    dentistaJson.optString("nome"),
                    gson.fromJson(
                        dentistaJson.optString("usuario"),
                        UsuarioDTO::class.java)
                )
                listaDentista.add(dentista)
            }
        }
        return listaDentista
    }

    fun enviarImagem(json: String, id: String) : String {
        val url = URL + "/upload/" + id;
        val body = getBody(json)
        print(url)
        var request = getRequestPost(url, body)
        var response = makeRequest(request)
        return response.body().toString()
    }
    fun enviarDentista(json: String) : String {
        val url = URL + "/cadastro";
        val body = getBody(json);
        var request = getRequestPost(url, body)
        var resposta = makeRequest(request).body()
        if(resposta != null){
            return resposta.string()
        }
        return ""

    }
    fun logar(json: String) : String {
        val url = URL + "/autenticacao";
        val body = getBody(json);
        var request = getRequestPost(url, body)
        var resposta = makeRequest(request).body()
        if(resposta != null){
            return resposta.string()
        }
        return ""

    }
    // Body da requisição
    private fun getBody(json: String) : RequestBody{
        return RequestBody.create(headerHttp, json)
    }
    // Construir a requisição http para o servidor
    private fun getRequestPost(myUrl:String, body: RequestBody ) : Request{
        return Request.Builder().url(myUrl).post(body).build()
    }
    private fun makeRequest(myRequest: Request) : Response{
        return client.newCall(myRequest).execute()
    }
}