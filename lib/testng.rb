class TestNG
  
  def initialize(java_wrapper, clazz)
    @java_wrapper = java_wrapper
    @clazz = clazz
  end
  
  def run_tests(tests = [])
    classes = @clazz.for_all(tests)
    return if classes.empty?
    
    testng = @java_wrapper.import('org.testng.TestNG').new
    testng.setTestClasses(classes)
    testng.addListener(@java_wrapper.import('org.testng.reporters.DotTestListener').new)
    testng.run
  end
  
end