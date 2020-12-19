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
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ec.edu.ups.controlador.AdmistradorDAO;
import ec.edu.ups.controlador.ClienteDAO;
import ec.edu.ups.controlador.CuentaDeAhorroDAO;
import ec.edu.ups.modelo.Administracion;
import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.modelo.CuentaDeAhorro;
import ec.edu.ups.negocio.ClienteON;

import java.net.PasswordAuthentication;
import java.text.DateFormat;
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


	@Inject
	private CuentaDeAhorroDAO cuentaDAO;
	
	
	

	public ClienteON() throws Exception {
		super();
	}
	
	
		
	public String getUsuario(String cedula, String nombre, String apellido) {
		System.out.println(cedula);
		System.out.println(nombre);
		System.out.println(apellido);
		String ud = cedula.substring(cedula.length() - 1);
		String pln = nombre.substring(0, 1);
		int it = 0;
		for (int i = 0; i < apellido.length(); i++) {
			if (apellido.charAt(i) == 32) {
				it = i;
			}
		}
		String a = "";
		if (it == 0) {
			a = apellido.substring(0, apellido.length());
		} else {
			a = apellido.substring(0, it);
		}
		return pln.toLowerCase() + a.toLowerCase() + ud;
	}

	public String getContraseña() {
		String simbolos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz0123456789!#$%&()*+,-./:;<=>?@_";

		int tam = simbolos.length() - 1;
		System.out.println(tam);
		String clave = "";
		for (int i = 0; i < 10; i++) {
			int v = (int) Math.floor(Math.random() * tam + 1);
			clave += simbolos.charAt(v);
		}

		return clave;
	}
	public void enviarMail1(String destinario, String asunto, String mensaje) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session sesion = Session.getDefaultInstance(props);
		String correoEnvia = "jecbank@gmail.com";
		String contrasena = "Cuenca123";
		
		try {
			Message message = new MimeMessage(sesion);
			message.setFrom(new InternetAddress("jecbank@gmail.com")); // Correo del que envia
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinario));		
			message.setSubject(asunto); // Asunto
			message.setText(mensaje); // Mensaje

			Transport transportar = sesion.getTransport("smtp");
			transportar.connect(correoEnvia, contrasena);
			transportar.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		} catch (AddressException ex) {
			System.out.println(ex.getMessage());
		} catch (MessagingException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public String fecha() {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return hourdateFormat.format(date);
	}

	
	public String obtenerFecha(Date fecha) {
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return hourdateFormat.format(fecha);
	}
	/*-------------- Cuenta De Ahorros----------------------*/
	
		public void guardarCuentaDeAhorros(CuentaDeAhorro c) {
		Cliente cliente = clientedao.buscarCedula(c.getCliente().getCedula());
		if (cliente == null) {
			Cliente cli = c.getCliente();
			String usuario = getUsuario(cli.getCedula(), cli.getNombre(), cli.getApellido());
			String contraseña = getContraseña();
			cli.setContrasenia(contraseña);
			c.setCliente(cli);
			String destinatario = cli.getEmail(); // A quien le quieres escribir.

			String asunto = "CREACION DE USUARIO";
			String cuerpo = "JAMVirtual                                               SISTEMA TRANSACCIONAL\n"
					+ "------------------------------------------------------------------------------\n"
					+ "              Estimado(a): " + cli.getNombre().toUpperCase() + " "
					+ cli.getApellido().toUpperCase() + "\n"
					+ "------------------------------------------------------------------------------\n"
					+ "COOPERATIVA JE le informa que el usuario ha sido habilitado exitosamente.    \n"
					+ "                                                                              \n"
					+ "                       Su usuario es : " + usuario + "                          \n"
					+ "                   	Su clave de acceso es:   " + contraseña + "               \n"
					+ "                       Fecha: " + fecha() + "                                     \n"
					+ "                                                                              \n"
					+ "------------------------------------------------------------------------------\n";
			enviarMail1(destinatario, asunto, cuerpo);
			cuentaDAO.insert(c);
		}
		
	}
	
		public CuentaDeAhorro buscarCuentaDeAhorroCliente(String cedulaCliente) {
			CuentaDeAhorro cuentaDeAhorro = cuentaDAO.getCuentaCedulaCliente(cedulaCliente);
			
			return cuentaDeAhorro;

		}

		public void eliminarCuentaDeAhorro(String numeroCuentaDeAhorro) {
			cuentaDAO.delete(numeroCuentaDeAhorro);
		}

		public void actualizarCuentaDeAhorro(CuentaDeAhorro cuentaDeAhorro) {
			cuentaDAO.update(cuentaDeAhorro);
		}
		public List<CuentaDeAhorro> listaCuentaDeAhorros(){
			List<CuentaDeAhorro> clientes = cuentaDAO.getCuentaDeAhorros();
			return clientes;
		}
	
		public String generarNumeroDeCuenta() {
			int numeroInicio = 4040;
			List<CuentaDeAhorro> listaCuentas = listaCuentaDeAhorros();
			int numero = listaCuentas.size() + 1;
			String resultado = String.format("%08d", numero);
			String resultadoFinal = String.valueOf(numeroInicio) + resultado;
			return resultadoFinal;
		}

	/*----------------- Cliente -------------------------------------*/
	
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
	
	
	/*------------------ Administracion ------------------------------------*/
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
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication("jecbank@gmail.com", "Cuenca123"); // Correo y
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
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication("jecbank@gmail.com", "Cuenca123"); // Correo y
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
