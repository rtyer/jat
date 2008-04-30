package org.jatproject.autotest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class AutoTestClassLoader extends ClassLoader
{
    private ClassPath reloadingClassPath;

    public AutoTestClassLoader(ClassPath reloadingClassPath)
    {
        this(ClassLoader.getSystemClassLoader(), reloadingClassPath);
    }

    public AutoTestClassLoader(ClassLoader classLoader, ClassPath reloadingClassPath)
    {
        super(classLoader);
        this.reloadingClassPath = reloadingClassPath;
    }

    @Override
    public Class loadClass(String s) throws ClassNotFoundException
    {
        return loadClass(s, reloadingClassPath.find(s.replace('.', '/') + ".class"));
    }

    public Class loadClass(String className, File file) throws ClassNotFoundException
    {
        if(file == null) return findSystemClass(className);
        
        try
        {
            byte[] contents = FileUtils.readFileToByteArray(file);
            return defineClass(className, contents, 0, contents.length);
        }
        catch (IOException e)
        {
            throw new ClassNotFoundException(className);
        }
    }
}
