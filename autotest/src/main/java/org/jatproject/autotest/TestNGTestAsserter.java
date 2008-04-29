package org.jatproject.autotest;

import java.lang.reflect.Method;
import org.testng.annotations.Test;

public class TestNGTestAsserter implements TestAsserter
{
    public boolean isTest(Class clazz)
    {
        return isAnnotationDeclaredAtTheClassLevel(clazz) || isAnnotationDeclaredAtTheMethodLevel(clazz);
    }

    private boolean isAnnotationDeclaredAtTheClassLevel(Class clazz)
    {
        return clazz.getAnnotation(Test.class) != null;
    }

    private boolean isAnnotationDeclaredAtTheMethodLevel(Class clazz)
    {
        for(Method method : clazz.getMethods())
        {
            if(method.getAnnotation(Test.class) != null) return true;
        }
        return false;
    }
}
