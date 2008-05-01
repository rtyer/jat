package org.jatproject.autotest;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleTestMapper implements TestMapper
{
    private TestAsserter asserter;
    private AutoTestClassLoader loader;

    public SimpleTestMapper(TestAsserter asserter, AutoTestClassLoader loader)
    {
        this.asserter = asserter;
        this.loader = loader;
    }

    public Class[] findTestsFor(ClassFiles changedClasses)
    {
        ArrayList<Class> testClasses = new ArrayList<Class>();

        for(ClassFile clazz : changedClasses)
        {
            testClasses.addAll(Arrays.asList(findTestsFor(clazz)));
        }

        return testClasses.toArray(new Class[testClasses.size()]);
    }

    public Class[] findTestsFor(ClassFile changedClass)
    {
        try
        {
            String className = changedClass.getClassName();
            Class clazz = loader.loadClass(className, changedClass);

            if(asserter.isTest(clazz))
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
