package com.tunkkaus.grails

class EjbLookupException extends RuntimeException {

  Exception cause
  
  EjbLookupException(Exception original) {
    super("EJB lookup failed")
    cause = original
  }
  
}
