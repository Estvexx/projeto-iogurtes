package com.empresa.iogurtes.gestaoiogurtes.core.controller;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.FornecedorCertificacaoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.service.FornecedorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fornecedores-certificacoes")
public class FornecedorCertificacaoController {

    private final FornecedorService fornecedorService;

    public FornecedorCertificacaoController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }
    @GetMapping
    public ResponseEntity<Page<FornecedorCertificacaoResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataInicio") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(fornecedorService.findAllCertificacoes(PageRequest.of(page, size, Sort.by(dir, sort))));
    }
}