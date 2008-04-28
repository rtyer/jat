require File.dirname(__FILE__) + '/../lib/core'

describe String do
  it 'should be turned into fully qualified java classnames' do
    classdir = '/my/class/directory/'
    result = '/my/class/directory/org/project/Project.class'.to_fully_qualified_java_classname(classdir)
    
    result.should == 'org.project.Project'
  end
  
  it 'should ignore preceeding slash if one is present' do
    classdir = '/my/class/directory'
    result = '/my/class/directory/org/project/Project.class'.to_fully_qualified_java_classname(classdir)
    
    result.should == 'org.project.Project'
  end
end