package controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.CirurgiaDAO;
import dao.ClienteDAO;
import dao.ConsultaDAO;
import dao.DoencaDAO;
import dao.RemedioDAO;
import model.Cirurgia;
import model.Cliente;
import model.Consulta;
import model.Doenca;
import model.Remedio;


@ManagedBean
@SessionScoped
public class ClienteController implements Serializable {

	private static final long serialVersionUID = 3788235120261465931L;
	
	private ArrayList<Cliente> listaDeClientes;
	private ClienteDAO clienteDAO;
	private DoencaDAO doencaDAO;
	private RemedioDAO remedioDAO;
	private CirurgiaDAO cirurgiaDAO;
	private ConsultaDAO consultaDAO;
	private Cliente cliente = new Cliente();
	private Doenca doenca = new Doenca();
	private Remedio remedio = new Remedio();
	private Cirurgia cirurgia = new Cirurgia();
	private Consulta consulta = new Consulta();
	private Consulta novaConsulta = new Consulta();
	private String IetmDeBusca="";
	//server apenas para limpar o cliente da memoria quando a pagina listaClientes.xhtml é inicializada
	private String limparCliente;
	
	
	@PostConstruct
	public void listarClientes(){
		clienteDAO = new ClienteDAO();
		this.setListaDeClientes(clienteDAO.listarClientes(IetmDeBusca));
		clienteDAO = null;
		cliente = null;
	}

	
	public String cadastrarOuAlterarCliente(){
		clienteDAO = new ClienteDAO();
		int idRetorno = this.clienteDAO.incluirOuAlterarCliente(getCliente());
		if( idRetorno > 0 && idRetorno == this.getCliente().getIdCliente()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente atualizado com sucesso.",""));
			clienteDAO = null;
			return"verCliente.xhtml";
		}else if(idRetorno > 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente cadastrado com sucesso.",""));
			listarClientes();
			return"listaClientes.xhtml";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar Cliente.",""));
		}
		listarClientes();
		return"listaClientes.xhtml";
	}
	
	
	public String verCliente(){
		clienteDAO = new ClienteDAO();
		doencaDAO = new DoencaDAO();
		remedioDAO = new RemedioDAO();
		cirurgiaDAO = new CirurgiaDAO();
		consultaDAO = new ConsultaDAO();
		this.cliente.setListaDeCirurgias(cirurgiaDAO.obterCirurgias(getCliente().getIdCliente()));
		this.cliente.setListaDeDoencas(doencaDAO.obterDoenca(getCliente().getIdCliente()));
		this.cliente.setListaDeRemedios(remedioDAO.obterRemedios(getCliente().getIdCliente()));
		this.cliente.setListaDeConsultas(consultaDAO.obterConsultas(getCliente().getIdCliente()));
		
		if(this.cliente.getListaDeConsultas().size() > 0){
			this.consulta = this.cliente.getListaDeConsultas().get(0);
		}
		
		
		doencaDAO = null;
		clienteDAO = null;
		remedioDAO = null;
		cirurgiaDAO = null;
		consultaDAO = null;
		novaConsulta = null;
		
		return "verCliente.xhtml";
	}
	
	public void deletarCliente(){
		clienteDAO = new ClienteDAO();
		if(clienteDAO.deletaCliente(getCliente().getIdCliente())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente deletado com sucesso.",""));
			listarClientes();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao deletar Cliente.",""));
		}
		clienteDAO = null;
		cliente = null;
	}
	
	public void deletarDoenca(){
		doencaDAO = new DoencaDAO();
		if(doencaDAO.deletaDoenca(getDoenca().getIdDoenca())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Doença deletada com sucesso.",""));
			verCliente();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao deletar Doença.",""));
		}
		doencaDAO = null;
		doenca = null;
	}
	
