package br.com.fiap.loyalty.dao;

import br.com.fiap.loyalty.db.ConnectionFactory;
import br.com.fiap.loyalty.model.EmpresaUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de EmpresaUsuario — CRUD contra TB_EMPRESA_USUARIO.
 * Chave composta (id_empresa + id_usuario): não existe IDENTITY nem um único id,
 * então os métodos usam os dois valores juntos pra achar o registro
 */
public class EmpresaUsuarioDAO {

    public void inserir(EmpresaUsuario vinculo) {
        String sql = "INSERT INTO TB_EMPRESA_USUARIO (TB_EMPRESA_id_empresa, TB_USUARIO_id_usuario, "
                + "TB_NIVEL_id_nivel, pontuacao_acumulada, data_associacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vinculo.getIdEmpresa());
            stmt.setInt(2, vinculo.getIdUsuario());
            stmt.setInt(3, vinculo.getIdNivel());
            stmt.setInt(4, vinculo.getPontuacaoAcumulada());
            stmt.setDate(5, java.sql.Date.valueOf(vinculo.getDataAssociacao()));

            stmt.executeUpdate();
            System.out.println("Vínculo empresa-usuário inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir vínculo empresa-usuário: " + e.getMessage());
        }
    }

    // Busca por chave composta — não existe um único id nessa tabela
    public EmpresaUsuario buscarPorChave(int idEmpresa, int idUsuario) {
        String sql = "SELECT * FROM TB_EMPRESA_USUARIO WHERE TB_EMPRESA_id_empresa = ? "
                + "AND TB_USUARIO_id_usuario = ?";
        EmpresaUsuario vinculo = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmpresa);
            stmt.setInt(2, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    vinculo = montarEmpresaUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar vínculo empresa com usuário: " + e.getMessage());
        }
        return vinculo;
    }

    public List<EmpresaUsuario> listar() {
        String sql = "SELECT * FROM TB_EMPRESA_USUARIO ORDER BY TB_EMPRESA_id_empresa, TB_USUARIO_id_usuario";
        List<EmpresaUsuario> vinculos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vinculos.add(montarEmpresaUsuario(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar vínculos empresa com usuário: " + e.getMessage());
        }
        return vinculos;
    }

    public void atualizar(EmpresaUsuario vinculo) {
        String sql = "UPDATE TB_EMPRESA_USUARIO SET TB_NIVEL_id_nivel = ?, pontuacao_acumulada = ?, "
                + "data_associacao = ? WHERE TB_EMPRESA_id_empresa = ? AND TB_USUARIO_id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vinculo.getIdNivel());
            stmt.setInt(2, vinculo.getPontuacaoAcumulada());
            stmt.setDate(3, java.sql.Date.valueOf(vinculo.getDataAssociacao()));
            stmt.setInt(4, vinculo.getIdEmpresa());
            stmt.setInt(5, vinculo.getIdUsuario());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Vínculo empresa-usuário atualizado com sucesso!"
                    : "Nenhum vínculo encontrado com essa chave.");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar vínculo empresa com usuário: " + e.getMessage());
        }
    }

    public void remover(int idEmpresa, int idUsuario) {
        String sql = "DELETE FROM TB_EMPRESA_USUARIO WHERE TB_EMPRESA_id_empresa = ? "
                + "AND TB_USUARIO_id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmpresa);
            stmt.setInt(2, idUsuario);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Vínculo empresa-usuário removido com sucesso!"
                    : "Nenhum vínculo encontrado com essa chave.");

        } catch (SQLException e) {
            System.out.println("Erro ao remover vínculo empresacom usuário: " + e.getMessage());
        }
    }

    private EmpresaUsuario montarEmpresaUsuario(ResultSet rs) throws SQLException {
        return new EmpresaUsuario(
                rs.getInt("TB_USUARIO_id_usuario"),
                rs.getInt("TB_EMPRESA_id_empresa"),
                rs.getInt("TB_NIVEL_id_nivel"),
                rs.getInt("pontuacao_acumulada"),
                rs.getDate("data_associacao").toLocalDate()
        );
    }
}
