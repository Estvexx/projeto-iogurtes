package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.*;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.*;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmpresaRepository empresaRepository;
    private final EncomendaRepository encomendaRepository;
    private final EncomendaPalletRepository encomendaPalletRepository;
    private final EncomendaOrdemRepository encomendaOrdemRepository;
    private final OrdemProducaoRepository ordemProducaoRepository;
    private final MovimentoStockMPRepository movimentoStockMPRepository;

    public UserService(UserRepository userRepository,
                       UserValidator userValidator,
                       BCryptPasswordEncoder passwordEncoder,
                       EmpresaRepository empresaRepository,
                       EncomendaRepository encomendaRepository,
                       EncomendaPalletRepository encomendaPalletRepository,
                       EncomendaOrdemRepository encomendaOrdemRepository,
                       OrdemProducaoRepository ordemProducaoRepository,
                       MovimentoStockMPRepository movimentoStockMPRepository) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
        this.empresaRepository = empresaRepository;
        this.encomendaRepository = encomendaRepository;
        this.encomendaPalletRepository = encomendaPalletRepository;
        this.encomendaOrdemRepository = encomendaOrdemRepository;
        this.ordemProducaoRepository = ordemProducaoRepository;
        this.movimentoStockMPRepository = movimentoStockMPRepository;
    }

    @Transactional
    public User createUser(String nome,
                           String email,
                           String password,
                           String turno,
                           LocalDate dataAdmissao,
                           List<String> roles,
                           UUID empresaId
                            ) {

        TurnoTipo turnoTipo = userValidator.validateAndParseTurno(turno);
        List<UserRole> userRoles = userValidator.validateAndParseRoles(roles);

        userValidator.validateCreateUser(nome, email,password, turnoTipo, dataAdmissao, userRoles, empresaId);

        String passwordHash = passwordEncoder.encode(password);
        // o getReferenceById é só para passar como objeto empresa o uuid, assim nao retorno o objeto inteiro, evito queries
        Empresa empresa = empresaId != null ? empresaRepository.getReferenceById(empresaId) : null;

        User user = new User(
                empresa,
                nome,
                email,
                passwordHash,
                turnoTipo,
                dataAdmissao
        );

        for (UserRole role : userRoles) {
            role.setUser(user);
        }

        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UUID id, String nome, String turno, List<String> roles) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado"));

        TurnoTipo turnoTipo = userValidator.validateAndParseTurno(turno);
        List<UserRole> userRoles = userValidator.validateAndParseRoles(roles);
        userValidator.validateUpdateUser(nome, turnoTipo, userRoles);

        user.getRoles().clear();
        userRepository.flush();

        for (UserRole role : userRoles) {
            role.setUser(user);
            user.getRoles().add(role);
        }

        user.setNome(nome);
        user.setTurno(turnoTipo);

        return userRepository.save(user);
    }

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado"));
    }

    public List<User> getAll() {
        return userRepository.findAllByIsActiveTrue();
    }

    public List<User> getAllIncludingInactive() {
        return userRepository.findAll();
    }

    @Transactional
    public void delete(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado"));

        ordemProducaoRepository.findByUserId(id)
                .forEach(ordem -> {
                    ordem.getProdutos().forEach(produto -> produto.softDelete());
                    ordem.getConsumos().forEach(consumo -> consumo.softDelete());
                    encomendaOrdemRepository.findByOrdemId(ordem.getId())
                            .forEach(eo -> {
                                eo.softDelete();
                                encomendaOrdemRepository.save(eo);
                            });
                    ordem.softDelete();
                    ordemProducaoRepository.save(ordem);
                });

        encomendaRepository.findByUserId(id)
                .forEach(encomenda -> {
                    for (EncomendaPallet pallet : encomenda.getPallets()) {
                        for (EncomendaOrdem encomendaOrdem : pallet.getOrdens()) {
                            encomendaOrdem.softDelete();
                            encomendaOrdemRepository.save(encomendaOrdem);
                        }
                        pallet.softDelete();
                        encomendaPalletRepository.save(pallet);
                    }
                    encomenda.softDelete();
                    encomendaRepository.save(encomenda);
                });

        movimentoStockMPRepository.findByUserId(id)
                .forEach(movimento -> {
                    movimento.softDelete();
                    movimentoStockMPRepository.save(movimento);
                });

        for (UserRole role : user.getRoles()) {
            role.softDelete();
        }

        user.softDelete();
        userRepository.save(user);
    }
}