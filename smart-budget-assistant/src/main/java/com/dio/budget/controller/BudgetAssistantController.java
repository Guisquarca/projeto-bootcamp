package com.dio.budget.controller;

import com.dio.budget.model.Transacao;
import com.dio.budget.repository.TransacaoRepository;
import com.dio.budget.service.BudgetAssistantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assistant")
public class BudgetAssistantController {

    private final BudgetAssistantService assistantService;
    private final TransacaoRepository repository;

    public BudgetAssistantController(BudgetAssistantService assistantService, TransacaoRepository repository) {
        this.assistantService = assistantService;
        this.repository = repository;
    }

    public record ComandoPrompt(String comando) {}

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody ComandoPrompt payload) {
        String resposta = assistantService.processarComando(payload.comando());
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/transacoes")
    public ResponseEntity<List<Transacao>> listar() {
        return ResponseEntity.ok(repository.listarTodas());
    }
}
