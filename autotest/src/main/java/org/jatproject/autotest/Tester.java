package org.jatproject.autotest;

public class Tester
{
    private TestEngine[] engines;

    public Tester(TestEngine... engines)
    {
        this.engines = engines;
    }
    
    public void runTests(ClassFiles classFiles)
    {
        for(ClassFile file : classFiles)
        {
            Class clazz = file.getClazz();
            for(TestEngine engine : engines)
            {
                if(engine.add(clazz)) break;
            }
        }

        for(TestEngine engine : engines) engine.run();
    }

    public void addListener(TestListener listener)
    {
        for(TestEngine engine : engines) engine.addListener(listener);
    }
}
