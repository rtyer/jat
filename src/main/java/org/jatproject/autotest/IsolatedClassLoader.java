package org.jatproject.autotest;

import java.net.URL;
import java.net.URLClassLoader;

public class IsolatedClassLoader extends URLClassLoader
{
    public IsolatedClassLoader(URL[] urls)
    {
        super(urls);
    }

    @Override
    public synchronized Class<?> loadClass(String name) throws ClassNotFoundException
    {
        Class<?> clazz = findLoadedClass(name);
        try
        {
            if (clazz == null) clazz = findClass(name);
        }
        catch (ClassNotFoundException e)
        {
            clazz = super.loadClass(name);
        }
        return clazz;
    }

}
