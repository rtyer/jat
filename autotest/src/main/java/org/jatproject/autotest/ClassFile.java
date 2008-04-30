package org.jatproject.autotest;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

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

    public byte[] getContents() throws IOException
    {
        return FileUtils.readFileToByteArray(classFile);
    }
}
