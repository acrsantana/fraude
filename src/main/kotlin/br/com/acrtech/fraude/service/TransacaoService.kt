package br.com.acrtech.fraude.service

import br.com.acrtech.fraude.dto.CargaDto
import br.com.acrtech.fraude.dto.TransacaoDto
import br.com.acrtech.fraude.model.Carga
import br.com.acrtech.fraude.dto.FileDto
import br.com.acrtech.fraude.dto.RespostaDto
import br.com.acrtech.fraude.model.Transacao
import br.com.acrtech.fraude.repository.CargaRepository
import br.com.acrtech.fraude.repository.TransacaoRepository
import org.apache.tomcat.util.http.fileupload.FileUploadException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.security.InvalidParameterException
import java.time.LocalDateTime

@Service
class TransacaoService(
    val transacaoRepository: TransacaoRepository,
    val cargaRepository: CargaRepository
) {

    val invalidParameterMessage: String = "Quantidade inválida de parâmetros, todos os campos são obrigatórios." +
            "Verifique o arquivo de origem e tente novamente."
    val fileUploadMessage: String = "Arquivo não existe ou não possui conteúdo. Verifique o arquivo de origem" +
            " e tente novamente."
    val illegalArgumentMessage: String = "Já foi realizada uma carga na data indicada: "

    fun processaArquivo(file: FileDto, paginacao: Pageable): RespostaDto {

        val nomeArquivo = file.file?.originalFilename
        val tamanho = file.file?.bytes?.size
        val transacoesASeremValidadas: MutableList<String> = mutableListOf()
        var contadorDeTransacoesNoArquivo = 0
        var contadorDeTransacoesValidas = 0
        var contadorDeTransacoesInvalidas = 0
        val transacoesValidadas: MutableList<String> = mutableListOf()
        val transacoesInvalidas: MutableList<String> = mutableListOf()

        val statusDaCarga = Carga(
            id = null,
            fileName = nomeArquivo,
            size = tamanho,
            totalTransacoesArquivo = contadorDeTransacoesNoArquivo,
            totalTransacoesFalha = contadorDeTransacoesInvalidas,
            totalTransacoesSucesso = contadorDeTransacoesValidas,
            dataReferenciaTransacoes = null,
            erro = null,
            status = null
        )

        val transacaoNula = Transacao(
            null,
            "",
            "",
            "",
            "",
            "",
            "",
            BigDecimal(0),
            LocalDateTime.now()
        )

        try {

            file.file?.inputStream?.bufferedReader()?.forEachLine { transacao ->
                if (transacao.trim().isNotEmpty()){
                    transacoesASeremValidadas.add(transacao)
                    contadorDeTransacoesNoArquivo++
                }
            }

            if (contadorDeTransacoesNoArquivo == 0) {
                throw FileUploadException(fileUploadMessage)
            }

//          Pega a primeira linha do arquivo e tenta converter em uma transação válida.
//          Caso não consiga a carga é abortada.
            val primeiraTransacao: Transacao = validaTransacao(transacoesASeremValidadas[0])
                ?: throw InvalidParameterException(invalidParameterMessage)

            val dataPrimeiraTransacao = primeiraTransacao.dataHoraTransacao.toLocalDate()
            statusDaCarga.dataReferenciaTransacoes = dataPrimeiraTransacao

//          Verifica se já existe uma carga no banco com a mesma data de referencia. Caso já exista a carga é abortada.
            val carga = cargaRepository.listaPorData(dataPrimeiraTransacao) ?: statusDaCarga
            if (carga.id != null) {
                throw IllegalArgumentException("$illegalArgumentMessage $dataPrimeiraTransacao")
            }


            for (t in transacoesASeremValidadas){
                val transacao = validaTransacao(t)
                if (transacao == null){
                    transacoesInvalidas.add(t)
                    contadorDeTransacoesInvalidas++
                    continue
                }
                if (dataPrimeiraTransacao == transacao.dataHoraTransacao.toLocalDate()) {
                    try {
                        val buscarTransacaoIgual = transacaoRepository.buscarTransacaoIgual(
                            transacao.bancoOrigem,
                            transacao.agenciaOrigem,
                            transacao.contaOrigem,
                            transacao.bancoDestino,
                            transacao.agenciaDestino,
                            transacao.contaDestino,
                            transacao.valorTransacao,
                            transacao.dataHoraTransacao
                        ) ?: transacaoNula

                        if (buscarTransacaoIgual.bancoOrigem == "") {
                            transacaoRepository.save(transacao)
                            transacoesValidadas.add(t)
                            contadorDeTransacoesValidas++
                        } else {
                            transacoesInvalidas.add(t)
                            contadorDeTransacoesInvalidas++
                            continue
                        }

                    } catch (e: InvalidParameterException) {
                        transacoesInvalidas.add(t)
                        contadorDeTransacoesInvalidas++
                        continue
                    }
                } else {
                    transacoesInvalidas.add(t)
                    contadorDeTransacoesInvalidas++
                    continue
                }
                statusDaCarga.totalTransacoesArquivo = contadorDeTransacoesNoArquivo
                statusDaCarga.totalTransacoesSucesso = contadorDeTransacoesValidas
                statusDaCarga.totalTransacoesFalha = contadorDeTransacoesInvalidas
                statusDaCarga.status = true
                cargaRepository.save(statusDaCarga)


            }
        } catch (e: Exception) {
            statusDaCarga.totalTransacoesFalha = contadorDeTransacoesInvalidas
            statusDaCarga.erro = e.message
            statusDaCarga.totalTransacoesArquivo = contadorDeTransacoesNoArquivo
            statusDaCarga.totalTransacoesSucesso = contadorDeTransacoesValidas
            statusDaCarga.status = false
            cargaRepository.save(statusDaCarga)
            return RespostaDto(sucesso = false, CargaDto(statusDaCarga), transacoesInvalidas)
        }

        return RespostaDto(sucesso = true, CargaDto(statusDaCarga), transacoesValidadas)
    }

    fun validaTransacao(transacao: String): Transacao? {
        val campos = transacao.split(",")
        if (campos.size != 8) {
            return null
        } else {
            campos.forEach {
                if (it.isEmpty()) {
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

    fun listarTransacoes(
        paginacao:Pageable

    ): Page<TransacaoDto> {
        return transacaoRepository.findAll(paginacao).map { TransacaoDto(it) }

    }

    fun listarCargas(
        paginacao: Pageable

    ): Page<CargaDto> {
        return cargaRepository.findAll(paginacao).map { CargaDto(it) }

    }
}