require 'rjb'

class JavaWrapper
  def initialize
    Rjb.load
  end
  
  def import(classname)
    Rjb.import(classname)
  end
end