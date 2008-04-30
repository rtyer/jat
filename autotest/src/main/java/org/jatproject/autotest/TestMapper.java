package org.jatproject.autotest;

public interface TestMapper
{
    Class[] findTestsFor(ClassFiles changeClasses);
}
