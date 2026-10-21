package com.dio.budget.model;

import java.time.LocalDateTime;

public record Transacao(
    Long id,
    String descricao,
    Double valor,
    String tipo, // RECEITA ou DESPESA
    String categoria,
    LocalDateTime dataHora
) {}
