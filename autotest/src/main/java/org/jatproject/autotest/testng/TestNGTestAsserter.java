package org.jatproject.autotest.testng;

import org.testng.annotations.Test;
import org.jatproject.autotest.TestAsserter;

import java.lang.reflect.Method;

public class TestNGTestAsserter implements TestAsserter
{
    public boolean isTest(Class<?> clazz)
    {
        return isAnnotationDeclaredAtTheClassLevel(clazz) || isAnnotationDeclaredAtTheMethodLevel(clazz);
    }

    private boolean isAnnotationDeclaredAtTheClassLevel(Class<?> clazz)
    {
        return clazz.getAnnotation(Test.class) != null;
    }

    private boolean isAnnotationDeclaredAtTheMethodLevel(Class<?> clazz)
    {
        for(Method method : clazz.getMethods())
        {
            if(method.getAnnotation(Test.class) != null) return true;
        }
        return false;
    }
}
