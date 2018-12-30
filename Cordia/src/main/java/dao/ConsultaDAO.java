package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Cliente;
import model.Consulta;
import util.ConexaoBanco;

public class ConsultaDAO {
	
	private Connection connection;

	
	public ArrayList<Consulta> obterConsultas(int idCliente) {

		Consulta consulta = null;
	
		ArrayList<Consulta> consultas = new ArrayList<Consulta>();
		
		String sql = "SELECT * FROM consulta WHERE idCliente = ? ORDER BY dataConsulta DESC";

		System.out.println("Obtendo consultas");
		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			// executa
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				consulta = new Consulta();
				consulta.setIdConsulta(rs.getInt("idConsulta"));
				consulta.setIdCliente(rs.getInt("idCliente"));
				consulta.setQueixaPrimaria(rs.getString("queixaPrincipal"));		
				consulta.setQueixaSecundaria(rs.getString("queixaSecundaria"));
				consulta.setResultado(rs.getString("conclusaoConsulta"));
				consulta.setDataConsulta(rs.getDate("dataConsulta"));
				consulta.setAvaliacao(rs.getString("avaliacao"));
		
				consultas.add(consulta);		
			
			}
		
			return consultas;

		} catch (SQLException e) {
			System.out.println("Erro ao buscar Consultas no BD!");
			e.printStackTrace();
			return null;
			
		} finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}
	
	
	
public boolean deletaConsulta(int idConsulta) {
		
		boolean removidoSucesso = false;
		String sql = "DELETE FROM consulta WHERE idConsulta = ?";

		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idConsulta);
			int ok = stmt.executeUpdate();

			if (ok == 1) {
				System.out.println("Consulta deletado com sucesso");
				removidoSucesso = true;
			} else {
				System.out.println("Erro ao deletar Consulta");
			}

			stmt.close();
			return removidoSucesso;
		} catch (SQLException e) {
			System.out.println("Erro ao remover Consulta no BD! "+e);
			throw new RuntimeException(e);
		}finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}


public int incluirConsulta(Consulta consulta) {
	
	int idInserido = 0;
	String sql = "INSERT INTO consulta (idCliente, queixaPrincipal, queixaSecundaria, conclusaoConsulta, dataConsulta, avaliacao) VALUES (?, ?, ?, ?, ?, ?)";
	java.sql.Date dataAtual = new java.sql.Date(System.currentTimeMillis());
	try {
		this.connection = new ConexaoBanco().getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		// seta os valores
		
		stmt.setInt(1, consulta.getIdCliente());
		stmt.setString(2, consulta.getQueixaPrimaria());	
		stmt.setString(3, consulta.getQueixaSecundaria());
		stmt.setString(4, consulta.getResultado());
		stmt.setDate(5, dataAtual);
		stmt.setString(6,  consulta.getAvaliacao());
		
		// executa
		stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			idInserido = rs.getInt(1);
		}

		if (idInserido > 0) {
			System.out.println("Consulta inserida com sucesso");
		} else {
			System.out.println("Erro ao inserir Consulta");
		}

		stmt.close();

		return idInserido;
		
	} catch (SQLException e) {
		System.out.println("Erro ao inserir Consulta " +e);
		throw new RuntimeException(e);
	} finally {
		ConexaoBanco.fecharConexao(this.connection);
	}
}

public boolean atualizarConsulta(Consulta consulta) {

	boolean atualizadoSucesso = false;
	String sql = "UPDATE consulta SET queixaPrincipal = ?, queixaSecundaria = ?, conclusaoConsulta = ?, avaliacao = ? WHERE idConsulta = ?";
	
	
	try {
		this.connection = new ConexaoBanco().getConnection();
		PreparedStatement stmt = connection.prepareStatement(sql);

		// seta os valores

		stmt.setString(1, consulta.getQueixaPrimaria());
		stmt.setString(2, consulta.getQueixaSecundaria());
		stmt.setString(3, consulta.getResultado());
		stmt.setString(4, consulta.getAvaliacao());
		stmt.setInt(5, consulta.getIdConsulta());
		
		// executa
		int ok = stmt.executeUpdate();

		if (ok == 1) {
			System.out.println("Consulta atualizada com sucesso no BD!");
			atualizadoSucesso = true;
		} else {
			System.out.println("Erro ao atualizar Consulta no BD! \n"+ stmt);
		}

		stmt.close();

		return atualizadoSucesso;
	} catch (SQLException e) {
		System.out.println("Erro ao atualizar Consulta no BD! "+e);
		throw new RuntimeException(e);
	} finally {
		ConexaoBanco.fecharConexao(this.connection);
	}
}

	
	
}
