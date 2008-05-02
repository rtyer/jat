package org.jatproject.autotest;

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
        Classname name = new Classname(classname);

        if(classpath.isOnPath(name))
        {
            return loadClass(classname, classpath.find(name));
        }
        return getParent().loadClass(classname);
    }

    public Class loadClass(String className, ClassFile file) throws ClassNotFoundException
    {
        if(classCache.containsKey(className)) return classCache.get(className);

        byte[] contents = file.getContents();
        Class clazz = defineClass(className, contents, 0, contents.length);
        classCache.put(className, clazz);

        return clazz;
    }

    public Class defineClass(String classname, byte[] contents)
    {
        return defineClass(classname, contents, 0, contents.length);
    }
}
