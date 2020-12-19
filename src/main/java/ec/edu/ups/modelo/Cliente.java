package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Cliente implements Serializable {

	@Id
	private String cedula;
	
	private String nombre;
	private String apellido;
	private String direccion;
	private String telefono;
	private String email;
	private String contrasenia;
	private Date fecha;
	
	public Cliente(String cedula, String nombre, String apellido, String direccion, String telefono, String email,
			String contrasenia, Date fecha) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.contrasenia = contrasenia;
		this.fecha = fecha;
	}



	public Cliente() {
		super();
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/*public List<Accesos> getListaAccesos() {
		return listaAccesos;
	}

	public void setListaAccesos(List<Accesos> listaAccesos) {
		this.listaAccesos = listaAccesos;
	}
	
	public void addAccesos(Accesos acces){
		if(listaAccesos==null)
			listaAccesos = new ArrayList<Accesos>();
		
		listaAccesos.add(acces);
	}*/
	
	
}
