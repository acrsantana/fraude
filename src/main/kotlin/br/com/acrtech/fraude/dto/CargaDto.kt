package br.com.acrtech.fraude.dto

import br.com.acrtech.fraude.model.Carga
import java.time.format.DateTimeFormatter

class CargaDto() {
    var id:Long? = null
    var fileName: String? = null
    var size: Int? = null
    var totalTransacoesArquivo: Int? = null
    var totalTransacoesSucesso: Int? = null
    var totalTransacoesFalha: Int? = null
    var dataCarga: String? = null
    var dataReferenciaTransacoes: String? = null
    var status: Boolean? = null
    var erro: String? = null

    constructor(carga: Carga) : this() {
        this.id = carga.id
        this.fileName = carga.fileName
        this.size = carga.size
        this.totalTransacoesArquivo = carga.totalTransacoesArquivo
        this.totalTransacoesSucesso = carga.totalTransacoesSucesso
        this.totalTransacoesFalha = carga.totalTransacoesFalha
        this.dataCarga = carga.dataCarga.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        this.dataReferenciaTransacoes = carga.dataReferenciaTransacoes?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        this.status = carga.status
        this.erro = carga.erro
    }
}