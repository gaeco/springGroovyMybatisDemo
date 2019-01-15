package net.gaeco.common;

/**
 * Created by gs on 2018-01-27.
 */

import net.gaeco.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {

//    @Autowired
//    private LoginService loginService;
//    @Autowired
//    private RequestService requestService;

    @Autowired
    private RequestInfo requestInfo;


    @Override  //컨트롤러(즉 RequestMapping이 선언된 메서드 진입) 실행 직전에 동작
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        Map userInfo = null;
        if(request.getSession().getAttribute(Constants.SESSION_INFO) == null){
            //request parameter로 empno를 받을 경우 session에 가져온 값을 넣고
            if(request.getParameter("user_id") != null){
                String useId = request.getParameter("user_id");

                Map<String, Object> obj = new HashMap<String,Object>();
                obj.put("user_id", useId);
//                Map userInfo = (Map)loginService.selectUser(obj); //사용자 정보

                if(userInfo != null) {
                    request.getSession().setAttribute(Constants.SESSION_INFO, userInfo);
                }else {
                    //로그인 되어있지 않으면 오류 처리 한다.
                    //throw new CustomException("Session 이 없습니다.",Constants.ERROR_CODE_SESSION);
                }
            }else{
                //임시로 주석처리한다.
//                throw new CustomException("Session 이 없습니다.",Constants.ERROR_CODE_SESSION);
            }
        }

        Map accessObj = new HashMap();
        userInfo = (HashMap)request.getSession().getAttribute(Constants.SESSION_INFO);
        if(userInfo == null){
            userInfo = new HashMap();
        }
        String userId = (String)userInfo.get("user_id");
        accessObj.put("request_url",request.getRequestURL().toString());
        accessObj.put("ip",request.getRemoteAddr());
        accessObj.put("user_id", userId);

//        String requestId = requestService.insertAccessLog(accessObj).toString();
        String requestId =null;
        requestInfo.setLogId(requestId);
        requestInfo.setUserId(userId);

        return result;
     }

    @Override   // 컨트롤러 진입 후 view가 랜더링 되기 전 수행이 됩니다. 컨트롤러에서 예외가 발생되면 실행되지 않는다.
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        //requestService.updateAccessLog(reqInfo.getLogId());

    }

    @Override //뷰를 생성할 때에 예외가 발생할 경우에도 실행이 된다.
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //requestService.updateAccessLog(requestInfo.getLogId());
    }

}
