package org.jatproject.autotest;

import java.io.IOException;
import java.util.HashMap;

public class AutoTestClassLoader extends ClassLoader
{
    private ClassPath classpath;
    private HashMap<String, Class> classCache = new HashMap<String, Class>();

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
        if(classCache.containsKey(className)) return classCache.get(className);
        
        try
        {
            byte[] contents = file.getContents();
            Class clazz = defineClass(className, contents, 0, contents.length);
            classCache.put(className, clazz);
            
            return clazz;
        }
        catch (IOException e)
        {
            throw new ClassNotFoundException(className);
        }
    }    
}
