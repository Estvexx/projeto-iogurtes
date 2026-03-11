package com.iogurtes.util;

import jakarta.persistence.EntityManager;

public class TesteHibernate {

    public static void main(String[] args) {

        System.out.println("A iniciar Hibernate...");

        EntityManager em = JPAUtil.getEntityManager();

        if (em != null) {
            System.out.println("✅ Hibernate iniciado com sucesso!");
            System.out.println("✅ Tabelas criadas/atualizadas na BD!");
            em.close();
        }

        JPAUtil.close();
    }
}