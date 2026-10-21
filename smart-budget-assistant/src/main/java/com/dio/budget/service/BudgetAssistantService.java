package com.dio.budget.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class BudgetAssistantService {

    private final ChatClient chatClient;

    public BudgetAssistantService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("Voce e um assistente financeiro inteligente. Identifique se o usuario quer cadastrar despesas/receitas ou consultar saldos, acione as ferramentas adequadas e forneca uma resposta concisa e prestativa.")
                .build();
    }

    public String processarComando(String comandoUsuario) {
        return this.chatClient.prompt()
                .user(comandoUsuario)
                .functions("registrarTransacaoTool", "consultarSaldoTool")
                .call()
                .content();
    }
}
