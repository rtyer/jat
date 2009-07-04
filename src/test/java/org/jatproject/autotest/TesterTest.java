package org.jatproject.autotest;

import org.testng.annotations.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Test
public class TesterTest
{
    public void shouldNotAskNextIfFirstEngineRunsClass()
    {
        ClassFile clazz = mock(ClassFile.class);
        TestEngine accepting = mock(TestEngine.class);
        TestEngine rejecting = mock(TestEngine.class);

        when(clazz.getClazz()).thenReturn(ClassFile.class);
        when(accepting.run(ClassFile.class)).thenReturn(true);
        
        new Tester(accepting, rejecting).runTests(Collections.singleton(clazz));
    }

    public void shouldAskNextEngineIfFirstDoesNotRunClass()
    {
        ClassFile clazz = mock(ClassFile.class);
        TestEngine accepting = mock(TestEngine.class);
        TestEngine rejecting = mock(TestEngine.class);

        when(clazz.getClazz()).thenReturn(ClassFile.class);
        when(rejecting.run(ClassFile.class)).thenReturn(false);
        when(accepting.run(ClassFile.class)).thenReturn(true);

        new Tester(rejecting, accepting).runTests(Collections.singleton(clazz));
    }

    public void shouldContinueToTheNextClazzIfNoEngineAcceptsAClazz()
    {
        ClassFile nonTest = mock(ClassFile.class, "nonTest");
        ClassFile test = mock(ClassFile.class, "test");
        TestEngine accepting = mock(TestEngine.class, "accepting");
        TestEngine rejecting = mock(TestEngine.class, "rejecting");

        when(nonTest.getClazz()).thenReturn(ClassFile.class);
        when(rejecting.run(ClassFile.class)).thenReturn(false);
        when(accepting.run(ClassFile.class)).thenReturn(false);
        when(test.getClazz()).thenReturn(ClassnameTest.class);
        when(rejecting.run(ClassnameTest.class)).thenReturn(false);
        when(accepting.run(ClassnameTest.class)).thenReturn(true);

        new Tester(rejecting, accepting).runTests(new HashSet<ClassFile>(Arrays.asList(nonTest, test)));
    }

    public void shouldPassTestListenersToAllTestEngines()
    {
        TestEngine accepting = mock(TestEngine.class);
        TestEngine rejecting = mock(TestEngine.class);
        TestListener listener = mock(TestListener.class);

        new Tester(rejecting, accepting).addListener(listener);

        verify(accepting).addListener(listener);
        verify(rejecting).addListener(listener);
    }
}
