package hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import timetable.Calouros;
import timetable.Disciplina;
import timetable.Turma;

public class CalourosDAO extends GenericoDAO {

	public CalourosDAO(){
		// TODO Auto-generated constructor stub
	}

	public static Calouros encontraCalouroPorVagas(int numVagas) throws HibernateException, Exception {
		Calouros calouros = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Calouros.class)
					.add(Restrictions.eq("numVagas", numVagas));	
			calouros = (Calouros) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return calouros;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List<Calouros> procuraTodos() throws Exception {
		List<Calouros> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Calouros.class);	
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
}
