package com.keb.club_pila.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends BasicResponse{
    private String errorMsg;
    private String errorCode;

    public ErrorResponse(String errorMsg, String errorCode) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public ErrorResponse(String errorMsg){
        this.errorMsg=errorMsg;
        this.errorCode="404";
    }
}
