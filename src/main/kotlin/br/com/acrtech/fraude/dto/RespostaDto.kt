package br.com.acrtech.fraude.dto

import br.com.acrtech.fraude.dto.CargaDto

class RespostaDto(
    val sucesso: Boolean,
    val carga: CargaDto,
    val transacoes: List<String>
) {
}