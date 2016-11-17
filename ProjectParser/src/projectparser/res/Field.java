/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.res;

import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;
import java.util.ArrayList;
import java.util.List;
import projectparser.acc.Types;
import japa.parser.ast.type.PrimitiveType;

/**
 *
 * @author Lucas
 */
public class Field {

    private FieldDeclaration fieldDeclaration;
    private String name;
    private int modifier = ModifierSet.PRIVATE;
    private Type type;
    private List<Annotation> annotations;

    public Field(String name, Types type) {
        fieldDeclaration = new FieldDeclaration();
        setType(type);
        setModifier(modifier);
        setName(name);
    }

    public Field(String name, Type type) {
        fieldDeclaration = new FieldDeclaration();
        setType(type);
        setModifier(modifier);
        setName(name);
    }

    public Field(FieldDeclaration fieldDeclaration) {
        setFieldDeclaration(fieldDeclaration);
    }

    public Field add(Annotation annotation) {
        if (annotations == null) {
            annotations = new ArrayList<>();
        }
        annotations.add(annotation);
        if (fieldDeclaration.getAnnotations() == null) {
            fieldDeclaration.setAnnotations(new ArrayList<>());
        }
        fieldDeclaration.getAnnotations().add(annotation.getAnnotationExpr());
        return this;
    }

    public class Annotations {

        public Annotations add(Annotation annotation) {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            annotations.add(annotation);
            if (fieldDeclaration.getAnnotations() == null) {
                fieldDeclaration.setAnnotations(new ArrayList<>());
            }
            fieldDeclaration.getAnnotations().add(annotation.getAnnotationExpr());
            return this;
        }

        public Annotations remove(String name) {
            List<AnnotationExpr> annotationDeclarations = fieldDeclaration.getAnnotations();
            List<AnnotationExpr> newlist = new ArrayList<>();
            for (AnnotationExpr a : annotationDeclarations) {
                if (a.getName().toString().startsWith(name)) {
                    a = null;
                } else {
                    newlist.add(a);
                }
            }
            fieldDeclaration.setAnnotations(newlist);
            return this;
        }

        public List<Annotation> list() {
            if (fieldDeclaration.getAnnotations() != null) {
                List<Annotation> annotations = new ArrayList<>();
                for (AnnotationExpr a : fieldDeclaration.getAnnotations()) {
                    Annotation annotation = new Annotation(a.getName().toString());
                    annotation.setAnnotationExpr(a);
                    annotations.add(annotation);
                }
                return annotations;
            }
            return null;
        }

        public Object getByName(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public String toString() {
        return fieldDeclaration.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        List<VariableDeclarator> variables = new ArrayList<VariableDeclarator>();
        variables.add(new VariableDeclarator(new VariableDeclaratorId(name)));
        fieldDeclaration.setVariables(variables);
        this.name = name;
    }

    public FieldDeclaration getFieldDeclaration() {
        return fieldDeclaration;
    }

    public void setFieldDeclaration(FieldDeclaration fieldDeclaration) {
        this.fieldDeclaration = fieldDeclaration;
        name = fieldDeclaration.getVariables().get(0).getId().getName();
        modifier = fieldDeclaration.getModifiers();
        type = fieldDeclaration.getType();
        annotations = Annotations().list();
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        fieldDeclaration.setModifiers(modifier);
        this.modifier = modifier;
    }

    public Type getType() {
        return fieldDeclaration.getType();
    }

    public String getTypeName() {
        if (fieldDeclaration.getType() instanceof japa.parser.ast.type.ReferenceType) {
            return fieldDeclaration.getType().toString();
        } else if (fieldDeclaration.getType() instanceof ClassOrInterfaceType) {
            return ((ClassOrInterfaceType) fieldDeclaration.getType()).getName();
        } else if (fieldDeclaration.getType() instanceof PrimitiveType) {
            return ((PrimitiveType) fieldDeclaration.getType()).toString();
        } else if (fieldDeclaration.getType() instanceof PrimitiveType) {
            return ((PrimitiveType) fieldDeclaration.getType()).toString();
        }
        return null;
    }

    public void setType(Types types) {
        fieldDeclaration.setType(types.getType());
        this.type = type;
    }

    public void setType(Type type) {
        fieldDeclaration.setType(type);
        this.type = type;
    }

    public List<Annotation> getAnnotations() {
        return annotations = Annotations().list();
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Annotations Annotations() {
        return new Annotations();
    }
}
