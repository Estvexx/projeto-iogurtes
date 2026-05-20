package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.AddCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.FornecedorCertificacaoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.UpdateFornecedorCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.CreateFornecedorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.FornecedorResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.UpdateFornecedorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public ResponseEntity<FornecedorResponse> create(@RequestBody @Valid CreateFornecedorRequest request) {
        return ResponseEntity.status(201).body(fornecedorService.createFornecedor(request));
    }

    @GetMapping
    public ResponseEntity<Page<FornecedorResponse>> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(fornecedorService.findAllActive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<FornecedorResponse>> findAllInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(fornecedorService.findAllInactive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(fornecedorService.findById(id));
    }

    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<Page<FornecedorResponse>> findAllByTipo(
            @PathVariable UUID tipoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(fornecedorService.findAllByTipo(tipoId, PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponse> update(@PathVariable @Valid UUID id,
                                                     @RequestBody UpdateFornecedorRequest request) {
        return ResponseEntity.ok(fornecedorService.updateFornecedor(id, request));
    }

    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        fornecedorService.softDelete(id);
        return ResponseEntity.ok("Fornecedor eliminado com sucesso");
    }
    */

    // ─── Gestão de Certificações do Fornecedor ────────────────────────────────

    @PostMapping("/{fornecedorId}/certificacoes")
    public ResponseEntity<FornecedorCertificacaoResponse> addCertificacao(
            @PathVariable UUID fornecedorId,
            @RequestBody @Valid AddCertificacaoRequest request) {
        return ResponseEntity.status(201)
                .body(fornecedorService.addCertificacao(fornecedorId, request));
    }

    @PutMapping("/certificacoes/{fornecedorCertificacaoId}")
    public ResponseEntity<FornecedorCertificacaoResponse> updateCertificacao(
            @PathVariable UUID fornecedorCertificacaoId,
            @RequestBody @Valid UpdateFornecedorCertificacaoRequest request) {
        return ResponseEntity.ok(
                fornecedorService.updateCertificacao(fornecedorCertificacaoId, request));
    }

    @DeleteMapping("/certificacoes/{fornecedorCertificacaoId}")
    public ResponseEntity<String> removeCertificacao(
            @PathVariable UUID fornecedorCertificacaoId) {
        fornecedorService.removeCertificacao(fornecedorCertificacaoId);
        return ResponseEntity.ok("Certificação removida do fornecedor com sucesso");
    }
}