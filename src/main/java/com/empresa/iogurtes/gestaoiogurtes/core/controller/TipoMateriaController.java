package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.materias_tipo.MateriaTipoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.materias_tipo.UpdateTipoMateriaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.materias_tipo.CreateTipoMateriaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.service.TipoMateriaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tipos-materia")
public class TipoMateriaController {

    private final TipoMateriaService service;

    public TipoMateriaController(TipoMateriaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MateriaTipoResponse> create(@Valid @RequestBody CreateTipoMateriaRequest request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<MateriaTipoResponse>> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(service.findAllActive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<MateriaTipoResponse>> findAllInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(service.findAllInactive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaTipoResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaTipoResponse> update(@PathVariable UUID id,
                                                      @Valid @RequestBody UpdateTipoMateriaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        service.softDelete(id);
        return ResponseEntity.ok("Tipo de matéria-prima eliminado com sucesso");
    }
}