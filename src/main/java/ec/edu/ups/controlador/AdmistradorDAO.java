package ec.edu.ups.controlador;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.edu.ups.modelo.Administracion;

@Stateless
public class AdmistradorDAO {
	
	 @PersistenceContext(name="SistemaTransaccionalPersistenceUnit")
		private EntityManager em;
	    
	    public void insertar(Administracion admin) {
			em.persist(admin);
		}
	    
	    public void delete(String cedula) {
			Administracion a= read(cedula);
			em.remove(a);
		}

		public Administracion read(String cedula) {
			return em.find(Administracion.class, cedula);
		}

		public List<Administracion> getAdministraciones() {
			String jpql = "SELECT c FROM Administracion c ";
			Query q = em.createQuery(jpql, Administracion.class);		
			return q.getResultList();
		}
		
		public Administracion inicioSesion(Administracion admin) {
			Administracion administracion = null;
			String jpql;
			try {
				jpql="SELECT c FROM Administracion c WHERE c.cedula=?1 and c.contra=?2";
				Query query= em.createQuery(jpql);
				query.setParameter(1, admin.getCedula());
				query.setParameter(2, admin.getContra());
				List<Administracion>lista=query.getResultList();
				if (!lista.isEmpty()) {
					administracion=lista.get(0);
				}
			} catch (Exception e) {
			throw e;
			}
			return administracion;
			
		}
		
}