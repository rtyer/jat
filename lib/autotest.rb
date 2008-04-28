class ClassChangeListener
  def initialize(tester)
    @tester = tester
  end
  
  def changed(classname)
    @tester.run_tests([classname + 'Test'])
  end
end

class AutoTest
  def initialize(src_class_directory, tester)
    @class_monitor = ClassMonitor.new(src_class_directory)
    @listener = ClassChangeListener.new(tester)
  end
  
  def run
    @class_monitor.watch(@listener)
  end
end