    package com.empresa.iogurtes.gestaoiogurtes.core.repository;

    import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaPrima;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.Optional;
    import java.util.UUID;

    @Repository
    public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, UUID> {

        Optional<MateriaPrima> findByIdAndIsActiveIsTrue(UUID id);

        boolean existsByNomeIgnoreCase(String nome);

        boolean existsByNomeIgnoreCaseAndIdNot(String nome, UUID id);

        boolean existsByTipo_IdAndIsActiveTrue(UUID tipoId);

        Page<MateriaPrima> findAllByIsActiveTrue(Pageable pageable);

        Page<MateriaPrima> findAllByIsActiveFalse(Pageable pageable);
    }