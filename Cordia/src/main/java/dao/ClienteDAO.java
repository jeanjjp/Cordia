package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import util.ConexaoBanco;
import model.Cirurgia;
import model.Cliente;
import model.Doenca;
import model.Remedio;


public class ClienteDAO {

	private Connection connection;

	public int incluirOuAlterarCliente(Cliente cliente) {
		
		int idInserido = 0;
		String sql;
		
		if (cliente.getIdCliente() <= 0) {
			sql = "INSERT INTO cliente (nome, sobrenome, telefone1, telefone2, email) VALUES (?, ?, ?, ?, ?)";
		} else {
			sql = "UPDATE cliente SET nome = ?, sobrenome = ?, telefone1 = ?, telefone2 = ?, email = ? WHERE idCliente = ?";
		}
		
		try {
			this.connection = new ConexaoBanco().getConnection();
			
			PreparedStatement stmt;
			
			if (cliente.getIdCliente() <= 0) {
				stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				// seta na string
				stmt.setString(1, cliente.getNomeCliente());
				stmt.setString(2, cliente.getSobrenomeCliente());
				stmt.setString(3, cliente.getTelefone1());
				stmt.setString(4, cliente.getTelefone2());
				stmt.setString(5, cliente.getEmail());
				// excuta
				stmt.executeUpdate();
				// retorna o ID inserido no BD
				ResultSet rs = stmt.getGeneratedKeys();
				// verifica
				if (rs.next()) {
					idInserido = rs.getInt(1);
				}

				if (idInserido > 0) {
					System.out.println("cliente inserida");
				} else {
					System.out.println("Erro ao inserir cliente DAO \n" + stmt);
				}
			} else {
				stmt = connection.prepareStatement(sql);

				stmt.setString(1, cliente.getNomeCliente());
				stmt.setString(2, cliente.getSobrenomeCliente());
				stmt.setString(3,  cliente.getTelefone1());
				stmt.setString(4,  cliente.getTelefone2());
				stmt.setString(5,  cliente.getEmail());
				stmt.setInt(6, cliente.getIdCliente());

				int ok = stmt.executeUpdate();

				if (ok == 1) {
					System.out.println("Cliente atualizada com sucesso no BD!");
					idInserido = cliente.getIdCliente();
				} else {
					System.out.println("Erro ao atualizar CLienteDAO no BD! \n" + stmt);
				}
			}
		
			stmt.close();

			return idInserido;
			
		} catch (SQLException e) {
			System.out.println("Erro ao inserir Cliente " +e);
			throw new RuntimeException(e);
		} finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}

	
	
	public ArrayList<Cliente> listarClientes(String filtro) {

		Cliente cliente;
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		String sql;
		if (filtro.equalsIgnoreCase("") || filtro == null) {
			sql = "SELECT * FROM cliente";
		}else{
			sql = "SELECT * FROM cliente WHERE nome LIKE '%"+filtro+"%' OR telefone1 LIKE '%"+filtro+"%' OR telefone2 LIKE '%"+filtro+"%' OR sobrenome LIKE '%"+filtro+"%' OR email LIKE '%"+filtro+"%'";
		}

		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			// executa
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("idCliente"));
				cliente.setNomeCliente(rs.getString("nome"));
				cliente.setSobrenomeCliente(rs.getString("sobrenome"));
				cliente.setTelefone1(rs.getString("telefone1"));
				cliente.setTelefone2(rs.getString("telefone2"));
				cliente.setEmail(rs.getString("email"));
				clientes.add(cliente);

			}
			System.out.println("Buscando Array de clientes");
			stmt.close();
			return clientes;
		} catch (SQLException e) {
			System.out.println("Erro ao buscar clientes no BD! " + e);
			return clientes;
		} finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}

	
	public boolean deletaCliente(int idCliente) {
		
		boolean removidoSucesso = false;
		String sql = "DELETE FROM cliente WHERE idCliente = ?";

		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			int ok = stmt.executeUpdate();

			if (ok == 1) {
				System.out.println("Cliente deletado com sucesso");
				removidoSucesso = true;
			} else {
				System.out.println("Erro ao deletar Cliente");
			}

			stmt.close();
			return removidoSucesso;
		} catch (SQLException e) {
			System.out.println("Erro ao remover Cliente no BD! "+e);
			throw new RuntimeException(e);
		}finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}

	

	
}
