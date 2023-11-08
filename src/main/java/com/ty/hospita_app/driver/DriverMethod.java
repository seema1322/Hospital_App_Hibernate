package com.ty.hospita_app.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ty.hospita_app.dao.AddressDao;
import com.ty.hospita_app.dao.BranchDao;
import com.ty.hospita_app.dao.EncounterDao;
import com.ty.hospita_app.dao.HospitalDao;
import com.ty.hospita_app.entity.Address;
import com.ty.hospita_app.entity.Branch;
import com.ty.hospita_app.entity.Encounter;
import com.ty.hospita_app.entity.Hospital;
import com.ty.hospita_app.entity.Item;
import com.ty.hospita_app.entity.MedOrders;
import com.ty.hospita_app.entity.Person;
import com.ty.hospita_app.exceptions.AddressNotFoundException;
import com.ty.hospita_app.exceptions.BranchNotFoundException;
import com.ty.hospita_app.exceptions.EncounterDetailsNotFound;
import com.ty.hospita_app.exceptions.HospitalNotFoundException;

public class DriverMethod {
	static Scanner scanner = new Scanner(System.in);
	static HospitalDao hospitalDao = new HospitalDao();
	static BranchDao branchDao = new BranchDao();
	static AddressDao addressDao = new AddressDao();
	static EncounterDao encounterDao = new EncounterDao();

	public static void saveHospital() {

		System.out.println("Enter hospital id");
		int hId = scanner.nextInt();
		System.out.println("Enter hospital name");
		String hName = scanner.next();

		List<Branch> branchList = new ArrayList<Branch>();
		System.out.println("Enter number of branches you want to add");
		int count = scanner.nextInt();
		for (int i = 0; i < count; i++) {

			System.out.println("Enter branch id");
			int bId = scanner.nextInt();
			System.out.println("Enter branch name");
			String bName = scanner.next();
			System.out.println("Enter branch location");
			String bLocation = scanner.next();

			Address address = new Address();
			System.out.println("Enter branch address id");
			int aId = scanner.nextInt();
			System.out.println("Enter place");
			String aPlace = scanner.next();
			System.out.println("Enter state");
			String aState = scanner.next();
			System.out.println("Enter pincode");
			long pincode = scanner.nextLong();
			address.setId(aId);
			address.setPlace(aPlace);
			address.setState(aState);
			address.setPincode(pincode);
			List<Encounter> encount = new ArrayList<Encounter>();

			System.out.println("Enter number of encounters available");
			int en_count = scanner.nextInt();

			for (int j = 0; j < en_count; j++) {

				Person person1 = new Person();
				System.out.println("Enter person id");
				int personId = scanner.nextInt();
				System.out.println("Enter person name");
				String pName = scanner.next();
				System.out.println("Enter person age");
				int age = scanner.nextInt();
				System.out.println("Enter phone number");
				long phone = scanner.nextLong();
				System.out.println("Enter bllod_group");
				String bgroup = scanner.next();
				person1.setId(personId);
				person1.setName(pName);
				person1.setAge(age);
				person1.setPhone(phone);
				person1.setBlood_group(bgroup);

				System.out.println("Enter encounter id");
				int eId = scanner.nextInt();
				System.out.println("Enter encounter cause");
				String cause = scanner.next();
				System.out.println("Encounter date");
				String date = scanner.next();
				System.out.println("Discharge date");
				String discharge = scanner.next();

				List<MedOrders> medList = new ArrayList<MedOrders>();

				System.out.println("Enter the number of orders you want to give");
				int orderCount = scanner.nextInt();
				for (int k = 0; k < orderCount; k++) {

					System.out.println("Enter order id");
					int oId = scanner.nextInt();
					System.out.println("Enter time");
					String time = scanner.next();
					System.out.println("Order bill");
					double bill = scanner.nextDouble();

					List<Item> itemList = new ArrayList<Item>();
					System.out.println("Enter number of items to add");
					int itemCount = scanner.nextInt();

					for (int l = 0; l < itemCount; l++) {

						System.out.println("Enter the item id");
						int itemId = scanner.nextInt();
						System.out.println("Enter item name");
						String itemName = scanner.next();
						System.out.println("Enter the number of items needed");
						int iCount = scanner.nextInt();

						Item item = new Item();
						item.setId(itemId);
						item.setItem_name(itemName);
						item.setCount(iCount);
						itemList.add(item);
					}

					MedOrders medOrder = new MedOrders();
					medOrder.setId(oId);
					medOrder.setOrder_cost(bill);
					medOrder.setTime(time);
					medOrder.setItems(itemList);
					medList.add(medOrder);
				}

				Encounter encounter1 = new Encounter();
				encounter1.setId(eId);
				encounter1.setCause(cause);
				encounter1.setAdmitDate(date);
				encounter1.setDischarge_date(discharge);
				encounter1.setMedorders(medList);
				encounter1.setPerson(person1);
				encount.add(encounter1);
			}
			Branch branch = new Branch();
			branch.setId(bId);
			branch.setAddress(address);
			branch.setEncounter(encount);
			branch.setLocation(bLocation);
			branch.setName(bName);
			branchList.add(branch);
		}
		Hospital hospital = new Hospital();
		hospital.setId(hId);
		hospital.setName(hName);
		hospital.setBranches(branchList);

		hospitalDao.saveHospital(hospital);
		System.out.println("Hospital details saved");
	}

