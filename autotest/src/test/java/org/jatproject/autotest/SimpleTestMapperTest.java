package org.jatproject.autotest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import static org.testng.AssertJUnit.assertSame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class SimpleTestMapperTest
{
    private Mockery mockery;

    @BeforeMethod
    protected void beforeEach()
    {
        mockery = new Mockery()
        {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
    }

    @AfterMethod
    protected void afterEach()
    {
        mockery.assertIsSatisfied();
    }

    public void nonTestClassShouldAppendTestToClassName() throws Exception
    {
        final ClassPath classpath = mockery.mock(ClassPath.class);
        final ClassFile clazz = mockery.mock(ClassFile.class);
        final ClassFile foundClazz = mockery.mock(ClassFile.class, "foundClazz");
        final String className = ClassFile.class.getName();

        mockery.checking(new Expectations()
        {{
            one(clazz).getClassName();will(returnValue(className));
            one(classpath).find(new Classname(className + "Test")); will(returnValue(foundClazz));
        }});

        SimpleTestMapper mapper = new SimpleTestMapper(classpath);
        ClassFiles tests = mapper.findTestsFor(clazz);

        assertSame(foundClazz, tests.get(0));
    }

    public void testClassShouldJustReturnTheClass() throws Exception
    {
        final ClassFile clazz = mockery.mock(ClassFile.class);
        final String className = ClassFileTest.class.getName();

        mockery.checking(new Expectations()
        {{
            one(clazz).getClassName();will(returnValue(className));
        }});

        SimpleTestMapper mapper = new SimpleTestMapper(null);
        ClassFiles tests = mapper.findTestsFor(clazz);

        assertSame(clazz, tests.get(0));
    }
}
