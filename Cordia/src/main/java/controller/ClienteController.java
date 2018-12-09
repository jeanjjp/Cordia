package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.ClienteDAO;
import model.Cliente;


@ManagedBean
public class ClienteController implements Serializable {

	private static final long serialVersionUID = 3788235120261465931L;
	
	private ArrayList<Cliente> listaDeClientes;
	private ClienteDAO clienteDAO;
	private Cliente cliente = new Cliente();
	private String IetmDeBusca="";
	
	
	@PostConstruct
	public void listarClientes(){
		clienteDAO = new ClienteDAO();
		this.setListaDeClientes(clienteDAO.listarClientes(IetmDeBusca));
		clienteDAO = null;
	}

	
	public String cadastrarOuAlterarCliente(){
		clienteDAO = new ClienteDAO();
		if(this.clienteDAO.incluirOuAlterarCliente(getCliente()) > 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente cadastrada com sucesso.",""));
			clienteDAO = null;
			cliente = null;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar Cliente.",""));
			clienteDAO = null;
			cliente = null;
		}
		listarClientes();
		return"index.xhtml";
	}


	public ArrayList<Cliente> getListaDeClientes() {
		return listaDeClientes;
	}



	public void setListaDeClientes(ArrayList<Cliente> listaDeClientes) {
		this.listaDeClientes = listaDeClientes;
	}



	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}



	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	public String getIetmDeBusca() {
		return IetmDeBusca;
	}



	public void setIetmDeBusca(String ietmDeBusca) {
		IetmDeBusca = ietmDeBusca;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
}
