package com.tunkkaus.grails

import grails.converters.JSON

class ExampleController {
  
  def ejbService

  def fetchData() {
    List dataEntries = ejbService.dataFetchManager.searchData("asdasd")
    render dataEntries as JSON
  }
  
}
