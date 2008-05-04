package org.jatproject.autotest.repositories;

import org.jatproject.autotest.ClassFile;
import org.jatproject.autotest.ClassPath;
import org.jatproject.autotest.Classname;
import org.jatproject.autotest.DependencyRepository;

import java.util.Collections;
import java.util.Set;

public class SimpleDependencyRepository implements DependencyRepository
{
    private static final Set<ClassFile> EMPTY_SET = Collections.emptySet();
    private ClassPath classpath;

    public SimpleDependencyRepository(ClassPath classpath)
    {
        this.classpath = classpath;
    }

    public Set<ClassFile> findDependenciesFor(ClassFile changedClass)
    {
        String className = changedClass.getClassName();

        if(className.endsWith("Test")) return Collections.singleton(changedClass);

        ClassFile file = classpath.find(new Classname(className + "Test"));
        return file == null ? EMPTY_SET : Collections.singleton(file);
    }
}
