package com.ty.hospita_app.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.ty.hospita_app.dao.AddressDao;
import com.ty.hospita_app.entity.Address;
import com.ty.hospita_app.exceptions.AddressNotFoundException;

public class AddressDao {

	
	private static EntityManager getEntityManager() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("seema");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}
	
	public void updateAddress(Address address) {
		EntityManager entityManager = AddressDao.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		int add_id= address.getId();
		Address address1= entityManager.find(Address.class, add_id);
		
		if (address1 != null) {
			
			if (address.getPlace() !=null && address.getState() != null &&address.getPincode() !=0) {
				address1.setPlace(address.getPlace());
				address1.setState(address.getState());
				address1.setPincode(address.getPincode());
			} else {
				System.out.println("Invalid credentials");
			}
			
			entityTransaction.begin();
			entityManager.merge(address1);
			entityTransaction.commit();
			
		} else {
			throw new AddressNotFoundException();
		}
		
	}
}
