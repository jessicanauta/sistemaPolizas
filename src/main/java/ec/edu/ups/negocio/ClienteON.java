package ec.edu.ups.negocio;

import java.util.Date;
import java.util.List;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import ec.edu.ups.controlador.ClienteDAO;
import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.negocio.ClienteON;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Stateless
public class ClienteON {

	@Inject
	private ClienteDAO clientedao;

	public ClienteON() throws Exception {
		super();
	}
	
	public void guardarContacto(Cliente cliente) throws Exception {

		if (validarCedula(cliente.getCedula())) {
			try {
				clientedao.insertar(cliente);
			} catch (Exception e) {
				throw new Exception("Error al ingresar Contacto");
			}
		} else {
			throw new Exception("Cedula Incorrecta");
		}
	}
	
	public Cliente buscarCuenta(int cuenta) {
		return clientedao.buscarCuenta(cuenta);
	}
	
	public Cliente obtenerCedula(String cedula) {
		try {
			return clientedao.findByCedula(cedula);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void actualizarCliente(Cliente cliente) throws Exception{
        try {
            clientedao.update(cliente);
        } catch (Exception e) {
        	throw new Exception("Error al actualizar Cliente");
        }
    }
	
	public List<Cliente> getCliente() {
		return clientedao.getClientes();
	}

	public boolean validarCedula(String ced) {
		boolean verdadera = false;
		int num = 0;
		int ope = 0;
		int suma = 0;
		for (int cont = 0; cont < ced.length(); cont++) {
			num = Integer.valueOf(ced.substring(cont, cont + 1));
			if (cont == ced.length() - 1) {
				break;
			}
			if (cont % 2 != 0 && cont > 0) {
				suma = suma + num;
			} else {
				ope = num * 2;
				if (ope > 9) {
					ope = ope - 9;
				}
				suma = suma + ope;
			}
		}
		if (suma != 0) {
			suma = suma % 10;
			if (suma == 0) {
				if (suma == num) {
					verdadera = true;
				}
			} else {
				ope = 10 - suma;
				if (ope == num) {
					verdadera = true;
				}
			}
		} else {
			verdadera = false;
		}
		return verdadera;
	}
}
