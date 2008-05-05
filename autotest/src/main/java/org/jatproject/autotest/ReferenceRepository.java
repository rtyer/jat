package org.jatproject.autotest;

import java.util.Set;

public interface ReferenceRepository
{
    Set<ClassFile> findReferencesTo(ClassFile changeClass);
}
