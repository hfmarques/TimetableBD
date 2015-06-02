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

public class CursoDAO extends GenericoDAO{

	public CursoDAO() {
		// TODO Auto-generated constructor stub
	}

	public static Curso encontraCursoPorCodigo(String cursoCodigo) throws HibernateException, Exception {
		Curso curso = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Curso.class)
					.add(Restrictions.eq("codigo", cursoCodigo));	
			curso = (Curso) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return curso;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Curso> procuraTodos() throws Exception {
		List<Curso> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Curso.class);
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
}
