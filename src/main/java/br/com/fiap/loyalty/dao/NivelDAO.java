package br.com.fiap.loyalty.dao;

import br.com.fiap.loyalty.db.ConnectionFactory;
import br.com.fiap.loyalty.model.Nivel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NivelDAO {
    public void inserir(Nivel nivel) {
        String sql = "INSERT INTO TB_NIVEL (nome_nivel, pontos_minimos_nivel, pontos_maximos_nivel) "
                + "VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nivel.getNomeNivel());
            stmt.setInt(2, nivel.getPontosMinNivel());
            stmt.setInt(3, nivel.getPontosMaxNivel());

            stmt.executeUpdate();
            System.out.println("Nível inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir nível: " + e.getMessage());
        }
    }

    public Nivel buscarPorId(int idNivel) {
        String sql = "SELECT * FROM TB_NIVEL WHERE id_nivel = ?";
        Nivel nivel = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idNivel);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nivel = montarNivel(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar nível: " + e.getMessage());
        }
        return nivel;
    }

    public List<Nivel> listar() {
        String sql = "SELECT * FROM TB_NIVEL ORDER BY id_nivel";
        List<Nivel> niveis = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                niveis.add(montarNivel(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar níveis: " + e.getMessage());
        }
        return niveis;
    }

    public void atualizar(Nivel nivel) {
        String sql = "UPDATE TB_NIVEL SET nome_nivel = ?, pontos_minimos_nivel = ?, "
                + "pontos_maximos_nivel = ? WHERE id_nivel = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nivel.getNomeNivel());
            stmt.setInt(2, nivel.getPontosMinNivel());
            stmt.setInt(3, nivel.getPontosMaxNivel());
            stmt.setInt(4, nivel.getIdNivel());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Nível atualizado com sucesso!"
                    : "Nenhum nível encontrado com esse id.");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar nível: " + e.getMessage());
        }
    }

    public void remover(int idNivel) {
        String sql = "DELETE FROM TB_NIVEL WHERE id_nivel = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idNivel);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Nível removido com sucesso!"
                    : "Nenhum nível encontrado com esse id.");

        } catch (SQLException e) {
            System.out.println("Erro ao remover nível: " + e.getMessage());
        }
    }

    // Converte a linha atual do ResultSet em um objeto Nivel
    private Nivel montarNivel(ResultSet rs) throws SQLException {
        return new Nivel(
                rs.getInt("id_nivel"),
                rs.getString("nome_nivel"),
                rs.getInt("pontos_minimos_nivel"),
                rs.getInt("pontos_maximos_nivel")
        );
    }
}
