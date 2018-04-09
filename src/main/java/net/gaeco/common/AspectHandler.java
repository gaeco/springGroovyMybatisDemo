package net.gaeco.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Aspect
public class AspectHandler {
    @Autowired
    private RequestInfo reqInfo;

     //DAO Method 수행전에 Session 파라미터를 넣기 위해 사용
    @Around("execution(* net.gaeco.dao..*.*(..))")
        public Object beforeSessionTargetMethod(ProceedingJoinPoint thisJoinPoint) throws Throwable{
        Object[] argument = thisJoinPoint.getArgs();

        if(reqInfo.getUserId() != null && argument!= null ){
            for (int i = 0; i < argument.length; i++) {
                ((HashMap)argument[i]).put("_user_id",reqInfo.getUserId());
            }
        }

        Object resultObj = thisJoinPoint.proceed(argument);
        return resultObj;
    }

    @Around("execution(* net.gaeco.controller..*.*(..))")
    public Object afterReturnValueTargetMethod(ProceedingJoinPoint thisJoinPoint) throws Throwable{
            Object obj = thisJoinPoint.proceed(); //핵심 기능 실행
            if(obj != null) {
                return ControllerResultMapping.setResultReturn(obj);
            }
            else{
                return obj;
            }



    }

}
