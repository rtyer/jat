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
            return loadClass(classpath.find(name));
        }

        return getParent().loadClass(classname);
    }

    public Class loadClass(ClassFile file)
    {
        String classname = file.getClassName();

        if(classCache.containsKey(classname)) return classCache.get(classname);

        byte[] contents = file.getContents();
        Class clazz = defineClass(classname, contents, 0, contents.length);
        classCache.put(classname, clazz);
        
        return clazz;
    }
}
