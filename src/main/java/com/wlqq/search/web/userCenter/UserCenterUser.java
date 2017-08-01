package com.wlqq.search.web.userCenter;

import com.wlqq.unify.sso.bean.UnifySession;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;

/**
 * Created by wei.zhao on 2017/8/1.
 */
public class UserCenterUser extends AbstractUser{

  private String username;
  private Vertx vertx;
  private UnifySession session;

  public UserCenterUser(String username, Vertx vertx, UnifySession session){
      this.username = username;
      this.vertx = vertx;
      this.session = session;
  }

  @Override
  protected void doIsPermitted(String s, Handler<AsyncResult<Boolean>> handler) {

  }

  @Override
  public JsonObject principal() {
    return null;
  }

  @Override
  public void setAuthProvider(AuthProvider authProvider) {

  }
}
