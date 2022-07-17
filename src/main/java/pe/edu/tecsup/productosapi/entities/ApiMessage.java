package pe.edu.tecsup.productosapi.entities;

public class ApiMessage {
    
    private String message;

	public static ApiMessage createMessage(String message) {
		ApiMessage apiMessage = new ApiMessage();
		apiMessage.setMessage(message);
		return apiMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ApiMessage [message=" + message + "]";
	}


}
