package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting findById(int id) {
		// TODO Auto-generated method stub
		return (Meeting) connector.getSession().get(Meeting.class, id);
	}
	
	public Meeting findByTitle(String title) {
		// TODO Auto-generated method stub
		return (Meeting) connector.getSession().get(Meeting.class, title);
	}

	public void create(Meeting meeting) {
		// TODO Auto-generated method stub
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
	}

	public void delete(Meeting meeting) {
		// TODO Auto-generated method stub
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().delete(meeting);;
		transaction.commit();
		
	}

}
