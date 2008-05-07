package org.jatproject.autotest.repositories;

import org.jatproject.autotest.ClassFile;
import org.jatproject.autotest.ClassPath;
import org.jatproject.autotest.Classname;
import org.jatproject.autotest.ReferenceRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InMemoryReferenceRepository implements ReferenceRepository{
	private Map<String,Set<ClassFile>> dependencies;
	private Map<String,Set<ClassFile>> reverseDependencies;
	private ClassPath classpath;
	public InMemoryReferenceRepository(ClassPath path){
		this(path, 1);
	}
	public InMemoryReferenceRepository(ClassPath path, int levels){
		dependencies = new HashMap<String, Set<ClassFile>>();
		reverseDependencies = new HashMap<String, Set<ClassFile>>();
		this.classpath = path;
		for(ClassFile file : path){
			index(file);
		}
	}
	private void index(ClassFile file){
		clear(file);
		indexDependency(file,file);//add self-reference/dependency
		ClassDependencyExtractor extractor = new ClassDependencyExtractor(file.getContents());
		for(String dependency :  extractor.getDependencies()){
			Classname classname = new Classname(dependency);
			if(classpath.isOnPath(classname)){
				indexDependency(file, classpath.find(classname));
			}
		}
		
	}
	private void clear(ClassFile file){
		Set<ClassFile> oldDependencies = dependencies.get(file.getClassName());
		if(oldDependencies == null){
			return;
		}
		dependencies.remove(file.getClassName());
		for(ClassFile dependency : oldDependencies){
			Set<ClassFile> files = reverseDependencies.get(dependency.getClassName());
			if(files != null){
				files.remove(file);
			}
		}
	}
	private void indexDependency(ClassFile sourceClass, ClassFile dependency){
		Set<ClassFile> dependencySet = dependencies.get(sourceClass.getClassName());
		if(dependencySet == null){
			dependencySet = new HashSet<ClassFile>();
			dependencies.put(sourceClass.getClassName(), dependencySet);
		}
		dependencySet.add(dependency);
		
		Set<ClassFile> reverseDependencySet = reverseDependencies.get(dependency.getClassName());
		if(reverseDependencySet == null){
			reverseDependencySet = new HashSet<ClassFile>();
			reverseDependencies.put(dependency.getClassName(), reverseDependencySet);
		}
		reverseDependencySet.add(sourceClass);
	}
	public Set<ClassFile> findReferencesTo(ClassFile changeClass) {
		index(changeClass);
		return reverseDependencies.get(changeClass.getClassName());
	}
}
