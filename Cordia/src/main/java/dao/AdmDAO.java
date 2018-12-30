package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Adm;
import util.ConexaoBanco;

public class AdmDAO {

	private Connection connection;
	
	
	public Adm verificaLogin(String email, String senha) {
		
		Adm adm = null;
		String sql = "SELECT * FROM adm  WHERE email = ? AND senha = ?";

		System.out.println("Verificando Login");
		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, senha);
			// executa
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				adm = new Adm();
				adm.setIdAdm(rs.getInt("idAdm"));
				adm.setNome(rs.getString("nome"));
				adm.setSobrenome(rs.getString("sobrenome"));
				adm.setEmail(rs.getString("email"));
				adm.setSenha(rs.getString("senha"));
				
			}
			stmt.close();
			

		} catch (SQLException e) {
			System.out.println("Erro ao verificar Login no BD!");
			e.printStackTrace();
		} finally {
			ConexaoBanco.fecharConexao(this.connection);
		}

		return adm;
	}
}
