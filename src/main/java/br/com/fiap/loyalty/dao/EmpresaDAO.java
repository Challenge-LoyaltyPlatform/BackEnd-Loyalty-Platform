package br.com.fiap.loyalty.dao;

import br.com.fiap.loyalty.db.ConnectionFactory;
import br.com.fiap.loyalty.model.Empresa;
import br.com.fiap.loyalty.model.StatusEmpresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {
        String sql = "INSERT INTO TB_EMPRESA (nome_empresa, cnpj_empresa, email_empresa, telefone_empresa, data_cadastro_empresa, status_empresa)"
                + " VALUES (?, ?, ?, ?, ?, ? )";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNomeEmpresa());
            stmt.setString(2, empresa.getCnpjEmpresa());
            stmt.setString(3, empresa.getEmail());
            stmt.setString(4, empresa.getTelefoneEmpresa());
            stmt.setDate(5, java.sql.Date.valueOf(empresa.getDataCadastro()));
            stmt.setString(6, empresa.getStatusEmpresa().name());
        }
    }

    public Empresa buscarPorId(int idEmpresa) {
        String sql = "SELECT * FROM TB_EMPRESA WHERE id_empresa = ?";
        Empresa empresa = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmpresa);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    empresa = montarEmpresa(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar empresa: " + e.getMessage());
        }
        return empresa;
    }

        String sql = "SELECT * FROM TB_EMPRESA ORDER BY id_empresa";
        List<Empresa> empresas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                empresas.add(montarEmpresa(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar empresas: " + e.getMessage());
        }
        return empresas;
    }

    public void atualizar(Empresa empresa) {
        String sql = "UPDATE TB_EMPRESA SET nome_empresa = ?, cnpj_empresa = ?, email_empresa = ?, "
                + "telefone_empresa = ?, data_cadastro_empresa = ?, status_empresa = ? WHERE id_empresa = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empresa.getNomeEmpresa());
            stmt.setString(2, empresa.getCnpjEmpresa());
            stmt.setString(3, empresa.getEmail());
            stmt.setString(4, empresa.getTelefoneEmpresa());
            stmt.setDate(5, java.sql.Date.valueOf(empresa.getDataCadastro()));
            stmt.setString(6, empresa.getStatusEmpresa().name());
            stmt.setInt(7, empresa.getId());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Empresa atualizada com sucesso!"
                    : "Nenhuma empresa encontrada com esse id.");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar empresa: " + e.getMessage());
        }
    }

    public void remover(int idEmpresa) {
        String sql = "DELETE FROM TB_EMPRESA WHERE id_empresa = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmpresa);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0
                    ? "Empresa removida com sucesso!"
                    : "Nenhuma empresa encontrada com esse id.");

        } catch (SQLException e) {
            System.out.println("Erro ao remover empresa: " + e.getMessage());
        }
    }

    // Converte a linha atual do ResultSet em um objeto Empresa
    private Empresa montarEmpresa(ResultSet rs) throws SQLException {
        return new Empresa(
                rs.getInt("id_empresa"),
                rs.getString("email_empresa"),
                rs.getDate("data_cadastro_empresa").toLocalDate(),
                rs.getString("nome_empresa"),
                rs.getString("cnpj_empresa"),
                rs.getString("telefone_empresa"),
                StatusEmpresa.valueOf(rs.getString("status_empresa"))
        );
    }
}