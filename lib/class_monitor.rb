require 'filesystemwatcher'

class ClassMonitor
  def initialize(class_dir)
    @class_dir = class_dir
    
    @watcher = FileSystemWatcher.new
    @watcher.addDirectory(class_dir)
    @watcher.sleepTime = 10
  end
  
  def watch(class_change_listener)
    @watcher.start do |status,file|
        class_change_listener.changed(file.to_fully_qualified_java_classname(@class_dir))
    end
    @watcher.join
  end
end