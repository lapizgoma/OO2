package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.ParameterizedType;

public abstract class Dao<T> {
	
	private Class<T> clasePersistente;
	protected static Session session;
    protected Transaction tx;
    
    @SuppressWarnings("unchecked")
	protected Dao(){  
        this.clasePersistente = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass())
        									.getActualTypeArguments()[0];
    } 
    
    public Class<T> getClasePersistente() {  
        return clasePersistente;  
    } 
    
    protected void iniciaOperacion() throws HibernateException {
    	
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    protected void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("ERROR en la capa de acceso a datos", he);
    }

 	@SuppressWarnings("unchecked")
	public List<T> traer() throws HibernateException {
    	List<T> lista=null;
        try {
            iniciaOperacion();                    
            lista=session.createQuery("from "+clasePersistente.getName()).list();
            
        } finally {
            session.close();
        }

        return lista;
    }
 	
 	@SuppressWarnings("unchecked")
	protected List<T> traerPorHQL(String hql) throws HibernateException  {
    	List<T> lista=null;
        //iniciaOperacion(); 
    	// iniciaOperacion se invoca en la subclase
    	lista=session.createQuery("from "+clasePersistente.getName()+" o "+hql).list();

        return lista;
    }
    
 	@SuppressWarnings("unchecked")
	public T traer(Long idObjeto) throws HibernateException {
		
		T objeto = null;
	
		try {
			iniciaOperacion();
			objeto = (T) session.get(clasePersistente, idObjeto);
		} finally {
			session.close();
		}
		return objeto;
		
	}
 	
 	public void eliminar(T objeto) throws HibernateException {
 		try {
 			iniciaOperacion();
 			session.delete(objeto);
 			tx.commit();
 		} 
 		catch (HibernateException he) {
 			manejaExcepcion(he);
 			throw he;
 		} 
 		finally {
 			session.close();
 		}
 	}
 	
 	public Long agregar(T objeto) {
 		Long id = 0L;
 		//System.out.println(objeto);
 		try {
 			iniciaOperacion();
 			session.save(objeto);
 			tx.commit();
 		} 
 		catch (HibernateException he) {
 			manejaExcepcion(he);
 			id = -1L;
 			throw he;
 		} 
 		finally {
 			session.close();
 		}
 		return id;
 	}
 	
 	public void actualizar(T objeto) throws HibernateException {
 	try {
 			iniciaOperacion();
 			session.update(objeto);
 			tx.commit();
 		} catch (HibernateException he) {
 			manejaExcepcion(he);
 			throw he;
 		} 
 		finally {
 			session.close();
 		}
 	}
}