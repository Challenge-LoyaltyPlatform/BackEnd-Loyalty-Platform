package br.com.fiap.loyalty.test;

import br.com.fiap.loyalty.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestaConexao {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("Conexão aberta com sucesso!");
            System.out.println("Autocommit: " + conn.getAutoCommit());
        } catch (SQLException e) {
            System.out.println("Falha na conexão: " + e.getMessage());
        }
    }
}
