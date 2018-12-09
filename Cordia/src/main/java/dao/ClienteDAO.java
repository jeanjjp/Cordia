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
import model.Cliente;


public class ClienteDAO {

	private Connection connection;

	public int incluirOuAlterarCliente(Cliente cliente) {
		
		int idInserido = 0;
		String sql;
		
		if (cliente.getIdCliente() <= 0) {
			sql = "INSERT INTO cliente (nome, sobrenome) VALUES (?, ?)";
		} else {
			sql = "UPDATE cliente SET nome = ?, sobrenome = ? WHERE idCliente = ?";
		}
		
		try {
			this.connection = new ConexaoBanco().getConnection();
			
			PreparedStatement stmt;
			
			if (cliente.getIdCliente() <= 0) {
				stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				// seta na string
				stmt.setString(1, cliente.getNomeCliente());
				stmt.setString(2, cliente.getSobrenomeCliente());
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
				stmt.setInt(3, cliente.getIdCliente());

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

	public boolean atualizarCliente(Cliente cliente) {

		java.sql.Date dataClienteSQL = null;
		boolean atualizadoSucesso = false;
		String sql = "UPDATE cliente SET nome_cliente = ?, sobrenome_cliente = ?, data_cliente = ?, qt_codigo_gratis = ? WHERE id_cliente = ?";
		
		
		
		
		//Estou adicionando mais um dia na data de nascimento do cliente.
		//por quê por agum motivo a data inserida vai comuma dia a menos e eu não sei omotivo.
		//tentar arrumar isso futuramente. 
		//SQN.
		
		//
		
		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores

			stmt.setString(1, cliente.getNomeCliente());
			stmt.setString(2, cliente.getSobrenomeCliente());
			stmt.setDate(3, dataClienteSQL);

			stmt.setInt(5, cliente.getIdCliente());
			
			// executa
			int ok = stmt.executeUpdate();

			if (ok == 1) {
				System.out.println("Cliente atualizado com sucesso no BD!");
				atualizadoSucesso = true;
			} else {
				System.out.println("Erro ao atualizar liente no BD! \n"+ stmt);
			}

			stmt.close();

			return atualizadoSucesso;
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar Pessoa no BD! "+e);
			throw new RuntimeException(e);
		} finally {
			ConexaoBanco.fecharConexao(this.connection);
		}
	}

	
	public Cliente obterCliente(int idCliente) {
		Cliente cliente = null;
		
		String sql = "SELECT * FROM cliente as c INNER JOIN endereco as e ON c.id_cliente = e.endereco_id_cliente INNER JOIN contato as con ON c.id_cliente = con.contato_id_cliente WHERE id_cliente = ?";

		System.out.println("Obtendo Cliente");
		try {
			this.connection = new ConexaoBanco().getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setMaxRows(1);
			stmt.setInt(1, idCliente);
			// executa
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("id_cliente"));
				cliente.setNomeCliente(rs.getString("nome_cliente"));
				cliente.setSobrenomeCliente(rs.getString("sobrenome_cliente"));
				cliente.setEmail(rs.getString("email_cliente"));
				
			
			}

			
			return cliente;

		} catch (SQLException e) {
			System.out.println("Erro ao buscar Cliente no BD!");
			e.printStackTrace();
			return null;
			
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
			sql = "SELECT * FROM cliente WHERE nome LIKE '%"+filtro+"%'";
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

	
	

	

	
}
