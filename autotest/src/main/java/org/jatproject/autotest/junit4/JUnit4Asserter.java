package org.jatproject.autotest.junit4;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

public class JUnit4Asserter {

	public boolean isTest(Class<?> clazz) {
		return doesTestInheritFromTestCase(clazz)
				|| isJUnit4AnnotationPresentOnAnyMethod(clazz);
	}

	private boolean isJUnit4AnnotationPresentOnAnyMethod(Class<?> clazz) {
		for (Method method : clazz.getMethods()) {
			if (method.getAnnotation(Test.class) != null)
				return true;
		}
		return false;
	}

	private boolean doesTestInheritFromTestCase(Class<?> clazz) {
		List<Class<?>> parents = getParentHierarchyMembers(clazz);
		for (Class<?> parent : parents) {
			if (parent.equals(TestCase.class)) {
				return true;
			}
		}
		return false;
	}

	private List<Class<?>> getParentHierarchyMembers(Class<?> clazz) {
		List<Class<?>> members = new ArrayList<Class<?>>();
		Class<?> parent = clazz.getSuperclass();
		while (parent != null) {
			members.add(parent);
			parent = parent.getSuperclass();
		}
		return members;
	}

}
