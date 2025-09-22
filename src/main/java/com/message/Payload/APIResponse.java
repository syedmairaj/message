package com.message.Payload;

import org.springframework.http.HttpStatusCode;

public class APIResponse<T> {

    private T Data ;
    private int Status;
    private String message;
    private String field;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}


