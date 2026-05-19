package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.CreateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.EmpresaResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.UpdateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.service.EmpresaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<EmpresaResponse> create(@RequestBody CreateEmpresaRequest request) {
        return ResponseEntity.status(201).body(empresaService.createEmpresa(request));
    }

    @GetMapping
    public ResponseEntity<Page<EmpresaResponse>> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nomeEmpresa") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(empresaService.findAllActive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(empresaService.findById(id));
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<EmpresaResponse>> findAllInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nomeEmpresa") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(empresaService.findAllInactive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponse> update(@PathVariable UUID id,
                                                  @RequestBody UpdateEmpresaRequest request) {
        return ResponseEntity.ok(empresaService.updateEmpresa(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        empresaService.softDelete(id);
        return ResponseEntity.ok("Empresa eliminada com sucesso");
    }
}