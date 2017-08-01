package com.wlqq.search.web.userCenter;

import com.caucho.hessian.client.HessianProxyFactory;

import java.net.MalformedURLException;

/**
 * Created by wei.zhao on 2017/8/1.
 */
public class HessionClient {


  public static <T> T createService(Class<T> clazz, String url) throws MalformedURLException {
      HessianProxyFactory factory = new HessianProxyFactory();
      factory.setOverloadEnabled(true);
      Object o = factory.create(clazz, url);
      return (T) o;
  }
}
