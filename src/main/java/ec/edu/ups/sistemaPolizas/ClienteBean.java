package ec.edu.ups.sistemaPolizas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.sound.midi.Soundbank;

import org.hibernate.validator.internal.util.privilegedactions.NewProxyInstance;

import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.negocio.ClienteON;

@ManagedBean
@SessionScoped
public class ClienteBean {

	@Inject
	private ClienteON on;

	private Cliente newCliente;

	private List<Cliente> listarClientes;

	public ClienteON getOn() {
		return on;
	}

	public void setOn(ClienteON on) {
		this.on = on;
	}

	private int cuenta;

	private String cedula;
	private String clave;

	@PostConstruct
	public void init() {
		newCliente = new Cliente();
		//acceso = new Accesos();
		// access = new ArrayList<Accesos>();
		cuenta = cuenta();
		this.cuenta = cuenta;
		loadDataPersonas();
	}

	public Cliente getNewCliente() {
		return newCliente;
	}

	public void setNewCliente(Cliente newCliente) {

		this.newCliente = newCliente;
	}

	public List<Cliente> getListarClientes() {
		return listarClientes;
	}

	public void setListarClientes(List<Cliente> listarClientes) {
		this.listarClientes = listarClientes;
	}

	@Override
	public String toString() {
		return "ClientesBeans [on=" + on + ", newCliente=" + newCliente + ", listarClientes=" + listarClientes + "]";
	}

	public String guardarDatos() {

		System.out.println(this.toString());

		try {
			on.guardarContacto(newCliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void onClick(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		System.out.println(dateFormat.format(date));

	}

	private void loadDataPersonas() {
		listarClientes = on.getCliente();

	}

	public int cuenta() {
		int numero = (int) (Math.random() * 1000 + 1);
		System.out.println("el resultado de la rmando es " + numero);
		return numero;
	}

	public int getCuenta() {
		return cuenta;
	}

	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
