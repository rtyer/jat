package org.jatproject.autotest;

public interface TestMapper
{
    ClassFiles findTestsFor(ClassFile changeClass);
}
