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

import ec.edu.ups.controlador.AdmistradorDAO;
import ec.edu.ups.controlador.ClienteDAO;
import ec.edu.ups.modelo.Administracion;
import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.negocio.ClienteON;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Stateless
public class ClienteON {

	//Atributos
	@Inject
	private ClienteDAO clientedao;
	
	@Inject
	private AdmistradorDAO admDAO;


	public ClienteON() throws Exception {
		super();
	}
	
	//Metodo para guardar el Cliente en la BD
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

	//Metodo para validar la cedula
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
	
	public void GuardarAdm(Administracion admin) throws Exception {
		if (validarCedula(admin.getCedula())) {
			try {
				admDAO.insertar(admin);
			} catch (Exception e) {
				throw new Exception("Error al ingresar Administrador");
			}

		} else {
			throw new Exception("CI:Incorrecta");
		}

	}
	
	public List<Administracion> getAdministraciones() {
		return admDAO.getAdministraciones();
	}

	public Administracion getAdministracione(String cedula) {
		return admDAO.read(cedula);
	}

	public Administracion iniciarSesionOn(Administracion admin) throws Exception {
		return admDAO.inicioSesion(admin);
	}
	
	public void enviarMail(Cliente t) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("jecbank@gmail.com", "Cuenca123"); // Correo y
																									// contraseña que se
																									// va a utilizar
																									// para enviar el
																									// correo.
			}
		});

		try {

			// Random n=new Random();
			// int r=n.nextInt(9999);
			// String cedula=t.getCedula();
			// String apellido=t.getApellido().substring(0, t.getApellido().length());
			// String user="";
			// user=cedula;

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("jecbank@gmail.com")); // Correo del que envia
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(t.getEmail())); // Correo al que se
																									// enviará el
																									// mensaje.
			message.setSubject("Confirmacion cuenta"); // Asunto
			message.setText("Usuario:" + t.getCedula() + "\n Clave: " + t.getContrasenia() + "\n"

					+ "saludos coordiales  \n\n"); // Mensaje

			Transport.send(message);

		} catch (MessagingException e) {

		}
	}

	/// Recuerda descargar tambien la libreria email.jar y

	public void mailAccesos(Cliente t, String mensaje) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("jecbank@gmail.com", "Cuenca123"); // Correo y
																									// contraseña que se
																									// va a utilizar
																									// para enviar el
																									// correo.
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("jecbank@gmail.com")); // Correo del que envia
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(t.getEmail())); // Correo al que se
																									// enviará el
																									// mensaje.
			message.setSubject("Estado del acceso del Login "); // Asunto
			message.setText(mensaje + "saludos coordiales  \n\n"); // Mensaje

			Transport.send(message);

		} catch (MessagingException e) {

		}
	}
}
