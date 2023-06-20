package org.hbrs.se2.project.aldavia.dtos;

import org.hbrs.se2.project.aldavia.util.enums.Reason;

public class RegistrationResult {
	private boolean result;
	
	private Reason reason;

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Reason getReason() {
		return reason;
	}

	/**
	 * Setzen eines Grunds für die fehlerhafte Registrierung.
	 * @param reason Grund für die fehlerhafte Registrierung.
	 */
	public void setReason(Reason reason) {
		this.reason = reason;
	}
	
	

}
