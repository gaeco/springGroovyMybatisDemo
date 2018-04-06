package net.gaeco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHandler {

    static Logger logger = Logger.getLogger(ExceptionHandler.class.getName());

    @org.springframework.web.bind.annotation.ExceptionHandler(value={CustomException.class})
    @ResponseBody
    public ResponseEntity<ExceptionInfo> handleCustomException(CustomException _ex){
        ExceptionInfo err = new ExceptionInfo();
        err.set_status("FAIL");
        err.set_msg(_ex.getMessage());
        err.set_errCode(_ex.getErrCode());
        StringWriter sw = new StringWriter();
        _ex.printStackTrace(new PrintWriter(sw));
        err.set_stackTrace(sw.toString());
        logger.warning(sw.toString());
        return new ResponseEntity<ExceptionInfo>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value={NullPointerException.class})
    @ResponseBody
    public ResponseEntity<ExceptionInfo> handleNullPointException(Exception _ex){
        ExceptionInfo err = new ExceptionInfo();
        err.set_status("FAIL");
        err.set_msg("A null pointer Exception ocurred");
        StringWriter sw = new StringWriter();
        _ex.printStackTrace(new PrintWriter(sw));
        err.set_stackTrace(sw.toString());
        logger.warning(sw.toString());
        return new ResponseEntity<ExceptionInfo>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value={FileNotFoundException.class})
    public ResponseEntity<ExceptionInfo> handleNotFoundException(FileNotFoundException _ex){
        ExceptionInfo err = new ExceptionInfo();
        err.set_status("FAIL");
        err.set_msg(_ex.getMessage());
        StringWriter sw = new StringWriter();
        _ex.printStackTrace(new PrintWriter(sw));
        err.set_stackTrace(sw.toString());
        logger.warning(sw.toString());
        return new ResponseEntity<ExceptionInfo>(err, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(value={Exception.class})
    @ResponseBody
    public ResponseEntity<ExceptionInfo> handleEtcException(Exception _ex){
        ExceptionInfo err = new ExceptionInfo();
        err.set_status("FAIL");
        err.set_msg(_ex.getMessage());
        StringWriter sw = new StringWriter();
        _ex.printStackTrace(new PrintWriter(sw));
        err.set_stackTrace(sw.toString());
        logger.warning(sw.toString());
        return new ResponseEntity<ExceptionInfo>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
