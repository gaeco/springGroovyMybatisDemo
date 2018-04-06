package net.gaeco.exception;

public class ExceptionInfo {
    String _msg;
    String _status;
    String _errCode;
    String _stackTrace;

    public void set_msg(String msg){
        _msg = msg;
    }
    public String get_msg(){
        return _msg;
    }

    public void set_status(String status){
        _status = status;
    }
    public String get_status(){
        return _status;
    }

    public void set_errCode(String errCode){_errCode = errCode;}
    public String get_errCode(){return _errCode;}

    public void set_stackTrace(String stackTrace){_stackTrace = stackTrace;}
    public String get_stackTrace(){return _stackTrace;}
}
