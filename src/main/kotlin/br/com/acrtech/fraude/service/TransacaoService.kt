package br.com.acrtech.fraude.service

import br.com.acrtech.fraude.model.FileModel
import br.com.acrtech.fraude.model.Transacao
import br.com.acrtech.fraude.repository.TransacaoRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.security.InvalidParameterException
import java.time.LocalDateTime

@Service
class TransacaoService(
    val repository: TransacaoRepository
) {

    val invalidParameterMessage: String = "Quantidade inválida de parâmetros. Todos os campos são obrigatórios"

    fun processaArquivo(file: FileModel) {

        println("Nome do arquivo: ${file.file?.originalFilename}")
        println("Tamanho: ${file.file?.bytes?.size} bytes")
        var listaDeTransacoes: MutableSet<String> = mutableSetOf()
        file.file?.inputStream?.bufferedReader()?.forEachLine { transacao ->
            listaDeTransacoes.add(transacao)
            println(transacao)
        }
        listaDeTransacoes.forEach { t ->

            try {
                repository.save(validaTransacao(t))
            } catch (e : InvalidParameterException){
                TODO("Logar as transações inválidas")
            }


        }
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