package ec.edu.ups.controlador;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import ec.edu.ups.modelo.Cliente;

@Stateless
public class ClienteDAO {

	 @PersistenceContext(name="sistemaPolizasPersistenceUnit")
		private EntityManager em;
	    
	    public void insertar(Cliente cliente) {
			em.persist(cliente);
		}

		public Cliente buscarCedula(String cedula) {
			return em.find(Cliente.class, cedula);
		}
		
		public Cliente findByCedula(String cedula) throws Exception {
	        try {
	        	String jpql = "SELECT c FROM Cliente c WHERE c.cedula = :cedula";
	            Query q = em.createQuery(jpql, Cliente.class);
	            q.setParameter("cedula", cedula);

	            System.out.println("cedula: " + jpql);
	            System.out.println("ced: " + q);
	            //return em.find(Cliente.class, q);
	            return (Cliente) q.getSingleResult();
	        } catch (Exception e) {
	            throw new Exception("Error al buscar por cedula");
	        }
	    }
	      
		public void update(Cliente cliente) throws Exception {
	        try {
	            em.merge(cliente);
	        } catch (Exception e) {
	            throw new Exception("Error al actualizar el cliente ");
	        }
	    }

		public Cliente buscarCuenta(int cuenta) {
			String jpql = "SELECT c FROM Cliente c Where c.cuenta=:cuenta ";
			Query q = em.createQuery(jpql, Cliente.class);	
			q.setParameter("cuenta", cuenta);
			return em.find(Cliente.class, q);
		}
		
		public List<Cliente> getClientes() {
			String jpql = "SELECT c FROM Cliente c ";
			Query q = em.createQuery(jpql, Cliente.class);		
			return q.getResultList();
		}

}
