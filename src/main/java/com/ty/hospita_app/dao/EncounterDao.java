package com.ty.hospita_app.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.ty.hospita_app.entity.Branch;
import com.ty.hospita_app.entity.Encounter;
import com.ty.hospita_app.entity.Item;
import com.ty.hospita_app.entity.MedOrders;
import com.ty.hospita_app.entity.Person;
import com.ty.hospita_app.exceptions.BranchNotFoundException;
import com.ty.hospita_app.exceptions.EncounterDetailsNotFound;

public class EncounterDao {
	
	private static EntityManager getEntityManager() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("seema");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	
	public void encounterPerson(Encounter encounter, int branch_id) {
		
		EntityManager entityManager = EncounterDao.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		Branch branch= entityManager.find(Branch.class, branch_id);
		
		if (branch != null) {
			List<Encounter> encounters= branch.getEncounter();
			encounters.add(encounter);
			
			branch.setEncounter(encounters);
			
			entityTransaction.begin();
			entityManager.merge(branch);
			entityManager.persist(encounter);
			entityTransaction.commit();
			
		} else {
			throw new BranchNotFoundException();
		}
	}
	
	public void deleteEncounter(int encount_id, int branch_id) {
		EntityManager entityManager = EncounterDao.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		Branch branch= entityManager.find(Branch.class, branch_id);
		Encounter encount= null;
		if (branch != null) {
			List<Encounter> encounts= branch.getEncounter();
			
			for (Encounter encounter : encounts) {
				if (encounter.getId()==encount_id) {
					encount=encounter;
					encounts.remove(encounter);
					break;
				}
			}
			if (encount !=null) {
				encount.setPerson(null);
				List<MedOrders> medorder= encount.getMedorders();
				//List<Item> items=new ArrayList<Item>();
				
				for (MedOrders order : medorder) {
					//List<Item> i= order.getItems();
					order.setItems(null);
				}
				entityTransaction.begin();
				entityManager.merge(branch);
//				for (Item item : items) {
//					entityManager.remove(item);
//				}
				for (MedOrders mo : medorder) {
					entityManager.remove(mo);
				}
				//entityManager.remove(person);
				entityManager.remove(encount);
				entityTransaction.commit();
				
			} else {
				throw new EncounterDetailsNotFound();
			}
			
			
		} else {
			throw new BranchNotFoundException();
		}
		
	}
}
