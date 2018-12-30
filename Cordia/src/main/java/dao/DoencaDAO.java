package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Doenca;
import util.ConexaoBanco;

public class DoencaDAO {
	
	private Connection connection;

	
	public ArrayList<Doenca> obterDoenca(int idCliente) {

		Doenca doenca = null;
	
		ArrayList<Doenca> doencas = new ArrayList<Doenca>();
		
		String sql = "SELECT * FROM doenca WHERE idCliente = ?";

		System.out.println("Obtendo Doencas");
		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			// executa
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				doenca = new Doenca();
				doenca.setIdDoenca(rs.getInt("idDoenca"));
				doenca.setNomeDoenca(rs.getString("nomeDoenca"));		
		
				doencas.add(doenca);		
			
			}
		
			return doencas;

		} catch (SQLException e) {
			System.out.println("Erro ao buscar Doencas no BD!");
			e.printStackTrace();
			return null;
			
		} finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}
	
	
	
public boolean deletaDoenca(int idDoenca) {
		
		boolean removidoSucesso = false;
		String sql = "DELETE FROM doenca WHERE idDoenca = ?";

		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idDoenca);
			int ok = stmt.executeUpdate();

			if (ok == 1) {
				System.out.println("Doença deletado com sucesso");
				removidoSucesso = true;
			} else {
				System.out.println("Erro ao deletar doenca");
			}

			stmt.close();
			return removidoSucesso;
		} catch (SQLException e) {
			System.out.println("Erro ao remover doenca no BD! "+e);
			throw new RuntimeException(e);
		}finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}


public int incluirDoenca(Doenca doenca) {
	
	int idInserido = 0;
	String sql = "INSERT INTO doenca (idCliente, nomeDoenca) VALUES (?, ?)";
	
	try {
		this.connection = new ConexaoBanco().getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		// seta os valores
		
		stmt.setInt(1, doenca.getIdCliente());
		stmt.setString(2, doenca.getNomeDoenca());	
		
		// executa
		stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			idInserido = rs.getInt(1);
		}

		if (idInserido > 0) {
			System.out.println("Doenca inserida com sucesso");
		} else {
			System.out.println("Erro ao inserir Doenca");
		}

		stmt.close();

		return idInserido;
		
	} catch (SQLException e) {
		System.out.println("Erro ao inserir Doenca " +e);
		throw new RuntimeException(e);
	} finally {
		ConexaoBanco.fecharConexao(this.connection);
	}
}
	
	
}
