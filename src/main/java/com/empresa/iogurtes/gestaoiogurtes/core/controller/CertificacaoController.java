package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.CertificacaoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.CreateCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.UpdateCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.service.CertificacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/certificacoes")
public class CertificacaoController {

    private final CertificacaoService certificacaoService;

    public CertificacaoController(CertificacaoService certificacaoService) {
        this.certificacaoService = certificacaoService;
    }

    @PostMapping
    public ResponseEntity<CertificacaoResponse> create(@Valid @RequestBody CreateCertificacaoRequest request) {
        return ResponseEntity.status(201).body(certificacaoService.createCertificacao(request));
    }

    // Todos (ativos + inativos) — paginado
    @GetMapping
    public ResponseEntity<Page<CertificacaoResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(certificacaoService.findAll(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    // Só ativos — paginado
    @GetMapping("/active")
    public ResponseEntity<Page<CertificacaoResponse>> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(certificacaoService.findAllActive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    // Só inativos — paginado
    @GetMapping("/inactive")
    public ResponseEntity<Page<CertificacaoResponse>> findAllInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(certificacaoService.findAllInactive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificacaoResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(certificacaoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificacaoResponse> update(@PathVariable UUID id,
                                                       @Valid @RequestBody UpdateCertificacaoRequest request) {
        return ResponseEntity.ok(certificacaoService.updateCertificacao(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        certificacaoService.softDelete(id);
        return ResponseEntity.ok("Certificação eliminada com sucesso");
    }
}