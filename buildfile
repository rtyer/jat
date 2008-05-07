require 'buildr'

VERSION_NUMBER = '0.1'

JMOCK = 'org.jmock:jmock:jar:2.4.0'
HAMCREST = group('hamcrest-core', 'hamcrest-library', :under => 'org.hamcrest', :version => '1.1')
TESTNG = 'org.testng:testng:jar:jdk15:5.7'
CGLIB = 'cglib:cglib-nodep:jar:2.1_3'
OBJ = 'org.objenesis:objenesis:jar:1.1'
COMMONS = 'commons-io:commons-io:jar:1.4'
JUNIT = 'junit:junit:jar:4.4'

repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.remote << 'http://public.planetmirror.com/pub/maven2'

desc 'Java AutoTest (JAT)'
define 'jat' do
   project.version = VERSION_NUMBER
   compile.options.target = "1.5"

   test.using :testng
   
   desc 'Auto Testing Framework for Java'
   define 'autotest' do
     compile.with TESTNG, COMMONS, JUNIT
     test.with JMOCK, HAMCREST, CGLIB, OBJ, JUNIT
     package :jar
     
     task('run', :test) do
       Java::Commands.java('org.jatproject.autotest.AutoTestRunner', "#{compile.target}", "#{test.compile.target}", :classpath => test.dependencies)
     end
   end   
end
