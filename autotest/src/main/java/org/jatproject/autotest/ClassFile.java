package org.jatproject.autotest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClassFile
{
    private File baseDirectory;
    private File classFile;

    public ClassFile(File baseDirectory, File classFile)
    {
        this.baseDirectory = baseDirectory;
        this.classFile = classFile;
    }

    public String getClassName()
    {
        String classFilePath = classFile.getAbsolutePath();
        String baseDirectoryPath = baseDirectory.getAbsolutePath();

        classFilePath = classFilePath.replace(baseDirectoryPath, "");
        classFilePath = classFilePath.replace(".class", "");
        classFilePath = classFilePath.replaceAll("/|\\\\", ".");
        classFilePath = classFilePath.replaceFirst("^\\.", "");

        return classFilePath;
    }

    public void appendClass(List<Class> classes, AutoTestClassLoader loader)
    {
        classes.add(loader.defineClass(getClassName(), getContents()));
    }

    public byte[] getContents()
    {
        try
        {
            return FileUtils.readFileToByteArray(classFile);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