	public void cadastrarDoenca(){
		doencaDAO = new DoencaDAO();
		this.getDoenca().setIdCliente(this.getCliente().getIdCliente());
		if(doencaDAO.incluirDoenca(doenca) >0 ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Doença cadastrada com sucesso.",""));
			verCliente();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao deletar Doença.",""));
		}
		doencaDAO = null;
		doenca = null;
	}
	
	
	public void deletarRemedio(){
		remedioDAO = new RemedioDAO();
		if(remedioDAO.deletaRemedios(getRemedio().getIdRemedio())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Remédio deletada com sucesso.",""));
			verCliente();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao deletar Remédio.",""));
		}
		remedioDAO = null;
		remedio = null;
	}
	
	
	public void cadastrarRemedio(){
		remedioDAO = new RemedioDAO();
		this.getRemedio().setIdCliente(this.getCliente().getIdCliente());
		if(remedioDAO.incluirRemedio(remedio) >0 ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Remédio cadastrada com sucesso.",""));
			verCliente();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao deletar Remédio.",""));
		}
		remedioDAO = null;
		remedio = null;
	}
	
	public void deletarCirurgia(){
		cirurgiaDAO = new CirurgiaDAO();
		if(cirurgiaDAO.deletaCirurgia(getCirurgia().getIdCirurgia())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cirurgia deletada com sucesso.",""));
			verCliente();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao deletar Cirurgia.",""));
		}
		cirurgiaDAO = null;
		cirurgia = null;
	}
	
	
	public void cadastrarCirurgia(){
		cirurgiaDAO = new CirurgiaDAO();
		this.getCirurgia().setIdCliente(this.getCliente().getIdCliente());
		if(cirurgiaDAO.incluirCirurgia(cirurgia) >0 ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cirurgia cadastrada com sucesso.",""));
			verCliente();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar Cirurgia.",""));
		}
		cirurgiaDAO = null;
		cirurgia = null;
	}
	
	public void verConsulta(){
		//metodo chamado pelo botao ver consulta, atualiza a consulta mostrada na tela
	}
	
	
	public void cadastrarConsulta(){
		consultaDAO = new ConsultaDAO();
		this.getNovaConsulta().setIdCliente(this.getCliente().getIdCliente());
		if(consultaDAO.incluirConsulta(this.getNovaConsulta()) > 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta cadastrada com sucesso.",""));
			verCliente();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar Consulta.",""));
		}
		novaConsulta = null;
		consultaDAO = null;
	}
	
	public void deletarConsulta(){
		consultaDAO = new ConsultaDAO();
		if(consultaDAO.deletaConsulta(this.getConsulta().getIdConsulta())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta deletada com sucesso.",""));
			verCliente();
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao deletar Consulta.",""));
		}
		cirurgiaDAO = null;
	}
	
	
	public void atualizarConsulta(){
		consultaDAO = new ConsultaDAO();
		if(consultaDAO.atualizarConsulta(consulta)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta atualizada com sucesso.",""));
		
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar Consulta.",""));
		}
		consultaDAO =  null;
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
		if(cliente == null){
			cliente = new Cliente();
		}
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


	public Doenca getDoenca() {
		if(doenca == null){
			doenca = new Doenca();
		}
		return doenca;
	}


	public void setDoenca(Doenca doenca) {
		this.doenca = doenca;
	}


	public Remedio getRemedio() {
		if(remedio == null){
			remedio = new Remedio();
		}
		return remedio;
	}


	public void setRemedio(Remedio remedio) {
		this.remedio = remedio;
	}


	public Cirurgia getCirurgia() {
		if(cirurgia == null){
			cirurgia = new Cirurgia();
		}
		return cirurgia;
	}


	public void setCirurgia(Cirurgia cirurgia) {
		this.cirurgia = cirurgia;
	}


	public String getLimparCliente() {
		this.cliente = null;
		this.consulta = null;
		return"";
	}


	public void setLimparCliente(String limparCliente) {
		this.limparCliente = limparCliente;
	}


	public Consulta getConsulta() {
		if(consulta == null){
			consulta = new Consulta();
		}
		return consulta;
	}


	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}


	public Consulta getNovaConsulta() {
		if(novaConsulta == null){
			novaConsulta = new Consulta();
		}
		return novaConsulta;
	}


	public void setNovaConsulta(Consulta novaConsulta) {
		this.novaConsulta = novaConsulta;
	}

	
	
	
	
}
