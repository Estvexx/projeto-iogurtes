package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.CreateFornecedorTipoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.FornecedorTipoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.UpdateFornecedorTipoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.service.FornecedorTipoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fornecedor-tipos")
public class FornecedorTipoController {

    private final FornecedorTipoService fornecedorTipoService;

    public FornecedorTipoController(FornecedorTipoService fornecedorTipoService) {
        this.fornecedorTipoService = fornecedorTipoService;
    }

    @PostMapping
    public ResponseEntity<FornecedorTipoResponse> create(@RequestBody CreateFornecedorTipoRequest request) {
        return ResponseEntity.status(201).body(fornecedorTipoService.createFornecedorTipo(request));
    }

    @GetMapping
    public ResponseEntity<List<FornecedorTipoResponse>> findAllActive() {
        return ResponseEntity.ok(fornecedorTipoService.findAllActive());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<FornecedorTipoResponse>> findAllInactive() {
        return ResponseEntity.ok(fornecedorTipoService.findAllInactive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorTipoResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(fornecedorTipoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorTipoResponse> update(@PathVariable UUID id,
                                                         @RequestBody UpdateFornecedorTipoRequest request) {
        return ResponseEntity.ok(fornecedorTipoService.updateFornecedorTipo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        fornecedorTipoService.softDelete(id);
        return ResponseEntity.ok("Tipo de fornecedor eliminado com sucesso");
    }
}