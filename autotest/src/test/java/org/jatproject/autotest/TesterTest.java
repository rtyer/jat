package org.jatproject.autotest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Test
public class TesterTest
{
    private Mockery mockery;

    @BeforeMethod
    public void beforeEach()
    {
        mockery = new Mockery()
        {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
    }

    @AfterMethod
    public void afterEach()
    {
        mockery.assertIsSatisfied();
    }

    public void shouldNotAskNextIfFirstEngineRunsClass()
    {
        final ClassFile clazz = mockery.mock(ClassFile.class);
        final TestEngine accepting = mockery.mock(TestEngine.class, "accepting");
        final TestEngine rejecting = mockery.mock(TestEngine.class, "rejecting");

        mockery.checking(new Expectations()
        {{
            one(clazz).getClazz(); will(returnValue(ClassFile.class));
            one(accepting).run(ClassFile.class); will(returnValue(true));
        }});

        new Tester(accepting, rejecting).runTests(Collections.singleton(clazz));
    }

    public void shouldAskNextEngineIfFirstDoesNotRunClass()
    {
        final ClassFile clazz = mockery.mock(ClassFile.class);
        final TestEngine accepting = mockery.mock(TestEngine.class, "accepting");
        final TestEngine rejecting = mockery.mock(TestEngine.class, "rejecting");

        mockery.checking(new Expectations()
        {{
            one(clazz).getClazz(); will(returnValue(ClassFile.class));
            one(rejecting).run(ClassFile.class); will(returnValue(false));
            one(accepting).run(ClassFile.class); will(returnValue(true));
        }});

        new Tester(rejecting, accepting).runTests(Collections.singleton(clazz));
    }

    public void shouldContinueToTheNextClazzIfNoEngineAcceptsAClazz()
    {
        final ClassFile nonTest = mockery.mock(ClassFile.class, "nonTest");
        final ClassFile test = mockery.mock(ClassFile.class, "test");
        final TestEngine accepting = mockery.mock(TestEngine.class, "accepting");
        final TestEngine rejecting = mockery.mock(TestEngine.class, "rejecting");

        mockery.checking(new Expectations()
        {{
            one(nonTest).getClazz(); will(returnValue(ClassFile.class));
            one(rejecting).run(ClassFile.class); will(returnValue(false));
            one(accepting).run(ClassFile.class); will(returnValue(false));
            one(test).getClazz(); will(returnValue(ClassnameTest.class));
            one(rejecting).run(ClassnameTest.class); will(returnValue(false));
            one(accepting).run(ClassnameTest.class); will(returnValue(true));
        }});

        new Tester(rejecting, accepting).runTests(new HashSet<ClassFile>(Arrays.asList(nonTest, test)));
    }

    public void shouldPassTestListenersToAllTestEngines()
    {
        final TestEngine accepting = mockery.mock(TestEngine.class, "accepting");
        final TestEngine rejecting = mockery.mock(TestEngine.class, "rejecting");
        final TestListener listener = mockery.mock(TestListener.class);

        mockery.checking(new Expectations()
        {{
            one(accepting).addListener(listener);
            one(rejecting).addListener(listener);
        }});

        new Tester(rejecting, accepting).addListener(listener);
    }
}
