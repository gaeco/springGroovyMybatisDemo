package net.gaeco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends Exception {


    private String message;

    public FileNotFoundException(String _msg){
        this.message = _msg;
    }

    public String getMessage(){
        return this.message;
    }

}
