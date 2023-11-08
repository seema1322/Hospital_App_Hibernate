package com.ty.hospita_app.exceptions;

public class EncounterDetailsNotFound extends RuntimeException {
	public String getMessage() {
		return "Encounter details are not available";
	}
}
