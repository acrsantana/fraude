package br.com.acrtech.fraude.dto

import br.com.acrtech.fraude.model.Usuario

class UsuarioDto() {

    var id: Long? = null
    var nome: String? = null
    var email: String? = null

    constructor(usuario: Usuario) : this() {
        this.id = usuario.id
        this.nome = usuario.nome
        this.email = usuario.email
    }
}
