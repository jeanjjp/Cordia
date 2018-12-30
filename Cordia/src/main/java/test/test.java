package test;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import model.Cliente;
import util.Criptografia;



public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//teste de email
//		EnviaEmail enviaEmail = new EnviaEmail();
//		
//		try {
//			Cliente c =  new Cliente();
//			c.setNomeCliente("");
//			c.setEmailCliente("");
//			enviaEmail.enviaEmailBemvindo(c);
//		} catch (EmailException | MalformedURLException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e);
//		}
		
		//teste de codigo
		//Codigo c = new Codigo();
		//System.out.println(c.gerarCodigo());
		
//		Date dataEnvio = new java.sql.Date(System.currentTimeMillis());
//		System.out.println(dataEnvio);
		
	
		
	Criptografia.criptografar("123");
		
	}

}
