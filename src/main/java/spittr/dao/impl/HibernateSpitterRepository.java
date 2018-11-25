package spittr.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spittr.dao.SpitterRepository;
import spittr.dto.Spitter;

@Repository
@Transactional
public class HibernateSpitterRepository implements SpitterRepository {
	@Autowired
	private SessionFactory sessionFactory;

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	public long count() {
		return findAll().size();
	}

	public Spitter save(Spitter spitter) {
		Serializable id = currentSession().save(spitter); // <co id="co_UseCurrentSession"/>
		return new Spitter((Long) id, spitter.getUsername(), spitter.getPassword(), spitter.getFirstName(),
				spitter.getLastName(), spitter.getEmail());
	}

	@Override
	public Spitter findOne(long id) {
		return (Spitter) currentSession().get(Spitter.class, id);
	}

	@Override
	public Spitter findByUsername(String username) {
		return (Spitter) currentSession().createCriteria(Spitter.class).add(Restrictions.eq("username", username))
				.list().get(0);
	}

	@Override
	public List<Spitter> findAll() {
		return currentSession().createCriteria(Spitter.class).list();
	}
}
