package com.tunkkaus.grails.service

import grails.util.Environment;

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy;
import javax.naming.Context
import javax.naming.InitialContext
import javax.naming.NamingException

import com.tunkkaus.ejb.DataFetchManager
import com.tunkkaus.ejb.impl.DataFetchManagerBean
import com.tunkkaus.grails.EjbLookupException



class EjbService {
  
  // for some reason, lookups fail after certain period of inactivity
  static scope = "request"

  private Context ejbContext

  @PostConstruct
  void init() {
    def jndiProperties = new Properties()
    if (Environment.current == Environment.DEVELOPMENT) {
      // DEV MODE - WE ARE USING DIFFERENT APP CONTAINER FOR EJB
      jndiProperties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory")
      jndiProperties.setProperty(Context.PROVIDER_URL, "remote://${hostname}")
      jndiProperties.setProperty(Context.SECURITY_PRINCIPAL, username)
      jndiProperties.setProperty(Context.SECURITY_CREDENTIALS, password)
      jndiProperties.setProperty("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
      // this is needed for "ebj:..." naming syntax
      jndiProperties.setProperty("jboss.naming.client.ejb.context", "true")
    } else {
      // PROD MODE - WE DEPLOYING OUR WAR AND EJB INSIDE SAME EAR
      // we don't actually need to do anything.. =)
    }
    // create a context passing these properties
    ejbContext = new InitialContext(jndiProperties)
  }
  
  @PreDestroy
  void destroy() {
    try {
      ejbContext?.close()
    } catch (Exception ex) {
      log.error "Exception occured while closing ejb context", ex
    }
  }
  
  private String getUsername() { "matti" }
  private String getPassword() { "foobar" }
  private String getHostname() { "localhost:4447" }
  private String getAppName() { "grailsEAR" }
  private String getModuleName() { "grailsEJB" }

    
  private Object getEjb(Class interfaceClass) throws EjbLookupException {
    try {
      def ejbName
      if (Environment.current == Environment.DEVELOPMENT) {
        // remove client calls use jboss/exported/ naming but it's appended inside JBOSS
        // defining only rest of the JNDI path
        ejbName = "$appName/$moduleName/$interfaceClass.simpleName!$interfaceClass.name"
      } else {
        // we can use normal jndi naming convention inside same ear
        ejbName = "java:global/$appName/$moduleName/$interfaceClass.simpleName!$interfaceClass.name"
      }
      log.debug "ejbName: $ejbName"
      return ejbContext.lookup(ejbName)
    } catch (NamingException ex) {
      throw new EjbLookupException(ex)
    }
  }
  
  
  DataFetchManager getDataFetchManager() {
    return getEjb(DataFetchManager)
  }

}
