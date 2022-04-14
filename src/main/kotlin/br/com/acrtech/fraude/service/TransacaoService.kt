package br.com.acrtech.fraude.service

import br.com.acrtech.fraude.model.Carga
import br.com.acrtech.fraude.model.FileModel
import br.com.acrtech.fraude.model.Resposta
import br.com.acrtech.fraude.model.Transacao
import br.com.acrtech.fraude.repository.CargaRepository
import br.com.acrtech.fraude.repository.TransacaoRepository
import org.apache.tomcat.util.http.fileupload.FileUploadException
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.security.InvalidParameterException
import java.time.LocalDateTime

@Service
class TransacaoService(
    val transacaoRepository: TransacaoRepository,
    val cargaRepository: CargaRepository
) {

    val invalidParameterMessage: String = "Quantidade inválida de parâmetros. Todos os campos são obrigatórios"
    val fileUploadMessage: String = "Arquivo não existe ou não possui conteúdo"
    val illegalArgumentMessage: String = "Já foi realizada uma carga na data indicada: "

    fun processaArquivo(file: FileModel): Resposta {

        val nomeArquivo = file.file?.originalFilename
        val tamanho = file.file?.bytes?.size

        var listaDeTransacoes: MutableList<String> = mutableListOf()
        var contadorTransacoes = 0
        var lista = arrayListOf<Transacao>()

        var cargaStatus = Carga(
            id = null,
            fileName = nomeArquivo,
            size = tamanho,
            totalTransacoes = contadorTransacoes,
            status = true,
            dataReferenciaTransacoes = null,
            erros = null
        )
        try {
            if (file.file?.isEmpty == true) {
                throw FileUploadException(fileUploadMessage)
            }

            file.file?.inputStream?.bufferedReader()?.forEachLine { transacao ->
                contadorTransacoes++
                listaDeTransacoes.add(transacao)
            }


            val primeiraTransacao: Transacao = validaTransacao(listaDeTransacoes[0])
                ?: throw InvalidParameterException(invalidParameterMessage)
            val dataPrimeiraTransacao = primeiraTransacao.dataHoraTransacao.toLocalDate()
            cargaStatus.dataReferenciaTransacoes = dataPrimeiraTransacao

            val carga = cargaRepository.findByDataReferenciaTransacoes(dataPrimeiraTransacao) ?: cargaStatus
            if (carga.id != null) {
                throw IllegalArgumentException("$illegalArgumentMessage $dataPrimeiraTransacao")
            }

            listaDeTransacoes.forEach { t ->
                val transacao = validaTransacao(t) ?: throw InvalidParameterException(invalidParameterMessage)

                if (dataPrimeiraTransacao == transacao.dataHoraTransacao.toLocalDate()) {
                    try {
                        transacaoRepository.save(transacao)
                        lista.add(transacao)
                    } catch (e: InvalidParameterException) {
                        throw InvalidParameterException(invalidParameterMessage)
                    }
                } else {
                    throw IllegalArgumentException("$illegalArgumentMessage ${carga.dataReferenciaTransacoes}")
                }
                cargaStatus.totalTransacoes = contadorTransacoes
                cargaRepository.save(cargaStatus)


            }
        } catch (e: Exception) {
            cargaStatus.status = false
            cargaStatus.erros = e.message
            cargaStatus.totalTransacoes = contadorTransacoes
            println(cargaStatus)
            cargaRepository.save(cargaStatus)
            return Resposta(sucesso = false, cargaStatus, null)
        }

        return Resposta(sucesso = true, cargaStatus, lista)
    }

    fun validaTransacao(transacao: String): Transacao? {
        var campos = transacao.split(",")
        if (campos.size != 8) {
            return null
        } else {
            campos.forEach {
                if (it.isNullOrEmpty()) {
                    return null
                }
            }

        }
        return Transacao(
            id = null,
            bancoOrigem = campos[0],
            agenciaOrigem = campos[1],
            contaOrigem = campos[2],
            bancoDestino = campos[3],
            agenciaDestino = campos[4],
            contaDestino = campos[5],
            valorTransacao = BigDecimal(campos[6]),
            dataHoraTransacao = LocalDateTime.parse(campos[7])
        )
    }
}