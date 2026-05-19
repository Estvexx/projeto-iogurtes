package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.CreateFornecedorTipoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.FornecedorTipoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.UpdateFornecedorTipoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.service.FornecedorTipoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/fornecedor-tipos")
public class FornecedorTipoController {

    private final FornecedorTipoService fornecedorTipoService;

    public FornecedorTipoController(FornecedorTipoService fornecedorTipoService) {
        this.fornecedorTipoService = fornecedorTipoService;
    }

    @PostMapping
    public ResponseEntity<FornecedorTipoResponse> create(@Valid @RequestBody CreateFornecedorTipoRequest request) {
        return ResponseEntity.status(201).body(fornecedorTipoService.createFornecedorTipo(request));
    }

    @GetMapping
    public ResponseEntity<Page<FornecedorTipoResponse>> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(fornecedorTipoService.findAllActive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<FornecedorTipoResponse>> findAllInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        return ResponseEntity.ok(fornecedorTipoService.findAllInactive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorTipoResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(fornecedorTipoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorTipoResponse> update(@PathVariable UUID id,
                                                         @RequestBody @Valid UpdateFornecedorTipoRequest request) {
        return ResponseEntity.ok(fornecedorTipoService.updateFornecedorTipo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        fornecedorTipoService.softDelete(id);
        return ResponseEntity.ok("Tipo de fornecedor eliminado com sucesso");
    }
}