/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.ger;

import static projectparser.ger.TypeConstructor.isClassOrInterface;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;
import java.util.ArrayList;
import java.util.List;
import projectparser.res.Field;
import projectparser.res.Import;
import projectparser.res.Unit;

/**
 *
 * @author Lucas
 */
public class ListConstructor {

    private Unit unit;

    public ListConstructor() {
    }

    public ListConstructor(Unit unit) {
        this.unit = unit;
    }

    public static boolean isList(Field field) {
        return isList(field.getTypeName());
    }

    public static boolean isList(String name) {
        return name.contains("List<");
    }

    public static boolean isList(Type type) {
        if (isClassOrInterface(type)) {
            ClassOrInterfaceType check = (ClassOrInterfaceType) type;
            return isList(check.getName());
        } else {
            return false;
        }
    }

    public static boolean hasArgument(Field field) {
        if (TypeConstructor.isClassOrInterface(field.getType())) {
            ClassOrInterfaceType type = (ClassOrInterfaceType) field.getType();
            if (type.getTypeArgs() != null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Type generateListType(String argument) {
        boolean has = false;
        for (Import _import : unit.getImports()) {
            if (_import.getName().equals("java.util.List")) {
                has = true;
            }
        }
        if (!has) {
            unit.add(("java.util.List"));
        }
        ClassOrInterfaceType List = new ClassOrInterfaceType("List");
        List<Type> args = new ArrayList<>();
        args.add(new ClassOrInterfaceType(argument));
        List.setTypeArgs(args);
        return List;
    }

    public static Type createListType(String argument) {
        ClassOrInterfaceType List = new ClassOrInterfaceType("List");
        List<Type> args = new ArrayList<>();
        args.add(new ClassOrInterfaceType(argument));
        List.setTypeArgs(args);
        return List;
    }

    public Field generateField(String type, String argument) {
        unit.add(("java.util.List"));
        ClassOrInterfaceType List = new ClassOrInterfaceType("List");
        List<Type> args = new ArrayList<>();
        args.add(new ClassOrInterfaceType(type));
        List.setTypeArgs(args);
        Field field = new Field(argument, List);
        return field;
    }

    public static Field createField(String type, String argument) {
        ClassOrInterfaceType List = new ClassOrInterfaceType("List");
        List<Type> args = new ArrayList<>();
        args.add(new ClassOrInterfaceType(type));
        List.setTypeArgs(args);
        Field field = new Field(argument, List);
        return field;
    }
}
