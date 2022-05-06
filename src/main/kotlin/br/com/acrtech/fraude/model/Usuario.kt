package br.com.acrtech.fraude.model

import br.com.acrtech.fraude.dto.UsuarioDto
import javax.persistence.*
import kotlin.random.Random

@Entity
@Table(name = "usuarios")
class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var nome: String?,
    var email: String?,
    var password: String? = null,
    val visivel: Boolean = true
) {

    constructor(usuarioDto: UsuarioDto) : this(nome = usuarioDto.nome, email = usuarioDto.email) {
        this.password = geraSenhaAleatoria()
    }

    private fun geraSenhaAleatoria(): String {

        val random = Random.nextLong(1000000000L, 9999999999L).toString()
        println(random)
        return random
    }

}