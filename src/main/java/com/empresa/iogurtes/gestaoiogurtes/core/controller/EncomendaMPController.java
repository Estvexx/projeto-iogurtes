package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomendamp.CreateEncomendaMPRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.encomendamp.EncomendaMPResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoEncomendaMP;
import com.empresa.iogurtes.gestaoiogurtes.core.service.EncomendaMPService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/encomendas-mp")
public class EncomendaMPController {

    private final EncomendaMPService encomendaMpService;

    public EncomendaMPController(EncomendaMPService encomendaMpService) {
        this.encomendaMpService = encomendaMpService;
    }

    @PostMapping
    public ResponseEntity<EncomendaMPResponse> create(@Valid @RequestBody CreateEncomendaMPRequest request) {
        return ResponseEntity.status(201).body(encomendaMpService.createEncomenda(request));
    }

    @GetMapping
    public ResponseEntity<Page<EncomendaMPResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataEncomenda") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return ResponseEntity.ok(encomendaMpService.findAll(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncomendaMPResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(encomendaMpService.findById(id));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<Page<EncomendaMPResponse>> findByEstado(
            @PathVariable EstadoEncomendaMP estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(encomendaMpService.findByEstado(estado, PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "dataEncomenda"))));
    }

    @GetMapping("/fornecedor/{fornecedorId}")
    public ResponseEntity<Page<EncomendaMPResponse>> findByFornecedor(
            @PathVariable UUID fornecedorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(encomendaMpService.findByFornecedor(fornecedorId, PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "dataEncomenda"))));
    }


    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<EncomendaMPResponse> aprovar(@PathVariable UUID id) {
        return ResponseEntity.ok(encomendaMpService.aprovar(id));
    }

    @PatchMapping("/{id}/recebida")
    public ResponseEntity<EncomendaMPResponse> marcarRecebida(@PathVariable UUID id) {
        return ResponseEntity.ok(encomendaMpService.marcarRecebida(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EncomendaMPResponse> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(encomendaMpService.cancelar(id));
    }
}
