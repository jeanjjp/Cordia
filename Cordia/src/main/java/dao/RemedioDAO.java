package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Remedio;
import util.ConexaoBanco;

public class RemedioDAO {
	
	private Connection connection;

	
	public ArrayList<Remedio> obterRemedios(int idCliente) {

		Remedio remedio = null;
	
		ArrayList<Remedio> remedios = new ArrayList<Remedio>();
		
		String sql = "SELECT * FROM remedio WHERE idCliente = ?";

		System.out.println("Obtendo remedios");
		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			// executa
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				remedio = new Remedio();
				remedio.setIdRemedio(rs.getInt("idRemedio"));
				remedio.setNomeRemedio(rs.getString("nomeRemedio"));		
		
				remedios.add(remedio);		
			
			}
		
			return remedios;

		} catch (SQLException e) {
			System.out.println("Erro ao buscar Remedios no BD!");
			e.printStackTrace();
			return null;
			
		} finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}
	
	
	
public boolean deletaRemedios(int idRemedio) {
		
		boolean removidoSucesso = false;
		String sql = "DELETE FROM remedio WHERE idRemedio = ?";

		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idRemedio);
			int ok = stmt.executeUpdate();

			if (ok == 1) {
				System.out.println("Remedio deletado com sucesso");
				removidoSucesso = true;
			} else {
				System.out.println("Erro ao deletar Remedio");
			}

			stmt.close();
			return removidoSucesso;
		} catch (SQLException e) {
			System.out.println("Erro ao remover Remedio no BD! "+e);
			throw new RuntimeException(e);
		}finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}


public int incluirRemedio(Remedio remedio) {
	
	int idInserido = 0;
	String sql = "INSERT INTO remedio (idCliente, nomeRemedio) VALUES (?, ?)";
	
	try {
		this.connection = new ConexaoBanco().getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		// seta os valores
		
		stmt.setInt(1, remedio.getIdCliente());
		stmt.setString(2, remedio.getNomeRemedio());	
		
		// executa
		stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			idInserido = rs.getInt(1);
		}

		if (idInserido > 0) {
			System.out.println("Remedio inserida com sucesso");
		} else {
			System.out.println("Erro ao inserir Eemedio");
		}

		stmt.close();

		return idInserido;
		
	} catch (SQLException e) {
		System.out.println("Erro ao inserir Remedio " +e);
		throw new RuntimeException(e);
	} finally {
		ConexaoBanco.fecharConexao(this.connection);
	}
}
	
	
}
