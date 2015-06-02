package hibernate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import timetable.Calouros;
import timetable.Disciplina;
import timetable.Sala;

public class DisciplinaDAO extends GenericoDAO{

	public DisciplinaDAO() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("finally")
	public static Disciplina encontraDisciplinaPorCodigo(String disciplinaCodigo) throws HibernateException, Exception {
		Disciplina disciplina = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Disciplina.class)
					.add(Restrictions.eq("codigo", disciplinaCodigo));	
			disciplina = (Disciplina) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return disciplina;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List<Disciplina> procuraTodos() throws Exception {
		List<Disciplina> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Disciplina.class);	
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
}
