package org.jatproject.autotest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import static org.testng.AssertJUnit.assertEquals;
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
        final AutoTestClassLoader loader = mockery.mock(AutoTestClassLoader.class);
        final ClassFile clazz = mockery.mock(ClassFile.class);
        final String className = ClassFile.class.getName();

        mockery.checking(new Expectations()
        {{
            one(clazz).getClassName();will(returnValue(className));
            one(loader).loadClass(className, clazz);will(returnValue(ClassFile.class));
            one(loader).loadClass(className + "Test"); will(returnValue(ClassFileTest.class));
        }});

        SimpleTestMapper mapper = new SimpleTestMapper(loader);
        Class[] tests = mapper.findTestsFor(clazz);

        assertSame(ClassFileTest.class, tests[0]);
    }

    public void testClassShouldJustReturnTheClass() throws Exception
    {
        final AutoTestClassLoader loader = mockery.mock(AutoTestClassLoader.class);
        final ClassFile clazz = mockery.mock(ClassFile.class);
        final String className = ClassFileTest.class.getName();

        mockery.checking(new Expectations()
        {{
            one(clazz).getClassName();will(returnValue(className));
            one(loader).loadClass(className, clazz);will(returnValue(ClassFileTest.class));
        }});

        SimpleTestMapper mapper = new SimpleTestMapper(loader);
        Class[] tests = mapper.findTestsFor(clazz);

        assertSame(ClassFileTest.class, tests[0]);
    }

    public void unknownTestClassesShouldBeIgnored() throws Exception
    {
        final AutoTestClassLoader loader = mockery.mock(AutoTestClassLoader.class);
        final ClassFile clazz = mockery.mock(ClassFile.class);
        final String className = Tester.class.getName();
        final String testName = className + "Test";

        mockery.checking(new Expectations()
        {{
            one(clazz).getClassName();will(returnValue(className));
            one(loader).loadClass(className, clazz);will(returnValue(Tester.class));

            one(loader).loadClass(testName); will(throwException(new ClassNotFoundException(testName)));
        }});

        SimpleTestMapper mapper = new SimpleTestMapper(loader);
        Class[] tests = mapper.findTestsFor(clazz);

        assertEquals(0, tests.length);
    }
}
