package org.jatproject.autotest.repositories;

import org.jatproject.autotest.ClassFile;
import org.jatproject.autotest.ClassFiles;
import org.jatproject.autotest.ClassPath;
import org.jatproject.autotest.Classname;
import org.jatproject.autotest.ClassnameTest;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import static org.testng.AssertJUnit.assertSame;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class SimpleDependencyRepositoryTest
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

        SimpleDependencyRepository repository = new SimpleDependencyRepository(classpath);
        ClassFiles tests = repository.findDependenciesFor(clazz);

        assertSame(foundClazz, tests.get(0));
    }

    public void testClassShouldJustReturnTheClass() throws Exception
    {
        final ClassFile clazz = mockery.mock(ClassFile.class);
        final String className = ClassnameTest.class.getName();

        mockery.checking(new Expectations()
        {{
            one(clazz).getClassName();will(returnValue(className));
        }});

        SimpleDependencyRepository repository = new SimpleDependencyRepository(null);
        ClassFiles tests = repository.findDependenciesFor(clazz);

        assertSame(clazz, tests.get(0));
    }

    public void nullReturnFromClassPathShouldReturnEmptyClassFiles()
    {
        final ClassPath classpath = mockery.mock(ClassPath.class);
        final ClassFile clazz = mockery.mock(ClassFile.class);
        final String className = ClassFile.class.getName();

        mockery.checking(new Expectations()
        {{
            one(clazz).getClassName();will(returnValue(className));
            one(classpath).find(new Classname(className + "Test")); will(returnValue(null));
        }});

        SimpleDependencyRepository repository = new SimpleDependencyRepository(classpath);
        ClassFiles tests = repository.findDependenciesFor(clazz);

        assertTrue(tests.isEmpty());
    }
}
