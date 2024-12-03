package edu.swaroop.servesmart.entity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;


public class Response<T> {
    private String message;
    private T data;
    private int status;

    // Default constructor
    public Response() {
    }

    // Parameterized constructor
    public Response(String message, T data, int status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

   

	public void setStatus(int status) {
		this.status = status;
	}
}
