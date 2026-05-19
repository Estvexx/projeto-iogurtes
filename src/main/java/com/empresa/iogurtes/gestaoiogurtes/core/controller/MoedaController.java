package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.moeda.*;
import com.empresa.iogurtes.gestaoiogurtes.core.service.MoedaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/moedas")
public class MoedaController {

    private final MoedaService moedaService;

    public MoedaController(MoedaService moedaService) {
        this.moedaService = moedaService;
    }

    @PostMapping
    public ResponseEntity<MoedaResponse> create(@Valid @RequestBody CreateMoedaRequest request) {
        return ResponseEntity.status(201).body(moedaService.createMoeda(request));
    }

    @GetMapping
    public ResponseEntity<Page<MoedaResponse>> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "codigo") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(moedaService.findAllActive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<MoedaResponse>> findAllInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "codigo") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(moedaService.findAllInactive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoedaResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(moedaService.findById(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<MoedaResponse> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(moedaService.findByCodigo(codigo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoedaResponse> update(@PathVariable UUID id,
                                                @Valid @RequestBody UpdateMoedaRequest request) {
        return ResponseEntity.ok(moedaService.updateMoeda(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        moedaService.softDelete(id);
        return ResponseEntity.ok("Moeda eliminada com sucesso");
    }
}