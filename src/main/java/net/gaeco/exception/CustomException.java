package net.gaeco.exception;

public class CustomException extends RuntimeException {

    private String message;
    private String errCode;//Session Error Code : , Excel Error Code :

    public CustomException(String _msg, String _errCode){
        this.message = _msg;
        this.errCode = _errCode;
    }

    public String getMessage(){
        return this.message;
    }
    public String getErrCode(){
        return this.errCode;
    }
}
