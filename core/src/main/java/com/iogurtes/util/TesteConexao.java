package com.iogurtes.util;

import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {

        System.out.println("A tentar ligar à BD...");

        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println("✅ Ligação bem sucedida!");
            DatabaseConnection.closeConnection();
        } else {
            System.out.println("❌ Falhou a ligação!");
        }
    }
}
