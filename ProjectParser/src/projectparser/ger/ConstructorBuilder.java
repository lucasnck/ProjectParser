/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.ger;

import projectparser.acc.Bodys;
import projectparser.acc.Bodys.Constructor;
import projectparser.acc.Expressions;
import projectparser.acc.Statements;
import japa.parser.ast.type.ClassOrInterfaceType;
import java.util.List;
import projectparser.res.Field;
import projectparser.res.Parameter;
import projectparser.util.StringUtil;

/**
 *
 * @author lucas
 */
public class ConstructorBuilder {

    public static Bodys.Constructor _default(String className) {
        Bodys.Constructor constructor = new Bodys()
                .Constructor()
                .name(StringUtil.UppercaseFirstLetter(className));
        constructor.block(new Statements().Block());
        constructor.end();
        return constructor;
    }

    public static Bodys.Constructor withFieldsAssignments(String className, List<Field> fields) {

        Bodys.Constructor constructor = new Bodys()
                .Constructor()
                .name(StringUtil.UppercaseFirstLetter(className));
        Statements.Block block = new Statements().Block();
        for (Field field : fields) {
            constructor.addParameter(new Parameter(field.getName(), field.getType()));
            if (ListConstructor.isList(field)) {
                ClassOrInterfaceType fieldType = (ClassOrInterfaceType) field.getType();
                ClassOrInterfaceType argument = (ClassOrInterfaceType) fieldType.getTypeArgs().get(0);
                block.addStatement(new Statements()
                        .expression(new Expressions()
                                .integerLiteral("this." + field.getName() + " = " + field.getName())
                        ));
            } else {
                block.addStatement(new Statements()
                        .expression(new Expressions()
                                .integerLiteral("this." + field.getName() + " = " + field.getName())
                        ));
            }
        }
        constructor.block(block);
        constructor.end();
        return constructor;
    }

    public static Constructor constructorCreateInstancesOfFields(String className, List<Field> fields) {

        Constructor constructor = new Bodys()
                .Constructor()
                .name(StringUtil.UppercaseFirstLetter(className));
        Statements.Block block = new Statements().Block();
        for (Field field : fields) {
            if (ListConstructor.isList(field)) {
                ClassOrInterfaceType fieldType = (ClassOrInterfaceType) field.getType();
                ClassOrInterfaceType argument = (ClassOrInterfaceType) fieldType.getTypeArgs().get(0);
                block.addStatement(new Statements()
                        .expression(new Expressions()
                                .integerLiteral("this." + field.getName() + " = new ArrayList<" + argument.getName() + ">()")
                        ));
            } else {
                block.addStatement(new Statements()
                        .expression(new Expressions()
                                .integerLiteral("this." + field.getName() + " = new " + field.getTypeName() + "()")
                        ));
            }
        }
        constructor.block(block);
        constructor.end();
        return constructor;
    }

}
