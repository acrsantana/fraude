package br.com.acrtech.fraude.dto

import br.com.acrtech.fraude.model.Usuario
import org.springframework.lang.NonNull

class UsuarioDto(
    var id: Long? = null,
    var nome: String? = null,
    var email: String? = null
) {
    constructor(usuario: Usuario) : this(usuario.id, usuario.nome, usuario.email) {
    }
}
