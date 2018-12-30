package controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.AdmDAO;
import model.Adm;


@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1652049856110915048L;
	private Adm adm;
	private String loginInformado;
	private String senhaInformada;

	public LoginController() {
		super();
	}

	public String voltar() {
		return "/index.xhtml";
	}

	public String autenticar() {

		String paginaDestino;

		// Chamar o DAO para verificar se o login e a senha est�o corretos
		AdmDAO dao = new AdmDAO();
		
		Adm admFazendoLogin = dao.verificaLogin(loginInformado, senhaInformada);
		
		// Obtem a Sessao
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

		HttpSession sessao = request.getSession();
		// Caso sim:
		if (admFazendoLogin != null) {
			// Adicionar a pessoa na Session
			sessao.setAttribute("admLogado", admFazendoLogin);

			// encaminhar para a p�gina de sucessos
			paginaDestino = "security/listaClientes.xhtml";
		} else {
			sessao.invalidate();

			// Caso contr�rio: encaminhar para a p�gina de erro
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome de usuário ou senha inválidos.", ""));
			paginaDestino = "inicio.xhtml";
		}

		return paginaDestino;
	}


	public String sair() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

		HttpSession sessao = request.getSession();

		sessao.invalidate();

		return "/inicio.xhtml";
	}

	public String getLoginInformado() {
		return loginInformado;
	}

	public void setLoginInformado(String loginInformado) {
		this.loginInformado = loginInformado;
	}

	public String getSenhaInformada() {
		return senhaInformada;
	}

	public void setSenhaInformada(String senhaInformada) {
		this.senhaInformada = senhaInformada;
	}

	public Adm getAdm() {
		if (this.adm == null) {
			adm = new Adm();
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			HttpSession sessao = request.getSession();
			adm = (Adm) sessao.getAttribute("admLogado");
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			HttpSession sessao = request.getSession();
			adm = (Adm) sessao.getAttribute("admLogado");
		}
		return adm;
	}

	public void setAdm(Adm adm) {
		this.adm = adm;
	}


	

}
