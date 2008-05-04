package org.jatproject.autotest;

import java.util.Set;

public class Tester
{
    private TestEngine[] engines;

    public Tester(TestEngine... engines)
    {
        this.engines = engines;
    }
    
    public void runTests(Set<ClassFile> classFiles)
    {
        for(ClassFile file : classFiles)
        {
            Class clazz = file.getClazz();
            for(TestEngine engine : engines)
            {
                if(engine.run(clazz)) break;
            }
        }
    }

    public void addListener(TestListener listener)
    {
        for(TestEngine engine : engines) engine.addListener(listener);
    }
}
