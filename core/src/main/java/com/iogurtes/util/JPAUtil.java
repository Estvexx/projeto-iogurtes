package com.iogurtes.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("iogurtesPU");

    private JPAUtil() {}


    //  Devolve um novo EntityManager.

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

     // Fechar quando a aplicação terminar.

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}