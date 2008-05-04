package org.jatproject.autotest.mappers;

import org.jatproject.autotest.DependencyRepository;
import org.jatproject.autotest.ClassPath;
import org.jatproject.autotest.ClassFiles;
import org.jatproject.autotest.ClassFile;
import org.jatproject.autotest.Classname;

public class SimpleTestMapper implements DependencyRepository
{
    private ClassPath classpath;

    public SimpleTestMapper(ClassPath classpath)
    {
        this.classpath = classpath;
    }

    public ClassFiles findDependenciesFor(ClassFile changedClass)
    {
        String className = changedClass.getClassName();

        if(className.endsWith("Test"))
        {
            return new ClassFiles(changedClass);
        }
        else
        {
            return new ClassFiles(classpath.find(new Classname(className + "Test")));
        }
    }
}
