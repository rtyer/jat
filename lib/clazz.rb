class Clazz
  def initialize(java_wrapper)
    @java_wrapper = java_wrapper
  end
  
  def for_name(classname)
    begin
      @java_wrapper.import('java.lang.Class').forName(classname)
    rescue
      puts classname
      nil
    end
  end
  
  def for_all(classnames = [])
    classnames.map {|classname| for_name(classname)}.select {|clazz| not clazz.nil?}
  end
end