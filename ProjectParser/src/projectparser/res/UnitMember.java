/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.res;

import projectparser.acc.Bodys;
import projectparser.acc.Bodys.Constructor;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.type.VoidType;
import java.util.ArrayList;
import java.util.List;
import projectparser.acc.Expressions;
import projectparser.acc.Statements;
import projectparser.util.StringUtil;
import projectparser.acc.Types;

/**
 *
 * @author Lucas
 */
public class UnitMember {

    private TypeDeclaration typeDeclaration;
    private ClassOrInterfaceDeclaration classDeclaration;
    private AnnotationDeclaration annotationDeclaration;
    private ClassOrInterfaceDeclaration interfaceDeclaration;
    private EnumDeclaration enumDeclaration;
    private final Unit unit;

    private UnitMember(Unit unit) {
        this.unit = unit;
    }

    public static UnitMember create(Unit unit, String name) {
        UnitMember member = new UnitMember(unit);
        member.setTypeDeclaration(new ClassOrInterfaceDeclaration(ModifierSet.PUBLIC, true, name));
        unit.addToBody(member);
        unit.update();
        return member;
    }

    public static UnitMember load(TypeDeclaration type, Unit unit) {
        UnitMember member = new UnitMember(unit);
        member.setTypeDeclaration(type);
        return member;
    }

    public UnitMember(TypeDeclaration typeDeclaration, Unit unit) {
        this.unit = unit;
        setTypeDeclaration(typeDeclaration);
    }

    public void generateGetterAndSetter(Field field) {
        Method getter = new Method("get" + StringUtil.UppercaseFirstLetter(field.getName()), new Types().setType(field.getType()));
        getter.Body().add(new Statements()
                ._return(new projectparser.acc.Expressions()
                        .name(field.getName())
                )
        );

        add(getter);

        Method setter = new Method("set" + StringUtil.UppercaseFirstLetter(field.getName()), new Types().setType(new VoidType()));
        setter.Parameters().add(new Parameter(StringUtil.LowercaseFirstLetter(field.getName()), field.getType()));
        setter.Body().add(new Statements().expression(new Expressions()
                .assign()
                .operator(AssignExpr.Operator.assign)
                .target(new Expressions()
                        .qualifiedName()
                        .name(field.getName())
                        .scope("this")
                        .end()
                )
                .value(new Expressions()
                        .name(field.getName())
                )
                .end())
        );
        add(setter);
    }

    public void add(Bodys.Constructor constructor) {
        if (typeDeclaration.getMembers() == null) {
            typeDeclaration.setMembers(new ArrayList<>());
        }
        typeDeclaration.getMembers().add(constructor.getCd());
        if (unit != null) {
            unit.update();
        }
    }

    public void add(Annotation annotation) {
        if (typeDeclaration.getAnnotations() == null) {
            typeDeclaration.setAnnotations(new ArrayList<AnnotationExpr>());
        }
        typeDeclaration.getAnnotations().add(annotation.getAnnotationExpr());
        if (unit != null) {
            unit.update();
        }
    }

    public void add(Field field) {
        if (typeDeclaration.getMembers() == null) {
            typeDeclaration.setMembers(new ArrayList<BodyDeclaration>());
        }
        boolean has = false;
        int index = 0;
        for (BodyDeclaration body : typeDeclaration.getMembers()) {
            if (body instanceof FieldDeclaration) {
                FieldDeclaration m = (FieldDeclaration) body;
                if (m.getVariables().get(0).getId().getName().equals(field.getFieldDeclaration().getVariables().get(0).getId().getName())) {
                    has = true;
                    break;
                }
            }
            index++;
        }
        if (has) {
            typeDeclaration.getMembers().remove(index);
        }
        typeDeclaration.getMembers().add(field.getFieldDeclaration());
        if (unit != null) {
            unit.update();
        }
    }

    public void add(BodyDeclaration member) {
        if (typeDeclaration.getMembers() == null) {
            typeDeclaration.setMembers(new ArrayList<BodyDeclaration>());
        }
        typeDeclaration.getMembers().add(member);
        if (unit != null) {
            unit.update();
        }
    }

