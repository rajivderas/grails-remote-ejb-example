import grails.util.Environment;

final EJB_BUILD_DIR_ROOT = "../grailsEJB/build"

eventSetClasspath = { rootLoader ->
  
  def build = System.getProperty("tunkkaus.add.ejb") 
    
  if(Environment.current == Environment.DEVELOPMENT || build == 'true') {
    println "| Adding EJB project classes to the classpath..."
    def ejbDir = new File(System.getProperty("user.dir"), EJB_BUILD_DIR_ROOT + '/classes')
    assert ejbDir.exists()
    // this is the actual magic
    rootLoader.addURL(ejbDir.toURI().toURL())
    grailsSettings.compileDependencies << ejbDir
  }
  
}