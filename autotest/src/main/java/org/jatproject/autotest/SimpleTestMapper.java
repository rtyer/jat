package org.jatproject.autotest;

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
