package com.ty.hospita_app.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ty.hospita_app.entity.Branch;
import com.ty.hospita_app.entity.Encounter;
import com.ty.hospita_app.entity.MedOrders;
import com.ty.hospita_app.entity.Person;

public class PersonDao {
	
	private static EntityManager getEntityManager() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("seema");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}
	
	public void savePerson(Person person) {
		
		
		
	}
	
	public void deletePerson(int person_id) {
		EntityManager entityManager = PersonDao.getEntityManager();
		EntityTransaction entityTransaction= entityManager.getTransaction();
		
		Query query = entityManager.createQuery("Select e from Encounter e where Encounter.person_id=?1");
		query.setParameter(1, person_id);
		
		List<Encounter> encList = query.getResultList();
		
		Query query2= entityManager.createQuery("Select b from Branch b");
		List<Branch> branches= query2.getResultList();
		
		Person person= entityManager.find(Person.class, person_id);
		
		for (Branch branch : branches) {
			branch.getEncounter();
			
		}
		
		
		
		
		
		
		
		
		
		
		
		List<MedOrders> medList=new ArrayList<MedOrders>();
		
		for (Encounter encounter : encList) {
			
			List<MedOrders> med= encounter.getMedorders();
			for (MedOrders medOrders : med) {
				medOrders.setItems(null);
				medList.add(medOrders);
			}
		}
		
		entityTransaction.begin();
		for (Encounter encount : encList) {
			entityManager.remove(encount);
		}
		for (MedOrders medOrders : medList) {
			entityManager.remove(medOrders);
		}
		entityManager.refresh(person);
		entityTransaction.commit();
		
	}

}
