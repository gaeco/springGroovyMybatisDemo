package net.gaeco.common;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.List;

/**
 * Created by gs on 2018-01-30.
 */
public class ControllerMapping {
    //session , obj 값 map으로
    public static Map setSessionObjectMerge(HttpSession session, Map obj) {
        if(obj == null){
            obj = new HashMap();
        }
        HashMap userInfo = (HashMap)session.getAttribute(Constants.SESSION_INFO);
        obj.put(Constants.SESSION_EMPNO, userInfo.get(Constants.SESSION_EMPNO));  //사번
        obj.put(Constants.SESSION_LOCALE, userInfo.get(Constants.SESSION_LOCALE));  //다국어
        return obj;
    }
    //session , obj 값 map으로
    public static List<Map> setSessionListMerge(HttpSession session, List<Map> list) {
        HashMap userInfo = (HashMap)session.getAttribute(Constants.SESSION_INFO);
        for(Map item : list){
            item.put(Constants.SESSION_EMPNO, userInfo.get(Constants.SESSION_EMPNO));  //사번
            item.put(Constants.SESSION_LOCALE, userInfo.get(Constants.SESSION_LOCALE));  //다국어
        }
        return list;
    }
}
