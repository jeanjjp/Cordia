package util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Adm;

public class FiltroLogin implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		Adm adm = null;
		
		HttpSession sessao = ((HttpServletRequest) request).getSession();

		if (sessao != null) {
			adm = (Adm) sessao.getAttribute("admLogado");
		}

		if (adm == null || sessao ==null) {
			String contextPath = ((HttpServletRequest) request).getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath);
		} else {

			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}
}