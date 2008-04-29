package org.jatproject.autotest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class AutoTestClassLoader extends ClassLoader
{
    public Class defineClass(String className, File file) throws ClassNotFoundException
    {
        try
        {
            FileChannel channel = new FileInputStream(file).getChannel();

            try
            {
                int size = (int) channel.size();

                ByteBuffer buffer = ByteBuffer.allocate(size);
                channel.read(buffer);

                return defineClass(className, buffer.array(), 0, size);
            }
            finally
            {
                channel.close();
            }
        }
        catch (IOException e)
        {
            throw new ClassNotFoundException(className);
        }
    }
}
