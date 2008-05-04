package org.jatproject.autotest;

import java.util.Set;

public interface DependencyRepository
{
    Set<ClassFile> findDependenciesFor(ClassFile changeClass);
}
