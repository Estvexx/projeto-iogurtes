    package com.empresa.iogurtes.gestaoiogurtes.core.repository;

    import com.empresa.iogurtes.gestaoiogurtes.core.model.MateriaPrima;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.UUID;

    @Repository
    public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, UUID> {

        boolean existsByNome(String nome);

    }