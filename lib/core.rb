class String
  def to_fully_qualified_java_classname(class_dir)
    classname = self.sub(Regexp.new(class_dir), '')
    classname.sub(/.class$/, '').sub(/^\//, '').tr('/', '.')
  end
end
