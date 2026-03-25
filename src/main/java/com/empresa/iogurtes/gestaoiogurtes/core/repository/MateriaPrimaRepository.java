    package com.empresa.iogurtes.gestaoiogurtes.core.repository;

    import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaPrima;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;
    import java.util.UUID;

    @Repository
    public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, UUID> {

        List<MateriaPrima> findAllByIsActiveTrue();
        List<MateriaPrima> findByFornecedorId(UUID fornecedorId);
        boolean existsByNome(String nome);

    }