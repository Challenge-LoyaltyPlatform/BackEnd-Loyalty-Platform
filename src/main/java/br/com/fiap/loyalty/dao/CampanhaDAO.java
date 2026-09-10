package br.com.fiap.loyalty.dao;

import br.com.fiap.loyalty.db.ConnectionFactory;
import br.com.fiap.loyalty.model.Campanha;
import br.com.fiap.loyalty.model.StatusCampanha;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CampanhaDAO {

    public void inserir(Campanha campanha) {
        String sql = "INSERT INTO TB_CAMPANHA (nome_campanha, descricao_campanha, data_inicio_campanha, "
                + "data_fim_campanha, status_campanha, TB_EMPRESA_id_empresa) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, campanha.getNomeCampanha());
            stmt.setString(2, campanha.getDescricaoCampanha());
            stmt.setDate(3, java.sql.Date.valueOf(campanha.getDataIniCampanha()));

            if (campanha.getDataFimCampanha() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(campanha.getDataFimCampanha()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setString(5, campanha.getStatusCampanha().name());
            stmt.setInt(6, campanha.getIdEmpresa());

            stmt.executeUpdate();
            System.out.println("Campanha inserida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir campanha: " + e.getMessage());
        }
    }

    public Campanha buscarPorId(int idCampanha) {
        String sql = "SELECT * FROM TB_CAMPANHA WHERE id_campanha = ?";
        Campanha campanha = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCampanha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    campanha = montarCampanha(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar campanha: " + e.getMessage());
        }
        return campanha;
    }

    public List<Campanha> listar() {
        String sql = "SELECT * FROM TB_CAMPANHA ORDER BY id_campanha";
        List<Campanha> campanhas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                campanhas.add(montarCampanha(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar campanhas: " + e.getMessage());
        }
        return campanhas;
    }

    public void atualizar(Campanha campanha) {
        String sql = "UPDATE TB_CAMPANHA SET nome_campanha = ?, descricao_campanha = ?, "
                + "data_inicio_campanha = ?, data_fim_campanha = ?, status_campanha = ?, "
                + "TB_EMPRESA_id_empresa = ? WHERE id_campanha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, campanha.getNomeCampanha());
            stmt.setString(2, campanha.getDescricaoCampanha());
            stmt.setDate(3, java.sql.Date.valueOf(campanha.getDataIniCampanha()));

            if (campanha.getDataFimCampanha() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(campanha.getDataFimCampanha()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setString(5, campanha.getStatusCampanha().name());
            stmt.setInt(6, campanha.getIdEmpresa());
            stmt.setInt(7, campanha.getIdCampanha());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Campanha atualizada com sucesso!"
                    : "Nenhuma campanha encontrada com esse id.");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar campanha: " + e.getMessage());
        }
    }

    public void remover(int idCampanha) {
        String sql = "DELETE FROM TB_CAMPANHA WHERE id_campanha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCampanha);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Campanha removida com sucesso!"
                    : "Nenhuma campanha encontrada com esse id.");

        } catch (SQLException e) {
            System.out.println("Erro ao remover campanha: " + e.getMessage());
        }
    }

    private Campanha montarCampanha(ResultSet rs) throws SQLException {
        java.sql.Date dataFim = rs.getDate("data_fim_campanha");

        return new Campanha(
                rs.getInt("id_campanha"),
                rs.getInt("TB_EMPRESA_id_empresa"),
                rs.getString("nome_campanha"),
                rs.getString("descricao_campanha"),
                rs.getDate("data_inicio_campanha").toLocalDate(),
                dataFim != null ? dataFim.toLocalDate() : null,
                StatusCampanha.valueOf(rs.getString("status_campanha"))
        );
    }
}