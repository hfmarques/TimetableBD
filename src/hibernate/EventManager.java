package hibernate;

import org.hibernate.Session;

/**
 *
 * @author Héber
 */

public class EventManager {

	public static void insert(Object obj) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.save(obj);

		session.getTransaction().commit();

		session.close();

		HibernateUtil.getSessionFactory().close();
	}
}