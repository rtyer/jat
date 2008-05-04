package org.jatproject.autotest.repositories;

import org.jatproject.autotest.DependencyRepository;
import org.jatproject.autotest.ClassPath;
import org.jatproject.autotest.ClassFiles;
import org.jatproject.autotest.ClassFile;
import org.jatproject.autotest.Classname;

public class SimpleDependencyRepository implements DependencyRepository
{
    private ClassPath classpath;

    public SimpleDependencyRepository(ClassPath classpath)
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
