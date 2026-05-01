package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.CreateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.EmpresaResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.UpdateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<EmpresaResponse>> findAll() {
        return ResponseEntity.ok(empresaService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(empresaService.findById(id));
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