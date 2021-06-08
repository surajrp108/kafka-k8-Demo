package com.srp.users.pojos;

public class ExceptionMessage {
    private Long status;
    private String message;
    private String code;

    public ExceptionMessage(Long status, String message, String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
