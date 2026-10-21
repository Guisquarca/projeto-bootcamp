package com.dio.budget.repository;

import com.dio.budget.model.Transacao;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TransacaoRepository {

    private final List<Transacao> transacoes = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public Transacao salvar(String descricao, Double valor, String tipo, String categoria) {
        Transacao nova = new Transacao(sequence.getAndIncrement(), descricao, valor, tipo.toUpperCase(), categoria, LocalDateTime.now());
        transacoes.add(nova);
        return nova;
    }

    public List<Transacao> listarTodas() {
        return Collections.unmodifiableList(transacoes);
    }

    public Double calcularSaldo() {
        double receitas = transacoes.stream().filter(t -> "RECEITA".equalsIgnoreCase(t.tipo())).mapToDouble(Transacao::valor).sum();
        double despesas = transacoes.stream().filter(t -> "DESPESA".equalsIgnoreCase(t.tipo())).mapToDouble(Transacao::valor).sum();
        return receitas - despesas;
    }
}
