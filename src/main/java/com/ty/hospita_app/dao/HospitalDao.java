package com.ty.hospita_app.dao;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.ty.hospita_app.entity.Hospital;
import com.ty.hospita_app.entity.Item;
import com.ty.hospita_app.entity.MedOrders;
import com.ty.hospita_app.entity.Person;
import com.ty.hospita_app.dao.HospitalDao;
import com.ty.hospita_app.exceptions.HospitalNotFoundException;
import com.ty.hospita_app.entity.Address;
import com.ty.hospita_app.entity.Branch;
import com.ty.hospita_app.entity.Encounter;

public class HospitalDao {

	private static EntityManager getEntityManager() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("seema");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	public void saveHospital(Hospital hospital) {

		EntityManager entityManager = HospitalDao.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		List<Branch> branches = hospital.getBranches();
		List<Encounter> encounters = new ArrayList<Encounter>();
		List<Address> address = new ArrayList<Address>();
		List<MedOrders> medorders = new ArrayList<MedOrders>();
		List<Item> items = new ArrayList<Item>();
		List<Person> person = new ArrayList<Person>();
		
		for (Branch branch : branches) {
			
			Address add1 = branch.getAddress();
			address.add(add1);

			List<Encounter> enList = branch.getEncounter();
			for (Encounter encounter1 : enList) {
				encounters.add(encounter1);
				List<MedOrders> medList = encounter1.getMedorders();
				for (MedOrders medOrders1 : medList) {
					medorders.add(medOrders1);

					List<Item> itemList = medOrders1.getItems();
					for (Item item1 : itemList) {
						items.add(item1);
					}
				}
				Person person1 = encounter1.getPerson();
				person.add(person1);
			}

		}

		entityTransaction.begin();

		entityManager.persist(hospital);

		for (Branch b1 : branches) {
			entityManager.persist(b1);
		}
		for (Encounter e1 : encounters) {
			entityManager.persist(e1);
		}
		for (Address a1 : address) {
			entityManager.persist(a1);
		}
		for (MedOrders m1 : medorders) {
			entityManager.persist(m1);
		}
		for (Item i1 : items) {
			entityManager.persist(i1);
		}
		for (Person p1 : person) {
			entityManager.persist(p1);
		}

		entityTransaction.commit();

	}

	public Hospital viewHospitalById(int hospital_id) {
		EntityManager entityManager = HospitalDao.getEntityManager();
		Hospital hospital = entityManager.find(Hospital.class, hospital_id);

		if (hospital != null) {
			return hospital;
		} else {

			throw new HospitalNotFoundException();
		}
	}
	
	public void deleteHospital(int id) {
		EntityManager entityManager = HospitalDao.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Hospital hospital = entityManager.find(Hospital.class, id);

		if (hospital != null) {
			List<Branch> branches = hospital.getBranches();
			List<Encounter> encounters = new ArrayList<Encounter>();
			List<Address> address = new ArrayList<Address>();
			List<MedOrders> medorders = new ArrayList<MedOrders>();
			List<Item> items = new ArrayList<Item>();
			List<Person> person = new ArrayList<Person>();

			for (Branch branch : branches) {
				Address add1 = branch.getAddress();
				address.add(add1);

				List<Encounter> enList = branch.getEncounter();
				for (Encounter encounter1 : enList) {
					encounters.add(encounter1);
					List<MedOrders> medList = encounter1.getMedorders();
					for (MedOrders medOrders1 : medList) {
						medorders.add(medOrders1);

						List<Item> itemList = medOrders1.getItems();
						for (Item item1 : itemList) {
							items.add(item1);
						}
					}
				}

			}

			entityTransaction.begin();
			for (Person person2 : person) {
				entityManager.remove(person2);
			}
			for (Branch b1 : branches) {
				entityManager.remove(b1);
			}
			for (Encounter e1 : encounters) {
				entityManager.remove(e1);
			}
			for (Address a1 : address) {
				entityManager.remove(a1);
			}
			for (MedOrders m1 : medorders) {
				entityManager.remove(m1);
			}
			for (Item i1 : items) {
				entityManager.remove(i1);
			}
			entityManager.remove(hospital);
			entityTransaction.commit();
		} else {
			throw new HospitalNotFoundException();
		}
	}

	public void updateHospitalName(int id, String name) {

		EntityManager entityManager = HospitalDao.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Hospital hospital = entityManager.find(Hospital.class, id);

		if (hospital != null) {
			hospital.setName(name);
			entityTransaction.begin();
			entityManager.merge(hospital);
			entityTransaction.commit();
		} else {
			throw new HospitalNotFoundException();
		}
	}

}
