package com.ty.hospita_app.exceptions;

public class BranchNotFoundException extends RuntimeException {
	public String getMessage() {
		return "Branch details are not available";
	}
}
