package org.jatproject.autotest;

public class SimpleTestMapper implements TestMapper
{
    private AutoTestClassLoader loader;

    public SimpleTestMapper(AutoTestClassLoader loader)
    {
        this.loader = loader;
    }

    public Class[] findTestsFor(ClassFile changedClass)
    {
        try
        {
            String className = changedClass.getClassName();
            Class clazz = loader.loadClass(className, changedClass);

            if(className.endsWith("Test"))
            {
                return new Class[]{clazz};
            }
            else
            {
                return new Class[]{loader.loadClass(className + "Test")};
            }
        }
        catch(ClassNotFoundException e)
        {
            return new Class[0];
        }
    }
}
