package com.ty.hospita_app.exceptions;

public class AddressNotFoundException extends RuntimeException {
	public String getMessage() {
		return "Address details not found";
	}
}
