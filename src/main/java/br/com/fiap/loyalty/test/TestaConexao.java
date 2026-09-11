package br.com.fiap.loyalty.test;

import br.com.fiap.loyalty.dao.*;
import br.com.fiap.loyalty.db.ConnectionFactory;
import br.com.fiap.loyalty.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class TestaConexao {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("Conexão aberta com sucesso!");
            System.out.println("Autocommit: " + conn.getAutoCommit());
        } catch (SQLException e) {
            System.out.println("Falha na conexão: " + e.getMessage());
        }


        // --- CRIAÇÃO DE DADOS FICTICIA PARA TESTAR: roda uma vez, depois apaga esse bloco ---
        //new NivelDAO().inserir(new Nivel(0, "Bronze", 0, 99));
        //new NivelDAO().inserir(new Nivel(0, "Prata", 100, 499));
        //new NivelDAO().inserir(new Nivel(0, "Platina", 500, 1000));
        //new EmpresaDAO().inserir(new Empresa(0, "contato@soulup.com.br",
        //LocalDate.of(2026, 1, 15), "SoulUp", "12345678000199",
        //"(11) 3333-4444", StatusEmpresa.ATIVA));
        //new UsuarioDAO().inserir(new Usuario(0, "Andrei Sousa",
        //"andrei@email.com", "senha123", LocalDate.of(2026, 3, 1)));
        //new EmpresaUsuarioDAO().inserir(new EmpresaUsuario(1, 1, 1, 80, LocalDate.of(2026, 3, 1)));
        //new CampanhaDAO().inserir(new Campanha(0, 1, "Campanha de Indicação",
        // "Indique amigos e ganhe pontos", LocalDate.of(2026, 9, 1),
        // LocalDate.of(2026, 12, 31), StatusCampanha.ATIVA));
        //new MissaoDAO().inserir(new Missao(0, 1, "Missão de Influência",
        //"Compartilhe 20 vídeos com 3 amigos", 20, 50, StatusMissao.ATIVA));
        // --- FIM DO SEED ---
    }
}
