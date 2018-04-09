package net.gaeco.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gs on 2018-01-30.
 */
public class ControllerResultMapping {

    //session , obj 값 map으로
    public static Map setResultReturn(Object obj) {

        Map<String, Object> resultMap = new HashMap<String,Object>();

        if(obj instanceof List){
            resultMap.put("_resultList", obj);  //결과값
        }else{
            resultMap.put("_resultObj", obj);  //결과값
        }
        resultMap.put("_status", "OK");    //결과 반환값

        return resultMap;
    }
}
