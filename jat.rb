require 'lib/java_wrapper'
require 'lib/clazz'
require 'lib/testng'

classpath=[
  '/Users/aesterline/.m2/repository/org/jmock/jmock/2.4.0/jmock-2.4.0.jar',
  '/Users/aesterline/.m2/repository/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar',
  '/Users/aesterline/.m2/repository/org/hamcrest/hamcrest-library/1.1/hamcrest-library-1.1.jar',
  '/Users/aesterline/.m2/repository/javax/servlet/servlet-api/2.4/servlet-api-2.4.jar',
  '/Users/aesterline/.m2/repository/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar',
  '/Users/aesterline/.m2/repository/org/objenesis/objenesis/1.1/objenesis-1.1.jar',
  '/Users/aesterline/.m2/repository/javax/servlet/servlet-api/2.4/servlet-api-2.4.jar',
  '/Users/aesterline/.m2/repository/org/testng/testng/5.5/testng-5.5-jdk15.jar',
  '/Users/aesterline/Projects/conduit/mvc/target/classes',
  '/Users/aesterline/Projects/conduit/mvc/target/test/classes']

java_wrapper = JavaWrapper.new(classpath)
TestNG.new(java_wrapper, Clazz.new(java_wrapper)).run_tests(['org.conduitproject.mvc.routing.URIRouteTest'])