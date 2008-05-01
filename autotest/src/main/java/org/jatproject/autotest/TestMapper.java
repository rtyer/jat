package org.jatproject.autotest;

public interface TestMapper
{
    Class[] findTestsFor(ClassFile changeClass);
}
