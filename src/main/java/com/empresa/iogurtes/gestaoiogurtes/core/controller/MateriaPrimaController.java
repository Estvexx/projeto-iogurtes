package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima.*;
import com.empresa.iogurtes.gestaoiogurtes.core.service.MateriaFornecedorService;
import com.empresa.iogurtes.gestaoiogurtes.core.service.MateriaPrimaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/materias-primas")
public class MateriaPrimaController {

    private final MateriaPrimaService materiaPrimaService;
    private final MateriaFornecedorService materiaFornecedorService;

    public MateriaPrimaController(MateriaPrimaService materiaPrimaService,
                                  MateriaFornecedorService materiaFornecedorService) {
        this.materiaPrimaService = materiaPrimaService;
        this.materiaFornecedorService = materiaFornecedorService;
    }

    // MATÉRIAS PRIMAS

    @PostMapping
    public ResponseEntity<MateriaPrimaResponse> create(@Valid @RequestBody CreateMateriaPrimaRequest request) {
        return ResponseEntity.status(201).body(materiaPrimaService.createMateriaPrima(request));
    }

    @GetMapping
    public ResponseEntity<Page<MateriaPrimaResponse>> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(materiaPrimaService.findAllActive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }
    @GetMapping("/fornecedores")
    public ResponseEntity<Page<MateriaFornecedorResponse>> findAllFornecedores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(materiaFornecedorService.findAll(PageRequest.of(page, size, Sort.by(dir, sort))));
    }


    @GetMapping("/inactive")
    public ResponseEntity<Page<MateriaPrimaResponse>> findAllInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(materiaPrimaService.findAllInactive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaPrimaResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(materiaPrimaService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaPrimaResponse> update(@PathVariable UUID id,
                                                       @Valid @RequestBody UpdateMateriaPrimaRequest request) {
        return ResponseEntity.ok(materiaPrimaService.updateMateriaPrima(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        materiaPrimaService.softDelete(id);
        return ResponseEntity.ok("Matéria prima eliminada com sucesso");
    }

    // MATERIA FORNECEDORES

    @PostMapping("/{materiaId}/fornecedores")
    public ResponseEntity<MateriaFornecedorResponse> createFornecedor(
            @PathVariable UUID materiaId,
            @Valid @RequestBody CreateMateriaFornecedorRequest request) {
        return ResponseEntity.status(201).body(materiaFornecedorService.createMateriaFornecedor(materiaId, request));
    }

    @GetMapping("/{materiaId}/fornecedores")
    public ResponseEntity<Page<MateriaFornecedorResponse>> findFornecedoresByMateria(
            @PathVariable UUID materiaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(materiaFornecedorService.findAllByMateria(materiaId, PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/fornecedores/{id}")
    public ResponseEntity<MateriaFornecedorResponse> findFornecedorById(
            @PathVariable UUID id) {
        return ResponseEntity.ok(materiaFornecedorService.findById(id));
    }

    @PutMapping("/fornecedores/{id}")
    public ResponseEntity<MateriaFornecedorResponse> updateFornecedor(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMateriaFornecedorRequest request) {
        return ResponseEntity.ok(materiaFornecedorService.updateMateriaFornecedor(id, request));
    }

    @DeleteMapping("/fornecedores/{id}")
    public ResponseEntity<String> softDeleteFornecedor(
            @PathVariable UUID id) {
        materiaFornecedorService.softDelete(id);
        return ResponseEntity.ok("Associação eliminada com sucesso");
    }
}
