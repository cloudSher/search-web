package com.wlqq.search.web.userCenter;

import com.wlqq.unify.sso.bean.UnifySession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wei.zhao on 2017/8/1.
 */
public class UserCenterAuthContext {


    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    static final int appClientId = 159;
    static final String LOCAL_IP = "10.20.0.116";
    static final String DRIVER_DESC = "Search-Monitor";

    private Map<String,String> request = new HashMap<>();
    private UnifySession session;


    public Map<String,String> request(){
        return this.request;
    }

    public void put(String key,String val){
        request.put(key,val);
    }

    public UnifySession getSession() {
      return session;
    }

    public void setSession(UnifySession session) {
      this.session = session;
    }
}
