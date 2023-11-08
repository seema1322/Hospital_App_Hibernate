package com.ty.hospita_app.exceptions;

public class HospitalNotFoundException extends RuntimeException {
	public String getMessage() {
		return "Hospital details not found";
	}
}
