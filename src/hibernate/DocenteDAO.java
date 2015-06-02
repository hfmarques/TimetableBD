package hibernate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import timetable.Curso;
import timetable.Disciplina;
import timetable.Docente;

public class DocenteDAO extends GenericoDAO{

	public DocenteDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public static Docente encontraDocentePorName(String nome) throws HibernateException, Exception {
		Docente docente = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Docente.class)
					.add(Restrictions.eq("nome", nome));	
			docente = (Docente) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return docente;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List<Docente> procuraTodos() throws Exception {
		List<Docente> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Docente.class);	
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}

}
