package com.dio.budget.tools;

import com.dio.budget.model.Transacao;
import com.dio.budget.repository.TransacaoRepository;
org.springframework.context.annotation.Bean;
org.springframework.context.annotation.Configuration;
org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FinancialTools {

    public record RegistrarTransacaoRequest(String descricao, Double valor, String tipo, String categoria) {}
    public record RegistrarTransacaoResponse(String status, Long idTransacao, Double valorRegistrado) {}

    public record ConsultarSaldoRequest(String mensagemRequisicao) {}
    public record ConsultarSaldoResponse(Double saldoAtual, int totalTransacoes) {}

    @Bean
    @Description("Registra uma despesa ou receita financeira no sistema. Tipo deve ser RECEITA ou DESPESA.")
    public Function<RegistrarTransacaoRequest, RegistrarTransacaoResponse> registrarTransacaoTool(TransacaoRepository repository) {
        return req -> {
            Transacao t = repository.salvar(req.descricao(), req.valor(), req.tipo(), req.categoria());
            return new RegistrarTransacaoResponse("Transacao registrada com sucesso!", t.id(), t.valor());
        };
    }

    @Bean
    @Description("Consulta o saldo liquido total disponivel e o total de transacoes registradas.")
    public Function<ConsultarSaldoRequest, ConsultarSaldoResponse> consultarSaldoTool(TransacaoRepository repository) {
        return req -> {
            Double saldo = repository.calcularSaldo();
            int count = repository.listarTodas().size();
            return new ConsultarSaldoResponse(saldo, count);
        };
    }
}
