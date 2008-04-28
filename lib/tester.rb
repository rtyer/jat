require 'rubygems'
require 'rjb'

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

Rjb::load(classpath.join(':'))

dot_test_listener = Rjb.import('org.testng.reporters.DotTestListener')
test_ng = Rjb.import('org.testng.TestNG')
clazz = Rjb.import('java.lang.Class')

testng = test_ng.new
testng.setTestClasses([clazz.forName('org.conduitproject.mvc.routing.URIRouteTest')])
testng.addListener(dot_test_listener.new)
testng.run()



