package org.jatproject.autotest;

import java.lang.reflect.Method;
import org.testng.annotations.Test;

public class TestNGTestAsserter implements TestAsserter
{
    public boolean isTest(Class clazz)
    {
        Class<Test> annotationClass = Test.class;

        if(clazz.getAnnotation(annotationClass) != null) return true;

        for(Method method : clazz.getMethods())
        {
            if(method.getAnnotation(annotationClass) != null) return true;
        }

        return false;
    }
}
