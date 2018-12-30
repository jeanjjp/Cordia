package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Cirurgia;
import util.ConexaoBanco;

public class CirurgiaDAO {
	
	private Connection connection;

	
	public ArrayList<Cirurgia> obterCirurgias(int idCliente) {

		Cirurgia cirurgia = null;
	
		ArrayList<Cirurgia> cirurgias = new ArrayList<Cirurgia>();
		
		String sql = "SELECT * FROM cirurgia WHERE idCliente = ?";

		System.out.println("Obtendo cirurgias");
		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			// executa
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				cirurgia = new Cirurgia();
				cirurgia.setIdCirurgia(rs.getInt("idCirurgia"));
				cirurgia.setNomeCirigia(rs.getString("nomeCirurgia"));		
		
				cirurgias.add(cirurgia);		
			
			}
		
			return cirurgias;

		} catch (SQLException e) {
			System.out.println("Erro ao buscar Remedios no BD!");
			e.printStackTrace();
			return null;
			
		} finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}
	
	
	
public boolean deletaCirurgia(int idCirurgia) {
		
		boolean removidoSucesso = false;
		String sql = "DELETE FROM cirurgia WHERE idCirurgia = ?";

		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idCirurgia);
			int ok = stmt.executeUpdate();

			if (ok == 1) {
				System.out.println("Cirurgia deletado com sucesso");
				removidoSucesso = true;
			} else {
				System.out.println("Erro ao deletar Cirurgia");
			}

			stmt.close();
			return removidoSucesso;
		} catch (SQLException e) {
			System.out.println("Erro ao remover Cirurgia no BD! "+e);
			throw new RuntimeException(e);
		}finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}


public int incluirCirurgia(Cirurgia cirurgia) {
	
	int idInserido = 0;
	String sql = "INSERT INTO cirurgia (idCliente, nomeCirurgia) VALUES (?, ?)";
	
	try {
		this.connection = new ConexaoBanco().getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		// seta os valores
		
		stmt.setInt(1, cirurgia.getIdCliente());
		stmt.setString(2, cirurgia.getNomeCirigia());	
		
		// executa
		stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			idInserido = rs.getInt(1);
		}

		if (idInserido > 0) {
			System.out.println("Cirurgia inserida com sucesso");
		} else {
			System.out.println("Erro ao inserir Cirurgia");
		}

		stmt.close();

		return idInserido;
		
	} catch (SQLException e) {
		System.out.println("Erro ao inserir Cirurgia " +e);
		throw new RuntimeException(e);
	} finally {
		ConexaoBanco.fecharConexao(this.connection);
	}
}
	
	
}
