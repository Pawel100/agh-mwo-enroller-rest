package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

@Component("participantService")
public class ParticipantService {

	DatabaseConnector connector;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Participant> getAll() {
		return connector.getSession().createCriteria(Participant.class).list();
	}

	public Participant findByLogin(String login) {
		// TODO Auto-generated method stub
		return (Participant) connector.getSession().get(Participant.class, login);
	}

	public void create(Participant participant) {
		// TODO Auto-generated method stub
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().save(participant);
		transaction.commit();
	}
	
	public void delete(Participant participant) {
		// TODO Auto-generated method stub
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().delete(participant);
		transaction.commit();
	}
	
	public void update(Participant participant) {
		// TODO Auto-generated method stub
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().update(participant);
		transaction.commit();
	}

	
}
