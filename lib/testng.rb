class TestNG
  
  def initialize(java_wrapper, clazz)
    @java_wrapper = java_wrapper
    @clazz = clazz
  end
  
  def run_tests(tests = [])
    classes = @clazz.for_all(tests)
    return if classes.empty?
    
    testng = @java_wrapper.import('org.testng.TestNG').new
    testng.setVerbose(0)
    testng.setUseDefaultListeners(false)
    testng.addListener(Rjb.bind(TestListener.new, 'org.testng.ITestListener'))
    testng.setTestClasses(classes)
    testng.run
  end
  
end

class TestListener
  def method_missing(method, *args)
    puts method
  end
end