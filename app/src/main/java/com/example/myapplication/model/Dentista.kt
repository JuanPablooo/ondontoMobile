package com.example.myapplication.model

import com.example.myapplication.model.dto.UsuarioDTO

class Dentista (){

    var id  = ""
    var cro  = ""
    var telefone  = ""
    var urlFotoPerfil  = ""
    var nome  = ""
    var usuario: UsuarioDTO ? = null
    constructor(id: String, cro: String,
                telefone: String, urlFoto: String,
                nome:String, user:UsuarioDTO):this(){
        this.id = id
        this.cro = cro
        this.nome = nome
        this.telefone = telefone
        this.urlFotoPerfil = urlFoto
        this.usuario = user
    }
}
