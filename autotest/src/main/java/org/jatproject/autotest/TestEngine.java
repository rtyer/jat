package org.jatproject.autotest;

public interface TestEngine
{
    void addListener(TestListener listener);    
    boolean add(Class<?> clazz);
    void run();
}
