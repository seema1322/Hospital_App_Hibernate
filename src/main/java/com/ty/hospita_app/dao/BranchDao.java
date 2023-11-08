package com.ty.hospita_app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.ty.hospita_app.dao.BranchDao;
import com.ty.hospita_app.entity.Address;
import com.ty.hospita_app.entity.Branch;
import com.ty.hospita_app.entity.Encounter;
import com.ty.hospita_app.entity.Hospital;
import com.ty.hospita_app.entity.Item;
import com.ty.hospita_app.entity.MedOrders;
import com.ty.hospita_app.entity.Person;
import com.ty.hospita_app.exceptions.HospitalNotFoundException;
import com.ty.hospita_app.exceptions.BranchNotFoundException;

public class BranchDao {

	private static EntityManager getEntityManager() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("seema");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	public void saveBranch(Branch branch, int hospital_id) {

		EntityManager entityManager = BranchDao.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		Hospital hospital = entityManager.find(Hospital.class, hospital_id);

		if (hospital != null) {

			List<Branch> branches = hospital.getBranches();
			branches.add(branch);
			hospital.setBranches(branches);

			List<Encounter> encounters = new ArrayList<Encounter>();
			List<Address> address = new ArrayList<Address>();
			List<MedOrders> medorders = new ArrayList<MedOrders>();
			List<Item> items = new ArrayList<Item>();
			List<Person> person = new ArrayList<Person>();

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

			entityTransaction.begin();
			entityManager.persist(branch);
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
			entityManager.merge(hospital);
			entityTransaction.commit();

		} else {
			throw new HospitalNotFoundException();
		}

	}

	public Branch viewBranchById(int id) {

		EntityManager entityManager = BranchDao.getEntityManager();

		Branch branch = entityManager.find(Branch.class, id);

		if (branch != null) {
			return branch;
		} else {
			throw new BranchNotFoundException();
		}
	}

	public void updateBranchName(int id, String name) {
		EntityManager entityManager = BranchDao.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Branch branch = entityManager.find(Branch.class, id);
		if (branch != null) {
			branch.setName(name);
			entityTransaction.begin();
			entityManager.merge(branch);
			entityTransaction.commit();
		} else {
			throw new BranchNotFoundException();
		}
	}

	public void updateLocation(int id, String locName) {
		EntityManager entityManager = BranchDao.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Branch branch = entityManager.find(Branch.class, id);
		if (branch != null) {
			branch.setLocation(locName);
			entityTransaction.begin();
			entityManager.merge(branch);
			entityTransaction.commit();
		} else {
			throw new BranchNotFoundException();
		}
	}

	public Address viewBranchAddress(int branch_id) {

		EntityManager entityManager = BranchDao.getEntityManager();

		Branch branch = entityManager.find(Branch.class, branch_id);

		if (branch != null) {
			System.out.println("--------------------------------------");
			System.out.println("Branch loaction is " + branch.getLocation());
			Address address = branch.getAddress();
			return address;
		} else {
			throw new BranchNotFoundException();
		}
	}
	
	public void deleteBranch(int branch_id, int hospital_id) {

		EntityManager entityManager = BranchDao.getEntityManager();
		EntityTransaction entityTransaction= entityManager.getTransaction();
		
		Hospital hospital= entityManager.find(Hospital.class, hospital_id);
		Branch branch=null;
		if (hospital != null) {
			
			List<Branch> branches= hospital.getBranches();
			for (Branch branch1 : branches) {
				if (branch1.getId()==branch_id) {
					branch=branch1;
					branches.remove(branch1);
					break;
				}
			}
			
			if (branch !=null) {
				Address address= branch.getAddress();
				List<Encounter> encounters= branch.getEncounter();
				List<MedOrders> medorders= null;
				//List<Item> items= null;
				List<Person> persons= new ArrayList<Person>();
				for (Encounter en : encounters) {
					medorders=en.getMedorders();
					persons.add(en.getPerson());
				}
				for(MedOrders mo: medorders) {
					//=mo.getItems();
					mo.setItems(null);
				}
				
				entityTransaction.begin();
				entityManager.merge(hospital);
				
				entityManager.remove(address);
				for(Encounter en: encounters) {
					entityManager.remove(en);
				}
				for(MedOrders mo: medorders) {
					entityManager.remove(mo);
				}
//				for(Item i:items) {
//					entityManager.remove(i);
//				}
				for(Person p: persons) {
					entityManager.remove(p);
				}
				entityTransaction.commit();
				
			} else {
				throw new BranchNotFoundException();
			}
			
		} else {
			throw new HospitalNotFoundException();
		}
		
		
	}
}
