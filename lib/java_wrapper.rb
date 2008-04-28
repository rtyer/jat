require 'rjb'

class JavaWrapper
  def initialize(classpath)
    @classpath = classpath
  end
  
  def import(classname)
    Rjb.load(@classpath.join(':'))
    Rjb.import(classname)
  end
end