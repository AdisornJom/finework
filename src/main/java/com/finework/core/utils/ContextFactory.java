package com.finework.core.utils;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ContextFactory
{
  private static Context ctx;

  public static InitialContext getInstantContext()
    throws NamingException
  {
    if (ctx == null) {
      Properties props = new Properties();
      props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
      props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
      props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

      props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
      props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

      ctx = new InitialContext(props);
    }
    return (InitialContext)ctx;
  }
}