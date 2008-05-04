package org.jatproject.autotest;

public interface DependencyRepository
{
    ClassFiles findDependenciesFor(ClassFile changeClass);
}
