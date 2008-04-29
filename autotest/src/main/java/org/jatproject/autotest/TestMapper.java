package org.jatproject.autotest;

public interface TestMapper
{
    Class[] findTestsFor(Class... changeClasses);
}
