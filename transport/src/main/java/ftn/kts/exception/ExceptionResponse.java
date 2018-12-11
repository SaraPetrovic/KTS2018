package ftn.kts.exception;


public class ExceptionResponse {

	private String errorMessage;
	
	public ExceptionResponse() {
		
	}

	public ExceptionResponse(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
}
