package br.com.acrtech.fraude.model

class Resposta(
    val sucesso: Boolean,
    val carga: Carga,
    val transacoes: List<Transacao>?
) {
}