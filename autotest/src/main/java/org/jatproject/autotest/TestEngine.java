package org.jatproject.autotest;

public interface TestEngine
{
    void addListener(TestListener listener);    
    boolean run(Class<?> clazz);
}
