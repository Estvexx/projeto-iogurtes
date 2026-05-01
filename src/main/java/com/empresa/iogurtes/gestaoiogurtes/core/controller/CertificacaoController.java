package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.CreateCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.CertificacaoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.UpdateCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.service.CertificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/certificacoes")
public class CertificacaoController {

    private final CertificacaoService certificacaoService;

    public CertificacaoController(CertificacaoService certificacaoService) {
        this.certificacaoService = certificacaoService;
    }

    @PostMapping
    public ResponseEntity<CertificacaoResponse> create(@RequestBody CreateCertificacaoRequest request) {
        return ResponseEntity.status(201).body(certificacaoService.createCertificacao(request));
    }

    @GetMapping
    public ResponseEntity<List<CertificacaoResponse>> findAllActive() {
        return ResponseEntity.ok(certificacaoService.findAllActive());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<CertificacaoResponse>> findAllInactive() {
        return ResponseEntity.ok(certificacaoService.findAllInactive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificacaoResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(certificacaoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificacaoResponse> update(@PathVariable UUID id,
                                                       @RequestBody UpdateCertificacaoRequest request) {
        return ResponseEntity.ok(certificacaoService.updateCertificacao(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        certificacaoService.softDelete(id);
        return ResponseEntity.ok("Certificação eliminada com sucesso");
    }
}