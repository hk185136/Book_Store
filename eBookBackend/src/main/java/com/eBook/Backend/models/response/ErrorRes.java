package com.eBook.Backend.models.response;
import org.springframework.http.HttpStatus;


//Class for error response which can be used in any API.
public class ErrorRes {
	//Following fields shows what shall be stored in response
	HttpStatus httpStatus;
    String message;

    //All args constructor
    public ErrorRes(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
    
    //Getters and setters for the above fields
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
