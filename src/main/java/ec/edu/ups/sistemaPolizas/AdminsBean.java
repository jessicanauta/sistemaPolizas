package ec.edu.ups.sistemaPolizas;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import ec.edu.ups.modelo.Administracion;
import ec.edu.ups.modelo.Cliente;
import ec.edu.ups.negocio.ClienteON;

@ManagedBean
@ViewScoped
public class AdminsBean {

	@Inject
	private ClienteON on;

	private Administracion newAdm;

	private List<Administracion> listarAdmistracion;

	@PostConstruct
	public void init() {

		newAdm = new Administracion();
		loadDataAd();

	}

	public Administracion getNewAdm() {
		return newAdm;
	}

	public void setNewAdm(Administracion newAdm) {
		this.newAdm = newAdm;
	}

	public List<Administracion> getListarAdm() {
		return listarAdmistracion;
	}

	public void setListarAdm(List<Administracion> listarAdm) {
		this.listarAdmistracion = listarAdm;
	}

	@Override
	public String toString() {
		return "AdminsBean [newAdm=" + newAdm + ", listarAdmistracion=" + listarAdmistracion + "]";
	}

	public String GuarAdm() {
		try {
			on.GuardarAdm(newAdm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listarAdm";
	}

	public String editar(String cedula) {
		System.out.println(cedula);
		return "CrearAdm?faces-redirect=true&cedula=" + cedula;
	}
	private String cedula;

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		System.out.println("cedula parametro " + cedula);
		this.cedula = cedula;
		if (cedula != null) {
			newAdm = on.getAdministracione(cedula);
		}
	}

	private void loadDataAd() {
		listarAdmistracion = on.getAdministraciones();

	}

	public String iniciarSesion() {
		String redireccion = null;
		Administracion admin;
		try {
		admin=on.iniciarSesionOn(newAdm);
		if (admin!=null) {
			redireccion = "CrearAdm?faces-redirect=true";
			
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso","Credenciales Incorrectas"));
		}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error"));
		}

		return redireccion;

	}

}