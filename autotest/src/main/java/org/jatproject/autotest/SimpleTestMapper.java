package org.jatproject.autotest;

import java.util.ArrayList;

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

        try
        {
            Class[] classes = changedClasses.toClassArray(loader);
            for(Class currentClass : classes)
            {
                if(asserter.isTest(currentClass))
                {
                    testClasses.add(currentClass);
                }
                else
                {
                    String className = currentClass.getName();
                    testClasses.add(loader.loadClass(className + "Test"));
                }
            }

        }
        catch(ClassNotFoundException e)
        {            
        }

        return testClasses.toArray(new Class[testClasses.size()]);
    }
}
