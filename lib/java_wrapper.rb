require 'rjb'
require 'facets/memoize'

class JavaWrapper
  def initialize(classpath)
    Rjb::load(classpath.join(':'))
  end
  
  def import(classname)
    clazz = Rjb.import(classname)
  end
  memoize :import 
end