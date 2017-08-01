package com.wlqq.search.util;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Created by wei.zhao on 2017/7/31.
 */
public class Runner {


  public static final String WEB_DIR = "search-web";

  public static final String WEB_JAVA_DIR = WEB_DIR + "/src/main/java/";
  public static final String WEB_JS_DIR = WEB_DIR + "/src/main/js/";



  public static void run(Class clazz){
    run(WEB_JAVA_DIR,clazz,new VertxOptions().setClustered(false),null);
  }

  public static void run(Class clazz, DeploymentOptions options){
    run(WEB_JAVA_DIR,clazz,new VertxOptions().setClustered(false),options);
  }

  public static void run(String dir, Class clazz, VertxOptions options, DeploymentOptions deploymentOptions){
    doRun(dir + clazz.getPackage().getName().replace(".","/"),clazz.getName(),options,deploymentOptions);
  }

  public static void doRun(String exampleDir, String verticleID, VertxOptions options, DeploymentOptions deploymentOptions){
    if (options == null) {
      // Default parameter
      options = new VertxOptions();
    }

    try {
      // We need to use the canonical file. Without the file name is .
      File current = new File(".").getCanonicalFile();
      System.out.println("file : "+ current);
      if (exampleDir.startsWith(current.getName()) && !exampleDir.equals(current.getName())) {
        exampleDir = exampleDir.substring(current.getName().length() + 1);
      }
    } catch (IOException e) {
      // Ignore it.
    }

    System.setProperty("vertx.cwd", exampleDir);
    Consumer<Vertx> runner = vertx -> {
      try {
        if (deploymentOptions != null) {
          vertx.deployVerticle(verticleID, deploymentOptions);
        } else {
          vertx.deployVerticle(verticleID);
        }
      } catch (Throwable t) {
        t.printStackTrace();
      }
    };

    runner.accept(Vertx.vertx(options));

  }

}

