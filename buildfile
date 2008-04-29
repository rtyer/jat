require 'buildr'

VERSION_NUMBER = '0.1'

JMOCK = 'org.jmock:jmock:jar:2.4.0'
HAMCREST = group('hamcrest-core', 'hamcrest-library', :under => 'org.hamcrest', :version => '1.1')
TESTNG = 'org.testng:testng:jar:5.8'
CGLIB = 'cglib:cglib-nodep:jar:2.1_3'
OBJ = 'org.objenesis:objenesis:jar:1.1'

repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.remote << 'http://public.planetmirror.com/pub/maven2'

desc 'Java AutoTest (JAT)'
define 'jat' do
   project.version = VERSION_NUMBER
   compile.options.target = "1.5"

   test.using :testng
   
   desc 'Auto Testing Framework for Java'
   define 'autotest' do
     compile.with TESTNG
     test.with TESTNG, JMOCK, HAMCREST, CGLIB, OBJ
     
     package :jar
   end   
end
