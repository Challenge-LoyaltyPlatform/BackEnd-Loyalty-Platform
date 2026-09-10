package br.com.fiap.loyalty.dao;

import br.com.fiap.loyalty.db.ConnectionFactory;
import br.com.fiap.loyalty.model.Missao;
import br.com.fiap.loyalty.model.StatusMissao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MissaoDAO {

    public void inserir(Missao missao) {
        String sql = "INSERT INTO TB_MISSAO (nome_missao, descricao_missao, meta_missao, "
                + "pontos_recompensa_missao, status_missao, TB_CAMPANHA_id_campanha) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, missao.getNomeMissao());
            stmt.setString(2, missao.getDescricaoMissao());
            stmt.setInt(3, missao.getMetaMissao());
            stmt.setInt(4, missao.getPontosRecompensaMissao());
            stmt.setString(5, missao.getStatusMissao().name());
            stmt.setInt(6, missao.getIdCampanha());

            stmt.executeUpdate();
            System.out.println("Missão inserida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir missão: " + e.getMessage());
        }
    }

    public Missao buscarPorId(int idMissao) {
        String sql = "SELECT * FROM TB_MISSAO WHERE id_missao = ?";
        Missao missao = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMissao);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    missao = montarMissao(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar missão: " + e.getMessage());
        }
        return missao;
    }

    public List<Missao> listar() {
        String sql = "SELECT * FROM TB_MISSAO ORDER BY id_missao";
        List<Missao> missoes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                missoes.add(montarMissao(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar missões: " + e.getMessage());
        }
        return missoes;
    }

    public void atualizar(Missao missao) {
        String sql = "UPDATE TB_MISSAO SET nome_missao = ?, descricao_missao = ?, meta_missao = ?, "
                + "pontos_recompensa_missao = ?, status_missao = ?, TB_CAMPANHA_id_campanha = ? "
                + "WHERE id_missao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, missao.getNomeMissao());
            stmt.setString(2, missao.getDescricaoMissao());
            stmt.setInt(3, missao.getMetaMissao());
            stmt.setInt(4, missao.getPontosRecompensaMissao());
            stmt.setString(5, missao.getStatusMissao().name());
            stmt.setInt(6, missao.getIdCampanha());
            stmt.setInt(7, missao.getIdMissao());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Missão atualizada com sucesso!"
                    : "Nenhuma missão encontrada com esse id.");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar missão: " + e.getMessage());
        }
    }

    public void remover(int idMissao) {
        String sql = "DELETE FROM TB_MISSAO WHERE id_missao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMissao);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Missão removida com sucesso!"
                    : "Nenhuma missão encontrada com esse id.");

        } catch (SQLException e) {
            System.out.println("Erro ao remover missão: " + e.getMessage());
        }
    }

    private Missao montarMissao(ResultSet rs) throws SQLException {
        return new Missao(
                rs.getInt("id_missao"),
                rs.getInt("TB_CAMPANHA_id_campanha"),
                rs.getString("nome_missao"),
                rs.getString("descricao_missao"),
                rs.getInt("meta_missao"),
                rs.getInt("pontos_recompensa_missao"),
                StatusMissao.valueOf(rs.getString("status_missao"))
        );
    }
}