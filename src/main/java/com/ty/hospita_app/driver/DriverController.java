package com.ty.hospita_app.driver;
import java.util.Scanner;
public class DriverController {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

//		HospitalDao hospitalDao = new HospitalDao();
//		BranchDao branchDao = new BranchDao();
//		AddressDao addressDao = new AddressDao();
//		EncounterDao encounterDao= new EncounterDao();
		while (true) {
			System.out.println("Select option from given below");
			System.out.println(
					"1.Save hospital details \n2.View hospital details by id \n3.Update hospital name \n4.Delete Hospital");
			System.out
					.println("5.Add Branch\n6.Update branch name \n7.Update branch address\n8.Update branch location");
			System.out.println("9.Encounter a person \n10.Delete encounter \n11.Remove branch \n12.Delete person");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1: {
				DriverMethod.saveHospital();
			}
				break;

			case 2: {
				DriverMethod.viewHospital();
			}
				break;
			case 3: {
				DriverMethod.updateHospitalName();
			}
				break;
			case 4: {
				DriverMethod.deleteHospital();
			}
				break;
			case 5: {
				DriverMethod.addBranch();
			}
				break;
			case 6: {
				DriverMethod.updateBranchName();
			}
				break;
			case 7: {
				DriverMethod.updateAddress();
			}
				break;
			case 8: {
				
				DriverMethod.updateLocation();

			}
				break;
				
			case 9:{
				DriverMethod.encounterPerson();
				
			}
			break;
			case 10:{
				DriverMethod.deleteEncounter();
			}
			break;
			case 11:{
				DriverMethod.deleteBranch();
			}
			break;
			default:
				break;
			}
		}
	}
}