	public static void viewHospital() {
		System.out.println("Enter hospital id");
		int id = scanner.nextInt();

		try {

			Hospital hospital = hospitalDao.viewHospitalById(id);
			System.out.println("Hospital id: " + hospital.getId());
			System.out.println("Hospital name: " + hospital.getName());
			System.out.println("--------Branches-----------");
			List<Branch> branchList = hospital.getBranches();

			for (Branch branch : branchList) {
				System.out.println("Branch id: " + branch.getId());
				System.out.println("Branch name: " + branch.getName());
				System.out.println("Branch location: " + branch.getLocation());

				System.out.println("=========Branch Address===========");
				Address address = branch.getAddress();
				System.out.println("Address Id: " + address.getId());
				System.out.println(address.getPlace() + " " + address.getState() + " " + address.getPincode());

				System.out.println("*******Encounter availables*****************");
				List<Encounter> encounters = branch.getEncounter();

				for (Encounter en : encounters) {
					System.out.println("Encounter id: " + en.getId());
					System.out.println("Encountering cause: " + en.getCause());
					System.out.println("Encountered date: " + en.getAdmitDate());
					System.out.println("Discharge date: " + en.getDischarge_date());

					Person person = en.getPerson();
					System.out.println("=========person info==========");
					System.out.println("Person id: " + person.getId());
					System.out.println("Person name: " + person.getName());
					System.out.println("Person age: " + person.getAge());
					System.out.println("Person phone number: " + person.getPhone());
					System.out.println("Person Blood group: " + person.getBlood_group());

					List<MedOrders> orderList = en.getMedorders();
					for (MedOrders medorder : orderList) {
						System.out.println("===========order info==============");
						System.out.println("Order id: " + medorder.getId());
						System.out.println("Order placed time: " + medorder.getTime());
						System.out.println("Order bill: " + medorder.getOrder_cost());
						List<Item> itemList = medorder.getItems();

						for (Item item : itemList) {
							System.out.println("----------Items Info--------");
							System.out.println("Item id: " + item.getId());
							System.out.println("Item Name: " + item.getItem_name());
							System.out.println("Item count: " + item.getCount());
						}

					}
				}
			}

		} catch (HospitalNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void updateHospitalName() {
		System.out.println("Enter id to update");
		int id = scanner.nextInt();
		System.out.println("Enter name");
		String name = scanner.next();

		try {
			hospitalDao.updateHospitalName(id, name);
			System.out.println("*******************************");
			System.out.println("Hospital name updated");
		} catch (HospitalNotFoundException e) {
			System.out.println("*******************************");
			System.out.println(e.getMessage());
		}
	}

	public static void deleteHospital() {

		System.out.println("Enter id to delete");
		int id = scanner.nextInt();
		try {
			hospitalDao.deleteHospital(id);
			System.out.println("*******************************");
			System.out.println("Hospital details deleted");
		} catch (HospitalNotFoundException e) {
			System.out.println("*******************************");
			System.out.println(e.getMessage());
		}

	}

	public static void addBranch() {
		System.out.println("Enter hospital id to add branch");
		int hospital_id = scanner.nextInt();

		try {
			System.out.println("Enter branch id");
			int bId = scanner.nextInt();
			System.out.println("Enter branch name");
			String bName = scanner.next();
			System.out.println("Enter branch location");
			String bLocation = scanner.next();

			Address address = new Address();
			System.out.println("Enter branch address id");
			int aId = scanner.nextInt();
			System.out.println("Enter place");
			String aPlace = scanner.next();
			System.out.println("Enter state");
			String aState = scanner.next();
			System.out.println("Enter pincode");
			long pincode = scanner.nextLong();
			address.setId(aId);
			address.setPlace(aPlace);
			address.setState(aState);
			address.setPincode(pincode);
			List<Encounter> encount = new ArrayList<Encounter>();

			System.out.println("Enter number of encounters available");
			int en_count = scanner.nextInt();

			for (int j = 0; j < en_count; j++) {

				Person person1 = new Person();
				System.out.println("Enter person id");
				int personId = scanner.nextInt();
				System.out.println("Enter person name");
				String pName = scanner.next();
				System.out.println("Enter person age");
				int age = scanner.nextInt();
				System.out.println("Enter phone number");
				long phone = scanner.nextLong();
				System.out.println("Enter bllod_group");
				String bgroup = scanner.next();
				person1.setId(personId);
				person1.setName(pName);
				person1.setAge(age);
				person1.setPhone(phone);
				person1.setBlood_group(bgroup);

				System.out.println("Enter encounter id");
				int eId = scanner.nextInt();
				System.out.println("Enter encounter cause");
				String cause = scanner.next();
				System.out.println("Encounter date");
				String date = scanner.next();
				System.out.println("Discharge date");
				String discharge = scanner.next();

				List<MedOrders> medList = new ArrayList<MedOrders>();

				System.out.println("Enter the number of orders you want to give");
				int orderCount = scanner.nextInt();
				for (int k = 0; k < orderCount; k++) {

					System.out.println("Enter order id");
					int oId = scanner.nextInt();
					System.out.println("Enter time");
					String time = scanner.next();
					System.out.println("Order bill");
					double bill = scanner.nextDouble();

					List<Item> itemList = new ArrayList<Item>();
					System.out.println("Enter number of items to add");
					int itemCount = scanner.nextInt();

					for (int l = 0; l < itemCount; l++) {

						System.out.println("Enter the item id");
						int itemId = scanner.nextInt();
						System.out.println("Enter item name");
						String itemName = scanner.next();
						System.out.println("Enter the number of items needed");
						int iCount = scanner.nextInt();

						Item item = new Item();
						item.setId(itemId);
						item.setItem_name(itemName);
						item.setCount(iCount);
						itemList.add(item);
					}

					MedOrders medOrder = new MedOrders();
					medOrder.setId(oId);
					medOrder.setOrder_cost(bill);
					medOrder.setTime(time);
					medOrder.setItems(itemList);
					medList.add(medOrder);
				}

				Encounter encounter1 = new Encounter();
				encounter1.setId(eId);
				encounter1.setCause(cause);
				encounter1.setAdmitDate(date);
				encounter1.setDischarge_date(discharge);
				encounter1.setMedorders(medList);
				encounter1.setPerson(person1);
				encount.add(encounter1);
			}
			Branch branch = new Branch();
			branch.setId(bId);
			branch.setAddress(address);
			branch.setEncounter(encount);
			branch.setLocation(bLocation);
			branch.setName(bName);

			branchDao.saveBranch(branch, hospital_id);
			System.out.println("Branch added");
		} catch (HospitalNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateBranchName() {
		System.out.println("Enter branch id");
		int id = scanner.nextInt();
		System.out.println("Enter branch name");
		String name = scanner.next();
		try {
			branchDao.updateBranchName(id, name);
			System.out.println("Branch name updated");
		} catch (BranchNotFoundException e) {
			System.out.println("=============================");
			System.out.println(e.getMessage());
		}
	}

	public static void updateAddress() {
		System.out.println("Enter address id to update");
		int aId = scanner.nextInt();
		System.out.println("Enter place");
		String aPlace = scanner.next();
		System.out.println("Enter state");
		String aState = scanner.next();
		System.out.println("Enter pincode");
		long pincode = scanner.nextLong();
		Address address = new Address();
		address.setId(aId);
		address.setPlace(aPlace);
		address.setState(aState);
		address.setPincode(pincode);

		try {
			addressDao.updateAddress(address);
			System.out.println("Address updated successfully");
		} catch (AddressNotFoundException e) {
			System.out.println("===============================");
			System.out.println(e.getMessage());
		}

	}

	public static void updateLocation() {
		System.out.println("Enter branch id");
		int id = scanner.nextInt();
		System.out.println("Enter branch location");
		String location = scanner.next();
		try {
			branchDao.updateLocation(id, location);
			System.out.println("Branch location updated");
		} catch (BranchNotFoundException e) {
			System.out.println("=============================");
			System.out.println(e.getMessage());
		}

	}

	public static void encounterPerson() {
		System.out.println("Enter branch id to encounter");
		int branch_id = scanner.nextInt();

		Person person1 = new Person();
		System.out.println("Enter person id");
		int personId = scanner.nextInt();
		System.out.println("Enter person name");
		String pName = scanner.next();
		System.out.println("Enter person age");
		int age = scanner.nextInt();
		System.out.println("Enter phone number");
		long phone = scanner.nextLong();
		System.out.println("Enter bllod_group");
		String bgroup = scanner.next();
		person1.setId(personId);
		person1.setName(pName);
		person1.setAge(age);
		person1.setPhone(phone);
		person1.setBlood_group(bgroup);

		System.out.println("Enter encounter id");
		int eId = scanner.nextInt();
		System.out.println("Enter encounter cause");
		String cause = scanner.next();
		System.out.println("Encounter date");
		String date = scanner.next();
		System.out.println("Discharge date");
		String discharge = scanner.next();

		List<MedOrders> medList = new ArrayList<MedOrders>();

		System.out.println("Enter the number of orders you want to give");
		int orderCount = scanner.nextInt();
		for (int k = 0; k < orderCount; k++) {

			System.out.println("Enter order id");
			int oId = scanner.nextInt();
			System.out.println("Enter time");
			String time = scanner.next();
			System.out.println("Order bill");
			double bill = scanner.nextDouble();

			List<Item> itemList = new ArrayList<Item>();
			System.out.println("Enter number of items to add");
			int itemCount = scanner.nextInt();

			for (int l = 0; l < itemCount; l++) {

				System.out.println("Enter the item id");
				int itemId = scanner.nextInt();
				System.out.println("Enter item name");
				String itemName = scanner.next();
				System.out.println("Enter the number of items needed");
				int iCount = scanner.nextInt();

				Item item = new Item();
				item.setId(itemId);
				item.setItem_name(itemName);
				item.setCount(iCount);
				itemList.add(item);
			}

			MedOrders medOrder = new MedOrders();
			medOrder.setId(oId);
			medOrder.setOrder_cost(bill);
			medOrder.setTime(time);
			medOrder.setItems(itemList);
			medList.add(medOrder);
		}
		Encounter encounter1 = new Encounter();
		encounter1.setId(eId);
		encounter1.setCause(cause);
		encounter1.setAdmitDate(date);
		encounter1.setDischarge_date(discharge);
		encounter1.setMedorders(medList);
		encounter1.setPerson(person1);

		try {
			encounterDao.encounterPerson(encounter1, branch_id);
			System.out.println("Person encountered successfully");
		} catch (BranchNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteEncounter() {
		System.out.println("Enter encounter id to delete");
		int encounter_id= scanner.nextInt();
		System.out.println("Enter branch id");
		int branch_id= scanner.nextInt();
		
		try {
			
			encounterDao.deleteEncounter(encounter_id, branch_id);
			System.out.println("Details deleted");
		} catch (BranchNotFoundException e) {
			System.out.println(e.getMessage());
		}catch (EncounterDetailsNotFound e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteBranch() {
		System.out.println("Enter hospital id");
		int hospital_id=scanner.nextInt();
		
		System.out.println("Enter branch_id");
		int branch_id= scanner.nextInt();
		
		try {
			branchDao.deleteBranch(branch_id, hospital_id);
			System.out.println("Branch info deleted");
		} catch (BranchNotFoundException e) {
			System.out.println(e.getMessage());
		}catch (HospitalNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}
