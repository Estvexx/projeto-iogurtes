package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomenda.CreateEncomendaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomenda.EncomendaResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomenda;
import com.empresa.iogurtes.gestaoiogurtes.core.service.EncomendaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/encomendas")
public class EncomendaController {

    private final EncomendaService service;

    public EncomendaController(EncomendaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EncomendaResponse> create(@Valid @RequestBody CreateEncomendaRequest request) {
        return ResponseEntity.status(201).body(service.createEncomenda(request));
    }

    @GetMapping
    public ResponseEntity<Page<EncomendaResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataEncomenda") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(service.findAll(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncomendaResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<Page<EncomendaResponse>> findByEstado(
            @PathVariable EstadoEncomenda estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataEncomenda") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(service.findByEstado(estado, PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<EncomendaResponse>> findByUser(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataEncomenda") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(service.findByUser(userId, PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @PatchMapping("/ordens/{encomendaOrdemId}/aceitar")
    public ResponseEntity<EncomendaResponse> aceitarOrdem(@PathVariable UUID encomendaOrdemId) {
        return ResponseEntity.ok(service.aceitarOrdem(encomendaOrdemId));
    }

    @PatchMapping("/ordens/{encomendaOrdemId}/recusar")
    public ResponseEntity<EncomendaResponse> recusarOrdem(@PathVariable UUID encomendaOrdemId) {
        return ResponseEntity.ok(service.recusarOrdem(encomendaOrdemId));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EncomendaResponse> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
}