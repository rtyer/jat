class ReloadTester
  def initialize(tester, java_wrapper, reload_classpath)
    @tester = tester
    @java_wrapper = java_wrapper
    
    file = @java_wrapper.import('java.io.File')
    @reload_classpath = reload_classpath.map {|path| file.new(path).toURL}    
  end
  
  def run_tests(tests = [])
    thread = @java_wrapper.import('java.lang.Thread').currentThread
    classloader = @java_wrapper.import('java.net.URLClassLoader').newInstance(@reload_classpath)
    
    thread.setContextClassLoader(classloader)

    @tester.run_tests(tests)
  end
end