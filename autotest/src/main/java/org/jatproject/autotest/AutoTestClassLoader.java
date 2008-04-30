package org.jatproject.autotest;

import java.io.IOException;

public class AutoTestClassLoader extends ClassLoader
{
    private ClassPath classpath;

    public AutoTestClassLoader(ClassPath classpath)
    {
        this(ClassLoader.getSystemClassLoader(), classpath);
    }

    public AutoTestClassLoader(ClassLoader classLoader, ClassPath classpath)
    {
        super(classLoader);
        this.classpath = classpath;
    }

    @Override
    public Class loadClass(String classname) throws ClassNotFoundException
    {
        return loadClass(classname, classpath.find(classname));
    }

    public Class loadClass(String className, ClassFile file) throws ClassNotFoundException
    {
        if(file == null) return findSystemClass(className);
        
        try
        {
            byte[] contents = file.getContents();
            return defineClass(className, contents, 0, contents.length);
        }
        catch (IOException e)
        {
            throw new ClassNotFoundException(className);
        }
    }
}
