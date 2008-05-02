package org.jatproject.autotest.mappers;

import org.jatproject.autotest.TestMapper;
import org.jatproject.autotest.ClassPath;
import org.jatproject.autotest.ClassFiles;
import org.jatproject.autotest.ClassFile;
import org.jatproject.autotest.Classname;

public class SimpleTestMapper implements TestMapper
{
    private ClassPath classpath;

    public SimpleTestMapper(ClassPath classpath)
    {
        this.classpath = classpath;
    }

    public ClassFiles findTestsFor(ClassFile changedClass)
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
