package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.produto_final.*;
import com.empresa.iogurtes.gestaoiogurtes.core.service.ProdutoFinalService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/produtos-finais")
public class ProdutoFinalController {

    private final ProdutoFinalService produtoFinalService;

    public ProdutoFinalController(ProdutoFinalService produtoFinalService) {
        this.produtoFinalService = produtoFinalService;
    }

    @PostMapping
    public ResponseEntity<ProdutoFinalResponse> create(@Valid @RequestBody CreateProdutoFinalRequest request) {
        return ResponseEntity.status(201).body(produtoFinalService.createProdutoFinal(request));
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoFinalResponse>> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(produtoFinalService.findAllActive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<ProdutoFinalResponse>> findAllInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(produtoFinalService.findAllInactive(PageRequest.of(page, size,
                Sort.by(Sort.Direction.ASC, "nome"))));
    }

    @GetMapping("/catalogo")
    public ResponseEntity<Page<ProdutoFinalResponse>> findAllVisivelCliente(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(produtoFinalService.findAllVisivelCliente(PageRequest.of(page, size,
                Sort.by(Sort.Direction.ASC, "nome"))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoFinalResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(produtoFinalService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoFinalResponse> update(@PathVariable UUID id,
                                                       @Valid @RequestBody UpdateProdutoFinalRequest request) {
        return ResponseEntity.ok(produtoFinalService.updateProdutoFinal(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        produtoFinalService.softDelete(id);
        return ResponseEntity.ok("Produto final eliminado com sucesso");
    }

    //COMPOSIÇÃO

    @PostMapping("/{produtoId}/composicao")
    public ResponseEntity<ProdutoFinalResponse> addMaterias(
            @PathVariable UUID produtoId,
            @Valid @RequestBody AddMateriasComposicaoRequest request) {
        return ResponseEntity.status(201).body(produtoFinalService.addMaterias(produtoId, request));
    }

    @PutMapping("/{produtoId}/composicao")
    public ResponseEntity<ProdutoFinalResponse> updateComposicao(
            @PathVariable UUID produtoId,
            @Valid @RequestBody UpdateComposicaoRequest request) {
        return ResponseEntity.ok(produtoFinalService.updateComposicao(produtoId, request));
    }

    @DeleteMapping("/{produtoId}/composicao/{composicaoId}")
    public ResponseEntity<ProdutoFinalResponse> removeMateria(
            @PathVariable UUID produtoId,
            @PathVariable UUID composicaoId) {
        return ResponseEntity.ok(produtoFinalService.removeMateria(produtoId, composicaoId));
    }
}