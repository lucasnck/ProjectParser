/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.res;

import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.type.Type;
import java.util.ArrayList;
import java.util.List;
import projectparser.acc.Statements;
import projectparser.acc.Types;

/**
 *
 * @author Lucas
 */
public class Method {

    private MethodDeclaration methodDeclaration;

    private String name;

    private int modifier = ModifierSet.PUBLIC;

    private List<Parameter> parameters;

    private int arrayCount;

    private List<Annotation> annotations;

    private List<NameExpr> trowns;

    private BlockStmt block;

    private List<TypeParameter> typeParameters;

    public Method(String name, Types type) {
        methodDeclaration = new MethodDeclaration();
        setName(name);
        setType(type);
        setModifier(modifier);
    }
    
    public Method(String name, Type type) {
        methodDeclaration = new MethodDeclaration();
        setName(name);
        setType(type);
        setModifier(modifier);
    }

    public Method(MethodDeclaration methodDeclaration) {
        setMethodDeclaration(methodDeclaration);

    }

    public void add(Statements statement) {
        if (methodDeclaration.getBody() == null) {
            methodDeclaration.setBody(new BlockStmt());
        }
        if (methodDeclaration.getBody().getStmts() == null) {
            methodDeclaration.getBody().setStmts(new ArrayList<Statement>());
        }
        methodDeclaration.getBody().getStmts().add(statement.getStatement());
    }

    public void add(Parameter parameter) {
        if (methodDeclaration.getParameters() == null) {
            methodDeclaration.setParameters(new ArrayList<japa.parser.ast.body.Parameter>());
        }
        methodDeclaration.getParameters().add(parameter.getParameter());
    }

    public class Annotations {

        public Annotations add(Annotation annotation) {
            if (annotations == null) {
                annotations = new ArrayList<Annotation>();
            }
            getAnnotations().add(annotation);
            if (methodDeclaration.getAnnotations() == null) {
                methodDeclaration.setAnnotations(new ArrayList<AnnotationExpr>());
            }
            methodDeclaration.getAnnotations().add(annotation.getAnnotationExpr());
            return this;
        }

        public Annotations remove(String name) {
            List<AnnotationExpr> annotationDeclarations = methodDeclaration.getAnnotations();
            List<AnnotationExpr> newlist = new ArrayList<>();
            for (AnnotationExpr a : annotationDeclarations) {
                if (a.getName().toString().startsWith(name)) {
                    a = null;
                } else {
                    newlist.add(a);
                }
            }
            methodDeclaration.setAnnotations(newlist);
            return this;
        }

        public Annotations delete(AnnotationExpr annotation) {
            if (methodDeclaration.getAnnotations() != null) {
                int index = 0;
                for (AnnotationExpr a : methodDeclaration.getAnnotations()) {
                    if (annotation == a) {
                        break;
                    }
                    index++;
                }
                methodDeclaration.getAnnotations().remove(index);
            }
            return this;
        }

        public List<Annotation> list() {
            if (methodDeclaration.getAnnotations() != null) {
                List<Annotation> annotations = new ArrayList<>();
                for (AnnotationExpr a : methodDeclaration.getAnnotations()) {
                    Annotation annotation = new Annotation(a.getName().toString());
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

    public class Body {

        public Body add(Statements statement) {
            if (block == null) {
                block = new BlockStmt();
            }
            if (block.getStmts() == null) {
                block.setStmts(new ArrayList<Statement>());
            }
            block.getStmts().add(statement.getStatement());
            methodDeclaration.setBody(block);
            return this;
        }

        public Body delete(Statement statement) {
            if (getBlock().getStmts() != null) {
                int index = 0;
                for (Statement s : getBlock().getStmts()) {
                    if (s == statement) {
                        break;
                    }
                    index++;
                }
                getBlock().getStmts().remove(index);
            }
            methodDeclaration.setBody(block);
            return this;
        }

        public List<Statements> list() {
            List<Statements> statements = new ArrayList<>();
            if (getBlock().getStmts() != null) {
                for (Statement s : getBlock().getStmts()) {
                    statements.add(new Statements().setStatement(s));
                }
            }
            return statements;
        }

        public Object getByName(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public class Parameters {

        public Parameters add(Parameter parameter) {
            if (parameters == null) {
                parameters = new ArrayList<Parameter>();
            }
            getParameters().add(parameter);
            if (methodDeclaration.getParameters() == null) {
                methodDeclaration.setParameters(new ArrayList<japa.parser.ast.body.Parameter>());
            }
            methodDeclaration.getParameters().add(parameter.getParameter());
            return this;
        }

        public Object getByName(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public List list() {
            return new ArrayList();
        }

    }

    @Override
    public String toString() {
        return methodDeclaration.toString();
    }

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
        name = (methodDeclaration.getName());
        modifier = (methodDeclaration.getModifiers());
        parameters = Parameters().list();
        annotations = Annotations().list();
        arrayCount = (methodDeclaration.getArrayCount());
        trowns = (methodDeclaration.getThrows());
        typeParameters = (methodDeclaration.getTypeParameters());
        block = (methodDeclaration.getBody());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        methodDeclaration.setName(name);
        this.name = name;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        methodDeclaration.setModifiers(modifier);
        this.modifier = modifier;
    }

    public Type getType() {
        return methodDeclaration.getType();
    }

    public void setType(Types type) {
        methodDeclaration.setType(type.getType());
    }
    
    public void setType(Type type) {
        methodDeclaration.setType(type);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public int getArrayCount() {
        return arrayCount;
    }

    public void setArrayCount(int arrayCount) {
        methodDeclaration.setArrayCount(arrayCount);
        this.arrayCount = arrayCount;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public List<NameExpr> getTrowns() {
        return trowns;
    }

    public void setTrowns(List<NameExpr> trowns) {
        methodDeclaration.getThrows();
        this.trowns = trowns;
    }

    public BlockStmt getBlock() {
        return block;
    }

    public void setBlock(BlockStmt block) {
        methodDeclaration.setBody(block);
        this.block = block;
    }

    public List<TypeParameter> getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(List<TypeParameter> typeParameters) {
        methodDeclaration.setTypeParameters(typeParameters);
        this.typeParameters = typeParameters;
    }

    public Annotations Annotations() {
        return new Annotations();
    }

    public Parameters Parameters() {
        return new Parameters();
    }

    public Body Body() {
        return new Body();
    }
}
