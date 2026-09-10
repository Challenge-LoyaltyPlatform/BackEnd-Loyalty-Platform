package br.com.fiap.loyalty.dao;

import br.com.fiap.loyalty.db.ConnectionFactory;
import br.com.fiap.loyalty.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO TB_USUARIO (nome_usuario, email_usuario, senha_usuario, data_cadastro) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaUsuario());
            stmt.setDate(4, java.sql.Date.valueOf(usuario.getDataCadastro()));

            stmt.executeUpdate();
            System.out.println("Usuário inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    public Usuario buscarPorId(int idUsuario) {
        String sql = "SELECT * FROM TB_USUARIO WHERE id_usuario = ?";
        Usuario usuario = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = montarUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return usuario;
    }

    public List<Usuario> listar() {
        String sql = "SELECT * FROM TB_USUARIO ORDER BY id_usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(montarUsuario(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE TB_USUARIO SET nome_usuario = ?, email_usuario = ?, senha_usuario = ?, "
                + "data_cadastro = ? WHERE id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaUsuario());
            stmt.setDate(4, java.sql.Date.valueOf(usuario.getDataCadastro()));
            stmt.setInt(5, usuario.getId());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Usuário atualizado com sucesso!"
                    : "Nenhum usuário encontrado com esse id.");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void remover(int idUsuario) {
        String sql = "DELETE FROM TB_USUARIO WHERE id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Usuário removido com sucesso!"
                    : "Nenhum usuário encontrado com esse id.");

        } catch (SQLException e) {
            System.out.println("Erro ao remover usuário: " + e.getMessage());
        }
    }

    // Converte a linha atual do ResultSet em um objeto Usuario
    private Usuario montarUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nome_usuario"),
                rs.getString("email_usuario"),
                rs.getString("senha_usuario"),
                rs.getDate("data_cadastro").toLocalDate()
        );
    }
}
