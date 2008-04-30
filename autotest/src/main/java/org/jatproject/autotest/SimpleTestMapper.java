package org.jatproject.autotest;

import java.util.ArrayList;

public class SimpleTestMapper implements TestMapper
{
    private TestAsserter asserter;
    private ClassLoader loader;

    public SimpleTestMapper(TestAsserter asserter, ClassLoader loader)
    {
        this.asserter = asserter;
        this.loader = loader;
    }

    public Class[] findTestsFor(Class... changedClasses)
    {
        ArrayList<Class> testClasses = new ArrayList<Class>();

        try
        {
            for(Class currentClass : changedClasses)
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
