package com.empresa.iogurtes.gestaoiogurtes.core.controller;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.users.*;
import com.empresa.iogurtes.gestaoiogurtes.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ── CREATE ──────────────────────────────────────────────

    @PostMapping("/admins")
    public ResponseEntity<UserResponse> createAdmin(@RequestBody @Valid CreateAdminRequest request) {
        return ResponseEntity.status(201).body(userService.createAdmin(request));
    }

    @GetMapping("/admins")
    public ResponseEntity<Page<UserResponse>> findAllAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(userService.findAllAdmins(PageRequest.of(page, size, Sort.by(dir, sort))));
    }
    @PutMapping("/admins/{id}")
    public ResponseEntity<UserResponse> updateAdmin(@PathVariable UUID id,
                                                    @RequestBody @Valid UpdateAdminRequest request) {
        return ResponseEntity.ok(userService.updateAdmin(id, request));
    }

    @PostMapping("/gestores")
    public ResponseEntity<UserResponse> createGestor(@RequestBody @Valid CreateGestorRequest request) {
        return ResponseEntity.status(201).body(userService.createGestor(request));
    }

    @GetMapping("/gestores")
    public ResponseEntity<Page<UserResponse>> findAllGestores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(userService.findAllGestores(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @PutMapping("/gestores/{id}")
    public ResponseEntity<UserResponse> updateGestor(@PathVariable UUID id,
                                                     @RequestBody @Valid UpdateGestorRequest request) {
        return ResponseEntity.ok(userService.updateGestor(id, request));
    }

    @PostMapping("/funcionarios/mp")
    public ResponseEntity<UserResponse> createFuncionarioMP(@RequestBody @Valid CreateFuncionarioRequest request) {
        return ResponseEntity.status(201).body(userService.createFuncionarioMP(request));
    }

    @PostMapping("/funcionarios/op")
    public ResponseEntity<UserResponse> createFuncionarioOP(@RequestBody @Valid CreateFuncionarioRequest request) {
        return ResponseEntity.status(201).body(userService.createFuncionarioOP(request));
    }

    @GetMapping("/funcionarios/mp")
    public ResponseEntity<Page<UserResponse>> findAllFuncionariosMP(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(userService.findAllFuncionarios_MP(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/funcionarios/op")
    public ResponseEntity<Page<UserResponse>> findAllFuncionariosOP(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(userService.findAllFuncionarios_OP(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<Page<UserResponse>> findAllFuncionarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(userService.findAllFuncionarios(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<UserResponse> updateFuncionario(@PathVariable UUID id,
                                                          @RequestBody @Valid UpdateFuncionarioRequest request) {
        return ResponseEntity.ok(userService.updateFuncionario(id, request));
    }



    @PostMapping("/clientes")
    public ResponseEntity<UserResponse> createCliente(@RequestBody @Valid CreateClienteRequest request) {
        return ResponseEntity.status(201).body(userService.createCliente(request));
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<UserResponse> updateCliente(@PathVariable UUID id,
                                                      @RequestBody @Valid UpdateClienteRequest request) {
        return ResponseEntity.ok(userService.updateCliente(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/clientes")
    public ResponseEntity<Page<UserResponse>> findAllClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(userService.findAllClientes(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/active")
    public ResponseEntity<Page<UserResponse>> findAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(userService.findAllActive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<UserResponse>> findAllInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(userService.findAllInactive(PageRequest.of(page, size, Sort.by(dir, sort))));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable UUID id) {
        userService.softDelete(id);
        return ResponseEntity.ok("Utilizador eliminado com sucesso");
    }
}