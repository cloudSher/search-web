package com.wlqq.search.web.userCenter;

import com.wlqq.unify.sso.bean.LoginParameter;
import com.wlqq.unify.sso.bean.UnifySession;
import com.wlqq.unify.sso.service.UnifySessionService;

/**
 * Created by wei.zhao on 2017/8/1.
 */
public class UserCenterAuthHandlerImpl implements UserCenterAuthHandler {

  private static final String remoteUrl = "http://dev.sso.56qq.cn/remote/PFSSOService";

  private static final String WEB_USERCENTER_SESSION = "user_center_session";

  @Override
  public void handle(UserCenterAuthContext ctx) {

      try {
          UnifySessionService service = HessionClient.createService(UnifySessionService.class, remoteUrl);
          LoginParameter.Builder builder = new LoginParameter.Builder();
          LoginParameter parameter = builder.username(ctx.request().get(UserCenterAuthContext.USERNAME))
              .password(ctx.request().get(UserCenterAuthContext.PASSWORD))
              .appClientId(UserCenterAuthContext.appClientId)
              .domainId(1)
              .appClientFlag("")
              .versionCode(0)
              .remoteIp(UserCenterAuthContext.LOCAL_IP)
              .deviceDesc(UserCenterAuthContext.DRIVER_DESC)
              .isPwdEncrypt(true)
              .code("11111")
              .codeType("1").build();

//          UnifySession unifySession = service.loginByUsernameAndPassword(parameter);
//
        UnifySession unifySession = service.startSession(1, parameter.getUsername(), parameter.getPassword(), parameter.getAppClientId(),
          parameter.getAppClientFlag(), parameter.getRemoteIp(), parameter.getDeviceDesc(), parameter.getFingerprint(), parameter.getVersionCode());
        if(unifySession != null){
              ctx.setSession(unifySession);
          }
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
}
