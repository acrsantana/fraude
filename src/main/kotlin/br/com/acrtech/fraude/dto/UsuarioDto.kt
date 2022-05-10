package br.com.acrtech.fraude.dto

import br.com.acrtech.fraude.model.Usuario
import javax.validation.constraints.NotBlank

class UsuarioDto(
    var id: Long? = null,
    @field:NotBlank(message = "O campo Nome não pode ser nulo ou vazio.")
    var nome: String? = null,
    @field:NotBlank(message = "O campo E-mail não pode ser nulo ou vazio.")
    var email: String? = null
) {
    constructor(usuario: Usuario) : this(usuario.id, usuario.nome, usuario.email) {
    }
}
