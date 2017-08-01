package com.wlqq.search.web;

import com.wlqq.search.util.Runner;
import com.wlqq.search.web.userCenter.UserCenterAuthContext;
import com.wlqq.search.web.userCenter.UserCenterAuthHandler;
import com.wlqq.search.web.userCenter.UserCenterAuthHandlerImpl;
import com.wlqq.search.web.userCenter.UserCenterUser;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.shiro.ShiroAuth;
import io.vertx.ext.auth.shiro.ShiroAuthOptions;
import io.vertx.ext.auth.shiro.ShiroAuthRealmType;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;


/**
 * Created by wei.zhao on 2017/7/31.
 */
public class Server extends AbstractVerticle {



  public static void main(String args[]){
    Runner.run(Server.class);
  }


  public void start(){

    Router router = Router.router(vertx);

    router.route().handler(CookieHandler.create());
    router.route().handler(BodyHandler.create());
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

    AuthProvider authProvider = ShiroAuth.create(vertx, new ShiroAuthOptions().setType(ShiroAuthRealmType.PROPERTIES).setConfig(new JsonObject()));

    router.route().handler(UserSessionHandler.create(authProvider));


    router.route("/private/*").handler(BasicAuthHandler.create(authProvider, "/loginpage.html"));

    router.route("/private/*").handler(RedirectAuthHandler.create(authProvider, "/loginpage.html"));

    router.route("/private/*").handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("private"));

    // Handles the actual login
    router.route("/login").handler(FormLoginHandler.create((authInfo,rec)->{
        vertx.executeBlocking(fut -> {
          String username = authInfo.getString("username");
          String password = authInfo.getString("password");

          UserCenterAuthHandler handler = new UserCenterAuthHandlerImpl();
          UserCenterAuthContext ctx = new UserCenterAuthContext();
          ctx.put(UserCenterAuthContext.USERNAME,username);
          ctx.put(UserCenterAuthContext.PASSWORD,password);
          handler.handle(ctx);
          if(ctx.getSession() != null){
              fut.complete(new UserCenterUser(username,vertx,ctx.getSession()));
          }
        }, rec);
    }).setDirectLoggedInOKURL("/index.html"));


    router.route("/logout").handler(context -> {
      context.clearUser();
      // Redirect back to the index page
      context.response().putHeader("location", "/").setStatusCode(302).end();
    });


    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router::accept).listen(8080);

  }


}
