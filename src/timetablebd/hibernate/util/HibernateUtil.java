/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetablebd.hibernate.util;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


/**
 *
 * @author Héber
 */
public class HibernateUtil {

    //private static SessionFactory sessionFactory = buildSessionFactory();
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
	private static Transaction transaction;
	private static Session session;
    

    private static void buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                .applySetting("hibernate.connection.url", "jdbc:postgresql://localhost:5432/timetableBD")
                .applySetting("hibernate.connection.username", "postgres")
                .applySetting("hibernate.connection.password", "root")
                .applySetting("dialect", "org.hibernate.dialect.PostgreSQLDialect");
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getInstance() throws Exception {
		if (sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory.openSession();
	}
    
    /**
	 * Retorna todos os registros da tabela (classe) informada entre begin e
	 * end. Caso begin e end sejam negativos retorna todos os dados.
	 * 
	 * @param objClass
	 * @param begin
	 * @param end
	 * @return List<?>
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	public static List<?> findAll(Class objClass, int begin, int end) {
		List<?> lista = null;
		Query query = null;
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			query = session.createQuery("From " + objClass.getName());
			if (begin >= 0 && end >= 0) {
				query.setFirstResult(begin);
				query.setMaxResults(end - begin);
			}
			lista = (List<?>) query.list();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
		} finally {
			session.close();
			return lista;
		}
	}
        
        @SuppressWarnings({ "finally", "rawtypes" })
	public static List<?> findAll(Class objClass) {
		List<?> lista = null;
		Query query = null;
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			query = session.createQuery("From " + objClass.getName());
			lista = (List<?>) query.list();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
		} finally {
			session.close();
			return lista;
		}
	}

	/**
	 * Retorna todos os objetos da tabela
	 * 
	 * @param objClass
	 * @param id
	 * @return Object
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	public static Object find(Class objClass, int id) {
		Object objGet = null;
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			objGet = session.get(objClass, id);
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
		} finally {
			session.close();
			return objGet;
		}
	}

	/**
	 * Atualiza ou salva um objeto passado por parâmetro retornando {@code true}
	 * caso tenha sucesso e {@code false} caso não atualize.
	 * 
	 * @param obj
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transação, executando um
	 *                {@code rollback} nas modificações.
	 */
	@SuppressWarnings("finally")
	public static boolean saveOrUpdate(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.saveOrUpdate(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	/**
	 * Salva um objeto passado por parâmetro retornando {@code true} caso tenha
	 * sucesso e {@code false} caso não atualize.
	 * 
	 * @param obj
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transação, executando um
	 *                {@code rollback} nas modificações.
	 */
	@SuppressWarnings("finally")
	public static boolean save(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.save(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	/**
	 * Salva um objeto passado por parâmetro retornando {@code true} caso tenha
	 * sucesso e {@code false} caso não atualize.
	 * 
	 * @param obj
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transação, executando um
	 *                {@code rollback} nas modificações.
	 */
	@SuppressWarnings("finally")
	public static boolean saveList(List<?> objects) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			for (Object obj : objects) {
				session.save(obj);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	/**
	 * Atualiza o objeto passado por parâmetro retornando {@code true} caso
	 * tenha sucesso e {@code false} caso não atualize.
	 * 
	 * @param obj
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transação, executando um
	 *                {@code rollback} nas modificações.
	 */
	@SuppressWarnings("finally")
	public static boolean update(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.update(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	/**
	 * Exclui o objeto passado por parâmetro
	 * 
	 * @param obj
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transação, executando um
	 *                {@code rollback} nas modificações.
	 */
	@SuppressWarnings("finally")
	public static boolean delete(Object obj) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			session.delete(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}

	/**
	 * Exclui o vários objetos passados por parâmetro
	 * 
	 * @param objects
	 * @return boolean
	 * @exception HibernateException
	 *                em caso de erro de transação, executando um
	 *                {@code rollback} nas modificações.
	 */
	@SuppressWarnings("finally")
	public static boolean deleteList(List<?> objects) {
		try {
			session = getInstance();
			transaction = session.beginTransaction();
			for (Object obj : objects) {
				session.delete(obj);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			session.close();
			return true;
		}
	}
}