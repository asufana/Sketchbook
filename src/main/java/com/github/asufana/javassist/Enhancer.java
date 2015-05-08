package com.github.asufana.javassist;

import java.io.*;

import javassist.*;

public class Enhancer {
    
    public static void enhance(final String fqcnClassName) throws FileNotFoundException, IOException, RuntimeException, CannotCompileException {
        final File javaFile = getFile(fqcnClassName);
        final ClassPool classPool = ClassPool.getDefault();
        final CtClass ctClass = classPool.makeClass(new FileInputStream(javaFile));
        
        final CtMethod count = CtMethod.make("public static long count() { return play.db.jpa.JPQL.instance.count(); }",
                                             ctClass);
        ctClass.addMethod(count);
    }
    
    private static File getFile(final String fqcnClassName) {
        final String fileName = fqcnClassName.replaceAll("\\.", "/") + ".java";
        final File javaFile = new File(currentPath()
                + "/src/test/java/"
                + fileName);
        if (javaFile.exists() == false) {
            throw new RuntimeException("File is not exists.");
        }
        return javaFile;
    }
    
    private static String currentPath() {
        return System.getProperty("user.dir");
    }
}
