package org.jatproject.autotest;

import org.apache.commons.io.FileUtils;
import org.jatproject.autotest.repositories.ClassDependencyExtractor;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ClassFile
{
    private ClassPath path;
    private Classname classname;
    private File classFile;

    public ClassFile(ClassPath path, Classname classname, File classFile)
    {
        this.path = path;
        this.classname = classname;
        this.classFile = classFile;
    }

    public String getClassName()
    {
        return classname.getFullyQulifiedClassName();
    }

    public Class getClazz()
    {
        try
        {
            return Class.forName(getClassName(), true, ClassFile.class.getClassLoader());
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public byte[] getContents()
    {
        try
        {
            return FileUtils.readFileToByteArray(classFile);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public int hashCode()
    {
        return getClassName().hashCode();
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        ClassFile classfile = (ClassFile) o;
        return this.getClassName().equals(classfile.getClassName());
    }

    public Set<ClassFile> getDependencies()
    {
        Set<ClassFile> dependencies = new HashSet<ClassFile>();
        for(String classname : new ClassDependencyExtractor(getContents()).getDependencies())
        {
            Classname name = new Classname(classname);
            if(path.isOnPath(name)) dependencies.add(path.find(name));
        }

        return dependencies;
    }
}