    public void add(Method method) {
        if (typeDeclaration.getMembers() == null) {
            typeDeclaration.setMembers(new ArrayList<BodyDeclaration>());
        }
        boolean hasSameParamsAndName = false;
        int index = 0;
        for (BodyDeclaration body : typeDeclaration.getMembers()) {
            if (body instanceof MethodDeclaration) {
                MethodDeclaration m = (MethodDeclaration) body;
                if (m.getName().equals(method.getMethodDeclaration().getName())) {
                    hasSameParamsAndName = true;
                    break;
                }
            }
            index++;
        }
        if (hasSameParamsAndName) {
            typeDeclaration.getMembers().remove(index);
        }
        typeDeclaration.getMembers().add(method.getMethodDeclaration());

        if (unit != null) {
            unit.update();
        }
    }

    public void addExtension(Types.ClassOrInterface extension) {
        if (!classDeclaration.isInterface() && classDeclaration.getExtends() == null) {
            classDeclaration.setExtends(new ArrayList<ClassOrInterfaceType>());
            classDeclaration.getExtends().add(extension.getClassOrInterface());
        } else if (classDeclaration.isInterface() && interfaceDeclaration.getExtends() == null) {
            interfaceDeclaration.setExtends(new ArrayList<ClassOrInterfaceType>());
            interfaceDeclaration.getExtends().add(extension.getClassOrInterface());
        }
        if (unit != null) {
            unit.update();
        }
    }

    public void addImplementation(Types.ClassOrInterface _interface) {
        if (!classDeclaration.isInterface() && classDeclaration.getImplements() == null) {
            classDeclaration.setImplements(new ArrayList<ClassOrInterfaceType>());
            classDeclaration.getImplements().add(_interface.getClassOrInterface());
        } else if (classDeclaration.isInterface() && interfaceDeclaration.getImplements() == null) {
            interfaceDeclaration.setImplements(new ArrayList<ClassOrInterfaceType>());
            interfaceDeclaration.getImplements().add(_interface.getClassOrInterface());
        }
        if (unit != null) {
            unit.update();
        }
    }

    public boolean remove(Field field) {
        boolean work = typeDeclaration.getMembers().remove(field.getFieldDeclaration());
        if (unit != null) {
            unit.update();
        }
        return work;
    }

    public boolean remove(Method method) {
        boolean work = typeDeclaration.getMembers().remove(method.getMethodDeclaration());
        if (unit != null) {
            unit.update();
        }
        return work;
    }

    public void remove(UnitMember member) {
        typeDeclaration.getMembers().remove(member.getTypeDeclaration());
        if (unit != null) {
            unit.update();
        }
    }

    public void remove(TypeDeclaration type) {
        typeDeclaration.getMembers().remove(type);
        if (unit != null) {
            unit.update();
        }
    }

    @Override
    public String toString() {
        return typeDeclaration.toString();
    }

    public TypeDeclaration getTypeDeclaration() {
        return typeDeclaration;
    }

    public void setTypeDeclaration(TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
        if (typeDeclaration instanceof ClassOrInterfaceDeclaration) {
            ClassOrInterfaceDeclaration o = new ClassOrInterfaceDeclaration();
            o = (ClassOrInterfaceDeclaration) typeDeclaration;
            setInterface(o.isInterface());
            if (o.isInterface()) {
                setClass(false);
                interfaceDeclaration = o;
            } else {
                classDeclaration = o;
            }
        } else if (typeDeclaration instanceof EnumDeclaration) {
            enumDeclaration = (EnumDeclaration) typeDeclaration;
        } else if (typeDeclaration instanceof AnnotationDeclaration) {
            annotationDeclaration = (AnnotationDeclaration) typeDeclaration;
        } else {

        }
    }

    public String getName() {
        return typeDeclaration.getName();
    }

    public void setName(String name) {
        typeDeclaration.setName(name);
    }

    public int getModifier() {
        return typeDeclaration.getModifiers();
    }

