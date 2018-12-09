package model;

import java.util.ArrayList;

public class Cliente {

	private int idCliente;
	private String nomeCliente;
	private String sobrenomeCliente;
	private String telefone1;
	private String telefone2;
	private String email;
	private ArrayList<Doenca> listaDeDoencas;
	private ArrayList<Remedio> listaDeRemedios;
	private ArrayList<Cirurgia> listaDeCirurgias;
	private ArrayList<Consulta> listaDeConsultas;
	
	
	
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getSobrenomeCliente() {
		return sobrenomeCliente;
	}
	public void setSobrenomeCliente(String sobrenomeCliente) {
		this.sobrenomeCliente = sobrenomeCliente;
	}
	public String getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	public String getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<Doenca> getListaDeDoencas() {
		return listaDeDoencas;
	}
	public void setListaDeDoencas(ArrayList<Doenca> listaDeDoencas) {
		this.listaDeDoencas = listaDeDoencas;
	}
	public ArrayList<Remedio> getListaDeRemedios() {
		return listaDeRemedios;
	}
	public void setListaDeRemedios(ArrayList<Remedio> listaDeRemedios) {
		this.listaDeRemedios = listaDeRemedios;
	}
	public ArrayList<Cirurgia> getListaDeCirurgias() {
		return listaDeCirurgias;
	}
	public void setListaDeCirurgias(ArrayList<Cirurgia> listaDeCirurgias) {
		this.listaDeCirurgias = listaDeCirurgias;
	}
	public ArrayList<Consulta> getListaDeConsultas() {
		return listaDeConsultas;
	}
	public void setListaDeConsultas(ArrayList<Consulta> listaDeConsultas) {
		this.listaDeConsultas = listaDeConsultas;
	}
	
	
}
