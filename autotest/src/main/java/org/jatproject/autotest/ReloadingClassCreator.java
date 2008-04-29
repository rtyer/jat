package org.jatproject.autotest;

import java.io.File;

public class ReloadingClassCreator implements ClassCreator
{
    private SearchPath path;
    private AutoTestClassLoader loader;

    public ReloadingClassCreator(SearchPath path, AutoTestClassLoader loader)
    {
        this.path = path;
        this.loader = loader;
    }

    public Class loadClass(String className) throws ClassNotFoundException
    {
        File file = path.find(className.replace('.', '/') + ".class");
        if (file == null)
        {
            return loader.loadClass(className);
        }
        else
        {
            return loader.defineClass(className, file);
        }
    }
}
