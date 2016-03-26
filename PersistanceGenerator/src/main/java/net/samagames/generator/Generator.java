package net.samagames.generator;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

/**
 * Created by Silvanosky on 25/03/2016.
 */
public class Generator {

    public static void main(String[] args)
    {
        TypeSpec helloWorld = TypeSpec.interfaceBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC)
                .addField(FieldSpec.builder(String.class, "ONLY_THING_THAT_IS_CONSTANT")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$S", "change")
                        .build())
                .addMethod(MethodSpec.methodBuilder("beep")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .build();
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        try {
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
