package br.com.acrtech.fraude.model

import br.com.acrtech.fraude.dto.CargaDto

class Resposta(
    val sucesso: Boolean,
    val carga: CargaDto,
    val transacoes: List<String>
) {
}