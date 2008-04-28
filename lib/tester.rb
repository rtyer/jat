class TestNG
  
  def initialize(java_wrapper, clazz)
    @java_wrapper = java_wrapper
    @clazz = clazz
  end
  
  def run_tests(tests = [])
    testng = @java_wrapper.import('org.testng.TestNG').new
    testng.setTestClasses(@clazz.for_all(tests))
    testng.addListener(@java_wrapper.import('org.testng.reporters.DotTestListener').new)
    testng.run
  end
  
end