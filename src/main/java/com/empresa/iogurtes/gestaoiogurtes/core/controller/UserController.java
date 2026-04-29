package com.empresa.iogurtes.gestaoiogurtes.core.controller;
import com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto.*;
import com.empresa.iogurtes.gestaoiogurtes.core.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ── CREATE ──────────────────────────────────────────────
    @PostMapping("/funcionarios/mp")
    public ResponseEntity<UserResponse> createFuncionarioMP(@RequestBody CreateFuncionarioRequest request) {
        return ResponseEntity.status(201).body(userService.createFuncionarioMP(request));
    }

    @PostMapping("/funcionarios/op")
    public ResponseEntity<UserResponse> createFuncionarioOP(@RequestBody CreateFuncionarioRequest request) {
        return ResponseEntity.status(201).body(userService.createFuncionarioOP(request));
    }

    @PostMapping("/clientes")
    public ResponseEntity<UserResponse> createCliente(@RequestBody CreateClienteRequest request) {
        return ResponseEntity.status(201).body(userService.createCliente(request));
    }

    @PostMapping("/admins")
    public ResponseEntity<UserResponse> createAdmin(@RequestBody CreateAdminRequest request) {
        return ResponseEntity.status(201).body(userService.createAdmin(request));
    }

    @PostMapping("/gestores")
    public ResponseEntity<UserResponse> createGestor(@RequestBody CreateGestorRequest request) {
        return ResponseEntity.status(201).body(userService.createGestor(request));
    }

    // ── READ ─────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<UserResponse>> findAllFuncionarios() {
        return ResponseEntity.ok(userService.findAllFuncionarios());
    }

    @GetMapping("/funcionarios/mp")
    public ResponseEntity<List<UserResponse>> findAllFuncionariosMP() {
        return ResponseEntity.ok(userService.findAllFuncionarios_MP());
    }

    @GetMapping("/funcionarios/op")
    public ResponseEntity<List<UserResponse>> findAllFuncionariosOP() {
        return ResponseEntity.ok(userService.findAllFuncionarios_OP());
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<UserResponse>> findAllClientes() {
        return ResponseEntity.ok(userService.findAllClientes());
    }

    @GetMapping("/admins")
    public ResponseEntity<List<UserResponse>> findAllAdmins() {
        return ResponseEntity.ok(userService.findAllAdmins());
    }

    @GetMapping("/gestores")
    public ResponseEntity<List<UserResponse>> findAllGestores() {
        return ResponseEntity.ok(userService.findAllGestores());
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserResponse>> findAllActive() {
        return ResponseEntity.ok(userService.findAllActive());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<UserResponse>> findAllInactive() {
        return ResponseEntity.ok(userService.findAllInactive());
    }

    // ── UPDATE ───────────────────────────────────────────────
    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<UserResponse> updateFuncionario(@PathVariable UUID id,
                                                          @RequestBody UpdateFuncionarioRequest request) {
        return ResponseEntity.ok(userService.updateFuncionario(id, request));
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<UserResponse> updateCliente(@PathVariable UUID id,
                                                      @RequestBody UpdateClienteRequest request) {
        return ResponseEntity.ok(userService.updateCliente(id, request));
    }

    @PutMapping("/admins/{id}")
    public ResponseEntity<UserResponse> updateAdmin(@PathVariable UUID id,
                                                    @RequestBody UpdateAdminRequest request) {
        return ResponseEntity.ok(userService.updateAdmin(id, request));
    }

    @PutMapping("/gestores/{id}")
    public ResponseEntity<UserResponse> updateGestor(@PathVariable UUID id,
                                                     @RequestBody UpdateGestorRequest request) {
        return ResponseEntity.ok(userService.updateGestor(id, request));
    }

    // ── DELETE ───────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable UUID id) {
        userService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}