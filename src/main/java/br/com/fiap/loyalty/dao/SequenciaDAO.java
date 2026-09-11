package br.com.fiap.loyalty.dao;

import br.com.fiap.loyalty.db.ConnectionFactory;
import br.com.fiap.loyalty.model.Sequencia;
import br.com.fiap.loyalty.model.StatusSequencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Sequencia CRUD da TB_SEQUENCIA.
 * Chave composta (id_usuario + id_empresa).
 *
 * Regra importante: a tabela exige data_inicio_sequencia e ultimo_acesso
 * como NOT NULL, então só insere sequências que já tiveram pelo menos
 * um acesso registrado (ultimoAcesso != null). Sequência "vazia" (recém
 * criada em memória, sem nenhum registrarAcesso() chamado) não é persistida.
 */


public class SequenciaDAO {

    public void inserir(Sequencia sequencia) {
        if (sequencia.getUltimoAcesso() == null || sequencia.getDataInicioSequencia() == null) {
            System.out.println("Não é possível inserir: a sequência precisa ter pelo menos "
                    + "um acesso registrado (ultimoAcesso e dataInicioSequencia não podem ser nulos).");
            return;
        }

        String sql = "INSERT INTO TB_SEQUENCIA (TB_USUARIO_id_usuario, TB_EMPRESA_id_empresa, "
                + "dias_consecutivos, status_sequencia, data_inicio_sequencia, ultimo_acesso, "
                + "data_desativacao) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sequencia.getIdUsuario());
            stmt.setInt(2, sequencia.getIdEmpresa());
            stmt.setInt(3, sequencia.getDiasConsecutivos());
            stmt.setString(4, sequencia.getStatusSequencia().name());
            stmt.setDate(5, java.sql.Date.valueOf(sequencia.getDataInicioSequencia()));
            stmt.setTimestamp(6, Timestamp.valueOf(sequencia.getUltimoAcesso()));

            if (sequencia.getDataDesativacao() != null) {
                stmt.setDate(7, java.sql.Date.valueOf(sequencia.getDataDesativacao()));
            } else {
                stmt.setNull(7, Types.DATE);
            }

            stmt.executeUpdate();
            System.out.println("Sequência inserida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir sequência: " + e.getMessage());
        }
    }

    // Busca por chave composta — não existe um único id nessa tabela
    public Sequencia buscarPorChave(int idUsuario, int idEmpresa) {
        String sql = "SELECT * FROM TB_SEQUENCIA WHERE TB_USUARIO_id_usuario = ? "
                + "AND TB_EMPRESA_id_empresa = ?";
        Sequencia sequencia = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idEmpresa);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    sequencia = montarSequencia(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar sequência: " + e.getMessage());
        }
        return sequencia;
    }

    public List<Sequencia> listar() {
        String sql = "SELECT * FROM TB_SEQUENCIA ORDER BY TB_USUARIO_id_usuario, TB_EMPRESA_id_empresa";
        List<Sequencia> sequencias = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                sequencias.add(montarSequencia(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar sequências: " + e.getMessage());
        }
        return sequencias;
    }

    public void atualizar(Sequencia sequencia) {
        String sql = "UPDATE TB_SEQUENCIA SET dias_consecutivos = ?, status_sequencia = ?, "
                + "data_inicio_sequencia = ?, ultimo_acesso = ?, data_desativacao = ? "
                + "WHERE TB_USUARIO_id_usuario = ? AND TB_EMPRESA_id_empresa = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sequencia.getDiasConsecutivos());
            stmt.setString(2, sequencia.getStatusSequencia().name());
            stmt.setDate(3, java.sql.Date.valueOf(sequencia.getDataInicioSequencia()));
            stmt.setTimestamp(4, Timestamp.valueOf(sequencia.getUltimoAcesso()));

            if (sequencia.getDataDesativacao() != null) {
                stmt.setDate(5, java.sql.Date.valueOf(sequencia.getDataDesativacao()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setInt(6, sequencia.getIdUsuario());
            stmt.setInt(7, sequencia.getIdEmpresa());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Sequência atualizada com sucesso!"
                    : "Nenhuma sequência encontrada com essa chave.");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar sequência: " + e.getMessage());
        }
    }

    public void remover(int idUsuario, int idEmpresa) {
        String sql = "DELETE FROM TB_SEQUENCIA WHERE TB_USUARIO_id_usuario = ? "
                + "AND TB_EMPRESA_id_empresa = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idEmpresa);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Sequência removida com sucesso!"
                    : "Nenhuma sequência encontrada com essa chave.");

        } catch (SQLException e) {
            System.out.println("Erro ao remover sequência: " + e.getMessage());
        }
    }

    // Converte a linha atual do ResultSet em um objeto Sequencia
    private Sequencia montarSequencia(ResultSet rs) throws SQLException {
        java.sql.Date dataDesativacao = rs.getDate("data_desativacao");

        return new Sequencia(
                rs.getInt("TB_USUARIO_id_usuario"),
                rs.getInt("TB_EMPRESA_id_empresa"),
                rs.getInt("dias_consecutivos"),
                StatusSequencia.valueOf(rs.getString("status_sequencia")),
                rs.getDate("data_inicio_sequencia").toLocalDate(),
                rs.getTimestamp("ultimo_acesso").toLocalDateTime(),
                dataDesativacao != null ? dataDesativacao.toLocalDate() : null
        );
    }
}