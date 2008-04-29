package org.jatproject.autotest;

public interface ClassCreator
{
    Class loadClass(String className) throws ClassNotFoundException;
}