    public void setModifier(int modifier) {
        typeDeclaration.setModifiers(modifier);
    }

    public ClassOrInterfaceDeclaration getClassDeclaration() {
        return classDeclaration;
    }

    public void setClassDeclaration(ClassOrInterfaceDeclaration classDeclaration) {
        this.classDeclaration = classDeclaration;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setEnum(boolean Enum) {
        enumDeclaration = new EnumDeclaration(typeDeclaration.getModifiers(), typeDeclaration.getName());
        enumDeclaration.setMembers(typeDeclaration.getMembers());
        typeDeclaration = enumDeclaration;
    }

    public void setInterface(boolean Interface) {
        interfaceDeclaration = new ClassOrInterfaceDeclaration(typeDeclaration.getModifiers(), true, typeDeclaration.getName());
        interfaceDeclaration.setMembers(typeDeclaration.getMembers());
        typeDeclaration = interfaceDeclaration;
    }

    public void setAnnotation(boolean Annotation) {
        annotationDeclaration = new AnnotationDeclaration(typeDeclaration.getModifiers(), typeDeclaration.getName());
        annotationDeclaration.setMembers(typeDeclaration.getMembers());
        typeDeclaration = annotationDeclaration;
    }

    public void setClass(boolean Class) {
        classDeclaration = new ClassOrInterfaceDeclaration(typeDeclaration.getModifiers(), false, typeDeclaration.getName());
        classDeclaration.setMembers(typeDeclaration.getMembers());
        typeDeclaration = classDeclaration;
    }

    public List<ClassOrInterfaceType> getExtensions() {
        List<ClassOrInterfaceType> extensions = new ArrayList<>();
        if (classDeclaration != null) {
            if (classDeclaration.isInterface()) {
                extensions = interfaceDeclaration.getExtends();
            } else {
                extensions = classDeclaration.getExtends();
            }
        }
        return extensions;
    }

    public List<ClassOrInterfaceType> getImplementations() {
        List<ClassOrInterfaceType> implementations = new ArrayList<>();
        if (classDeclaration != null) {
            if (!classDeclaration.isInterface()) {
                implementations = classDeclaration.getImplements();
            } else {
                implementations = interfaceDeclaration.getImplements();
            }
        }
        return implementations;
    }

    public List<BodyDeclaration> getMembers() {
        return typeDeclaration.getMembers();
    }

    public List<Annotation> getAnnotations() {
        List<Annotation> annotations = new ArrayList<>();
        if (typeDeclaration.getAnnotations() != null) {
            for (AnnotationExpr a : typeDeclaration.getAnnotations()) {
                Annotation annotation = new Annotation(a.getName().toString());
                annotations.add(annotation);
            }
        }
        return annotations;
    }

    public List<Field> getFields() {
        List<Field> fields = new ArrayList<>();
        if (typeDeclaration.getMembers() != null) {
            for (BodyDeclaration b : typeDeclaration.getMembers()) {
                if (b instanceof FieldDeclaration) {
                    fields.add(new Field((FieldDeclaration) b));
                }
            }
        }
        return fields;
    }

    public List<Method> getMethods() {
        List<Method> methods = new ArrayList<>();
        for (BodyDeclaration b : getMembers()) {
            if (b instanceof MethodDeclaration) {
                methods.add(new Method((MethodDeclaration) b));
            }
        }
        return methods;
    }

    public void setAnnotationDeclaration(AnnotationDeclaration annotationDeclaration) {
        this.annotationDeclaration = annotationDeclaration;
    }

    public ClassOrInterfaceDeclaration getInterfaceDeclaration() {
        return interfaceDeclaration;
    }

    public void setInterfaceDeclaration(ClassOrInterfaceDeclaration interfaceDeclaration) {
        this.interfaceDeclaration = interfaceDeclaration;
    }

    public EnumDeclaration getEnumDeclaration() {
        return enumDeclaration;
    }

    public void setEnumDeclaration(EnumDeclaration EnumDeclaration) {
        this.enumDeclaration = EnumDeclaration;
    }

}
