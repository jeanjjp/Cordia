package model;

import java.util.Date;

public class Consulta {

	
	private int idConsulta;
	private int idCliente;
	private String queixaPrimaria;
	private String queixaSecundaria;
	private String resultado;
	private Date dataConsulta;
	private String avaliacao;
	
	
	
	public int getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(int idConsulta) {
		this.idConsulta = idConsulta;
	}
	public String getQueixaPrimaria() {
		return queixaPrimaria;
	}
	public void setQueixaPrimaria(String queixaPrimaria) {
		this.queixaPrimaria = queixaPrimaria;
	}
	public String getQueixaSecundaria() {
		return queixaSecundaria;
	}
	public void setQueixaSecundaria(String queixaSecundaria) {
		this.queixaSecundaria = queixaSecundaria;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Date getDataConsulta() {
		return dataConsulta;
	}
	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getAvaliacao() {
		return avaliacao;
	}
	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	
	
	
}
