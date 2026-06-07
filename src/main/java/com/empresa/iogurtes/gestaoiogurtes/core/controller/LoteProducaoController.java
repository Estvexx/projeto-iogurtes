package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.lotes.LoteProducaoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.service.LoteProducaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/lotes-producao")
public class LoteProducaoController {

    private final LoteProducaoService loteProducaoService;

    public LoteProducaoController( LoteProducaoService loteProducaoService) {
        this.loteProducaoService = loteProducaoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteProducaoResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(loteProducaoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<LoteProducaoResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataProducao") String sort,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(loteProducaoService.findAll(PageRequest.of(page, size, Sort.by(dir, sort))));
    }
}