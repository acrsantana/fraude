package br.com.acrtech.fraude.service

import br.com.acrtech.fraude.model.Carga
import br.com.acrtech.fraude.model.FileModel
import br.com.acrtech.fraude.model.Transacao
import br.com.acrtech.fraude.repository.CargaRepository
import br.com.acrtech.fraude.repository.TransacaoRepository
import org.apache.tomcat.util.http.fileupload.FileUploadException
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.security.InvalidParameterException
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class TransacaoService(
    val transacaoRepository: TransacaoRepository,
    val cargaRepository: CargaRepository
) {

    val invalidParameterMessage: String = "Quantidade inválida de parâmetros. Todos os campos são obrigatórios"

    fun processaArquivo(file: FileModel) {
        val nomeArquivo = file.file?.originalFilename
        val tamanho = file.file?.bytes?.size

        println("Nome do arquivo: $nomeArquivo")
        println("Tamanho: $tamanho bytes")
        var listaDeTransacoes: MutableList<String> = mutableListOf()
        var contadorTransacoes = 0
        if (file.file?.isEmpty == true) {
            throw FileUploadException("Arquivo não existe ou não possui conteúdo")
        }
        file.file?.inputStream?.bufferedReader()?.forEachLine { transacao ->
            contadorTransacoes++
            listaDeTransacoes.add(transacao)
            println(transacao)
        }

        val primeiraTransacao = validaTransacao(listaDeTransacoes[0])
        val carga = cargaRepository.findByDataReferenciaTransacoes(primeiraTransacao.dataHoraTransacao.toLocalDate())
        if (carga?.equals(null) == false){
            throw IllegalArgumentException("Já foi realizada uma carga na data indicada: ${carga.dataReferenciaTransacoes}")
        }
        var status = true
        var transacaoInicial = validaTransacao(listaDeTransacoes[0])
        listaDeTransacoes.forEach { t ->
            val transacao = validaTransacao(t)
            if (transacaoInicial.dataHoraTransacao.toLocalDate() == transacao.dataHoraTransacao.toLocalDate()){
                try {
                    transacaoRepository.save(transacao)
                } catch (e: InvalidParameterException) {
                    status = false
                }
            }

        }
        cargaRepository.save(Carga(
            id = null,
            fileName = nomeArquivo,
            size = tamanho,
            totalTransacoes = contadorTransacoes,
            status = status,
            dataReferenciaTransacoes = transacaoInicial.dataHoraTransacao.toLocalDate()
        ))
    }

    fun validaTransacao(transacao: String): Transacao {
        var campos = transacao.split(",")
        if (campos.size != 8) {
            throw InvalidParameterException(invalidParameterMessage)
        } else {
            campos.forEach {
                if (it.isNullOrEmpty()) {
                    throw InvalidParameterException(invalidParameterMessage)
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