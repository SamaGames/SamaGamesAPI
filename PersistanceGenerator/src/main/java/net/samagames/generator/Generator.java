package net.samagames.generator;

import com.squareup.javapoet.*;
import net.samagames.persistanceapi.beans.statistics.PlayerStatisticsBean;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Silvanosky on 25/03/2016.
 */
public class Generator {

    private static List<JavaFile> toBuild = new ArrayList<>();

    public static void main(String[] args)
    {
        loadGameStats();

        build();
    }

    public static void loadGameStats()
    {
        Field[] playerStatisticFields = PlayerStatisticsBean.class.getDeclaredFields();
        TypeSpec.Builder playerStatsBuilder = TypeSpec.interfaceBuilder("IPlayerStats")
                .addModifiers(Modifier.PUBLIC);

        playerStatsBuilder.addMethod(getMethod("updateStats", void.class));
        playerStatsBuilder.addMethod(getMethod("refreshStats", boolean.class));
        playerStatsBuilder.addMethod(getMethod("getPlayerUUID", UUID.class));

        for (Field field : playerStatisticFields)
        {
            field.setAccessible(true);
            Class workingField = field.getType();
            String wname = workingField.getSimpleName().replaceAll("Bean", "");
            String name = "I" + wname;

            TypeSpec.Builder object = TypeSpec.interfaceBuilder(name)
                    .addModifiers(Modifier.PUBLIC);
            object.addMethod(getMethod("update", void.class));
            object.addMethod(getMethod("refresh", void.class));
            Method[] subDeclaredMethods = workingField.getDeclaredMethods();
            for (Method method : subDeclaredMethods)
            {
                MethodSpec.Builder builder = MethodSpec.methodBuilder(method.getName());
                if (method.getParameterCount() > 0)
                {
                    for (Parameter parameter : method.getParameters())
                    {
                        builder.addParameter(parameter.getType(), parameter.getName());
                    }
                }
                builder.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
                builder.returns(method.getReturnType());
                object.addMethod(builder.build());
            }
            TypeSpec build = object.build();
            String pack = "net.samagames.api.stats.games";
            ClassName className = ClassName.get(pack, name);

            //Create getter in player stat
            playerStatsBuilder.addMethod(getMethod("get" + wname, className));

            toBuild.add(JavaFile.builder("net.samagames.api.stats.games", build).build());
        }
        toBuild.add(JavaFile.builder("net.samagames.api.stats", playerStatsBuilder.build()).build());
    }

    public static void build()
    {
        try {
            File file = new File("./Generation");
            file.delete();
            for (JavaFile javaFile : toBuild)
            {
                javaFile.writeTo(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MethodSpec getMethod(String name, TypeName retur)
    {
        MethodSpec.Builder getter = MethodSpec.methodBuilder(name);
        getter.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
        getter.returns(retur);
        return getter.build();
    }

    public static MethodSpec getMethod(String name, Type retur)
    {
        return getMethod(name, TypeName.get(retur));
    }
}
