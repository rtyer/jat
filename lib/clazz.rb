class Clazz
  def initialize(java_wrapper)
    @java_wrapper = java_wrapper
  end
  
  def for_name(classname)
    clazz = @java_wrapper.import('java.lang.Class').forName(classname)
  end
  memoize :for_name
  
  def for_all(classnames = [])
    classnames.map {|classname| for_name(classname)}
  end
end