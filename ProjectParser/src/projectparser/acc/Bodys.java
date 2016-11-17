/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.acc;

import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class Bodys {

    private BodyDeclaration body;

    public BodyDeclaration getBody() {
        return body;
    }

    public void setBody(BodyDeclaration body) {
        this.body = body;
    }

    public Bodys EmptyMember() {
        body = new EmptyMemberDeclaration();
        return this;
    }

    public Annotation Annotation() {
        return new Annotation();
    }

    public class Annotation {

        private String name;
        private int modifiers;
        private List<AnnotationExpr> annotations;
        private List<BodyDeclaration> members;
        private AnnotationDeclaration ad;

        public Bodys end() {
            ad = new AnnotationDeclaration(null, modifiers, annotations, name, members);
            body = ad;
            return Bodys.this;
        }

        public Annotation name(String n) {
            name = n;
            return this;
        }

        public Annotation modifiers(int ModifierSet) {
            modifiers = ModifierSet;
            return this;
        }

        public Annotation addAnnotationExpr(projectparser.res.Annotation annotation) {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            annotations.add(annotation.getAnnotationExpr());
            return this;
        }

        public Annotation addMembers(Bodys body) {
            if (members == null) {
                members = new ArrayList<>();
            }
            members.add(body.getBody());
            return this;
        }

        public AnnotationDeclaration getAd() {
            return ad;
        }

        public void setAd(AnnotationDeclaration ad) {
            this.ad = ad;
        }

    }

    public ClassOrInterface ClassOrInterface() {
        return new ClassOrInterface();
    }

    public class ClassOrInterface {

        private String name;
        private int modifiers;
        private boolean isInterface = false;
        private List<AnnotationExpr> annotations;
        private List<BodyDeclaration> members;
        private List<TypeParameter> typeParameters;
        private List<ClassOrInterfaceType> extensions;
        private List<ClassOrInterfaceType> implementations;
        private ClassOrInterfaceDeclaration classType;

        public Bodys end() {
            classType = new ClassOrInterfaceDeclaration(null,
                    modifiers,
                    annotations,
                    isInterface,
                    name,
                    typeParameters,
                    extensions,
                    implementations,
                    members);
            body = classType;
            return Bodys.this;
        }

        public ClassOrInterface name(String n) {
            name = n;
            return this;
        }

        public ClassOrInterface modifiers(int ModifierSet) {
            modifiers = ModifierSet;
            return this;
        }

        public ClassOrInterface isInterface(boolean is) {
            isInterface = is;
            return this;
        }

        public ClassOrInterface addAnnotationExpr(projectparser.res.Annotation annotation) {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            annotations.add(annotation.getAnnotationExpr());
            return this;
        }

        public ClassOrInterface addMembers(Bodys body) {
            if (members == null) {
                members = new ArrayList<>();
            }
            members.add(body.getBody());
            return this;
        }

        public ClassOrInterface addExtends(Types.ClassOrInterface extension) {
            if (extensions == null) {
                extensions = new ArrayList<>();
            }
            extensions.add(extension.getClassOrInterface());
            return this;
        }

        public ClassOrInterface addImplement(Types.ClassOrInterface implementation) {
            if (implementations == null) {
                implementations = new ArrayList<>();
            }
            implementations.add(implementation.getClassOrInterface());
            return this;
        }

        public ClassOrInterface addTypeBound(TP typeParameter) {
            if (typeParameters == null) {
                typeParameters = new ArrayList<>();
            }
            typeParameters.add(typeParameter.getType());
            return this;
        }

        public ClassOrInterfaceDeclaration getClassType() {
            return classType;
        }

        public void setClassType(ClassOrInterfaceDeclaration _class) {
            this.classType = _class;
        }
        
    }

    public Constructor Constructor() {
        return new Constructor();
    }

    public class Constructor {

        private String name;
        private int modifiers = ModifierSet.PUBLIC;
        private List<AnnotationExpr> annotations;
        private List<TypeParameter> typeParameters;
        private List<Parameter> parameters;
        private List<NameExpr> _throws;
        private ConstructorDeclaration cd = new ConstructorDeclaration();
        private BlockStmt block;

        public Bodys end() {
            cd = new ConstructorDeclaration(null,
                    modifiers,
                    annotations,
                    typeParameters,
                    name,
                    parameters,
                    _throws,
                    block);
            body = cd;
            return Bodys.this;
        }

        public Constructor name(String n) {
            name = n;
            cd.setName(name);
            return this;
        }

        public Constructor modifiers(int ModifierSet) {
            modifiers = ModifierSet;
            cd.setModifiers(modifiers);
            return this;
        }

        public Constructor addAnnotationExpr(projectparser.res.Annotation annotation) {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            annotations.add(annotation.getAnnotationExpr());
            cd.setAnnotations(annotations);
            return this;
        }

        public Constructor addTypeBound(TP typeParameter) {
            if (typeParameters == null) {
                typeParameters = new ArrayList<>();
            }
            typeParameters.add(typeParameter.getType());
            cd.setTypeParameters(typeParameters);
            return this;
        }

        public Constructor addParameter(projectparser.res.Parameter parameter) {
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            parameters.add(parameter.getParameter());
            cd.setParameters(parameters);
            return this;
        }

        public Constructor _throws(String name) {
            if (_throws == null) {
                _throws = new ArrayList<>();
            }
            _throws.add(new NameExpr(name));
            cd.setThrows(_throws);
            return this;
        }

        public Constructor block(Statements.Block b) {
            block = b.getBlock();
            cd.setBlock(block);
            return this;
        }

        public ConstructorDeclaration getCd() {
            cd.setModifiers(modifiers);
            return cd;
        }

        public void setCd(ConstructorDeclaration cd) {
            this.cd = cd;
        }
    }

    public Enum Enum() {
        return new Enum();
    }

    public class Enum {

        private String name;
        private int modifiers;
        private List<AnnotationExpr> annotations;
        private List<BodyDeclaration> members;
        private List<ClassOrInterfaceType> implementations;
        private List<EnumConstantDeclaration> entries;
        private EnumDeclaration e;

        public Bodys end() {
            e = new EnumDeclaration(null, modifiers, annotations, name, implementations, entries, members);
            body = e;
            return Bodys.this;
        }

        public Enum name(String n) {
            name = n;
            return this;
        }

        public Enum modifiers(int ModifierSet) {
            modifiers = ModifierSet;
            return this;
        }

        public Enum addAnnotationExpr(projectparser.res.Annotation annotation) {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            annotations.add(annotation.getAnnotationExpr());
            return this;
        }

        public Enum addMembers(Bodys body) {
            if (members == null) {
                members = new ArrayList<>();
            }
            members.add(body.getBody());
            return this;
        }

        public Enum addImplement(Types.ClassOrInterface implementation) {
            if (implementations == null) {
                implementations = new ArrayList<>();
            }
            implementations.add(implementation.getClassOrInterface());
            return this;
        }

        public Enum addConstant(Bodys.EnumConstant constant) {
            if (entries == null) {
                entries = new ArrayList<>();
            }
            entries.add(constant.getConstant());
            return this;
        }
    }
    
    public Field Field() {
        return new Field();
    }
    
    public class Field {

        private String name;
        private int modifiers;
        private Types type;
        private List<AnnotationExpr> annotations;
        private List<VariableDeclarator> variables;
        private FieldDeclaration f;

        public Bodys end() {
            f = new FieldDeclaration(null, modifiers, annotations, type.getType(), variables);
            body = f;
            return Bodys.this;
        }

        public Field name(String n) {
            name = n;
            return this;
        }

        public Field modifiers(int ModifierSet) {
            modifiers = ModifierSet;
            return this;
        }
        
        public Field type(Types t) {
            type = t;
            return this;
        }

        public Field addAnnotationExpr(projectparser.res.Annotation annotation) {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            annotations.add(annotation.getAnnotationExpr());
            return this;
        }
        
        public Field addAnnotationExpr(Bodys.Variable var) {
            if (variables == null) {
                variables = new ArrayList<>();
            }
            variables.add(var.getVariable());
            return this;
        }
    }
    
    public Variable Variable() {
        return new Variable();
    }
    
    public class Variable {
        
        private VariableDeclaratorId id;
        private Expression expr;
        private VariableDeclarator variable;

        public Bodys end() {
            variable = new VariableDeclarator(id, expr);
            return Bodys.this;
        }
        
        public Variable name(String name) {
            id.setName(name);
            return this;
        }
        
        public Variable arrayCount(int arrayCount) {
            id.setArrayCount(arrayCount);
            return this;
        }
        
        public Variable initializator(Expressions init) {
            expr = init.getValueExpr();
            return this;
        }

        public VariableDeclarator getVariable() {
            return variable;
        }

        public void setVariable(VariableDeclarator variable) {
            this.variable = variable;
        }
        
    }
    
    public Method Method() {
        return new Method();
    }
    
    public class Method {

        private String name;
        private int modifiers;
        private int arrayCount;
        private Types type;
        private List<AnnotationExpr> annotations;
        private List<TypeParameter> typeParameters;
        private List<Parameter> parameters;
        private List<NameExpr> _throws;
        private BlockStmt block;
        private MethodDeclaration f;

        public Bodys end() {
            f = new MethodDeclaration(null, 
                                      modifiers, 
                                      annotations, 
                                      typeParameters, 
                                      type.getType(),
                                      name, 
                                      parameters,
                                      arrayCount, 
                                      _throws, 
                                      block);
            body = f;
            return Bodys.this;
        }

        public Method name(String n) {
            name = n;
            return this;
        }

        public Method modifiers(int ModifierSet) {
            modifiers = ModifierSet;
            return this;
        }
        
        public Method arrayCount(int count) {
            arrayCount = count;
            return this;
        }
        
        public Method type(Types t) {
            type = t;
            return this;
        }

        public Method addAnnotationExpr(projectparser.res.Annotation annotation) {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            annotations.add(annotation.getAnnotationExpr());
            return this;
        }
        
        public Method block(Statements.Block b) {
            block = b.getBlock();
            return this;
        }
        
        public Method addTypeBound(TP typeParameter) {
            if (typeParameters == null) {
                typeParameters = new ArrayList<>();
            }
            typeParameters.add(typeParameter.getType());
            return this;
        }

        public Method addParameter(projectparser.res.Parameter parameter) {
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            parameters.add(parameter.getParameter());
            return this;
        }

        public Method _throws(String name) {
            if (_throws == null) {
                _throws = new ArrayList<>();
            }
            _throws.add(new NameExpr(name));
            return this;
        }
        
        
    }

    public Initializer Initializer() {
        return new Initializer();
    }

    public class Initializer {

        private boolean isStatic = false;
        private BlockStmt block;
        private InitializerDeclaration i;

        public Bodys end() {
            i = new InitializerDeclaration(null, isStatic, null);
            body = i;
            return Bodys.this;
        }

        public Initializer isStatic(boolean is) {
            isStatic = is;
            return this;
        }

        public Initializer block(Statements.Block b) {
            block = b.getBlock();
            return this;
        }
    }

    public Type Type() {
        return new Type();
    }

    public class Type {

        private String name;
        private int modifiers;
        private List<AnnotationExpr> annotations;
        private List<BodyDeclaration> members;

        public Bodys end() {
            body = new TypeDeclaration(annotations, null, modifiers, name, members) {

                @Override
                public <R, A> R accept(GenericVisitor<R, A> gv, A a) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public <A> void accept(VoidVisitor<A> vv, A a) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            return Bodys.this;
        }

        public Type name(String n) {
            name = n;
            return this;
        }

        public Type modifiers(int ModifierSet) {
            modifiers = ModifierSet;
            return this;
        }

        public Type addAnnotationExpr(projectparser.res.Annotation annotation) {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            annotations.add(annotation.getAnnotationExpr());
            return this;
        }

        public Type addMembers(Bodys body) {
            if (members == null) {
                members = new ArrayList<>();
            }
            members.add(body.getBody());
            return this;
        }
    }

    public EnumConstant EnumConstant() {
        return new EnumConstant();
    }

    public class EnumConstant {

        private EnumConstantDeclaration constant;
        private String name;
        private List<AnnotationExpr> annotations;
        private List<Expression> args;
        private List<BodyDeclaration> members;

        public Bodys end() {
            constant = new EnumConstantDeclaration(null, annotations, name, args, members);
            body = constant;
            return Bodys.this;
        }

        public EnumConstant name(String n) {
            name = n;
            return this;
        }

        public EnumConstant addAnnotationExpr(projectparser.res.Annotation annotation) {
            if (annotations == null) {
                annotations = new ArrayList<>();
            }
            annotations.add(annotation.getAnnotationExpr());
            return this;
        }

        public EnumConstant addMembers(Bodys body) {
            if (members == null) {
                members = new ArrayList<>();
            }
            members.add(body.getBody());
            return this;
        }

        public EnumConstant addExpression(Expressions expr) {
            if (args == null) {
                args = new ArrayList<>();
            }
            args.add(expr.getValueExpr());
            return this;
        }

        public EnumConstantDeclaration getConstant() {
            return constant;
        }

        public void setConstant(EnumConstantDeclaration constant) {
            this.constant = constant;
        }
    }

    public TP typeParameter() {
        return new TP();
    }

    public class TP {

        private String name;
        private List<ClassOrInterfaceType> typeBound;
        private TypeParameter type;

        public TP end() {
            type = new TypeParameter(name, typeBound);
            return this;
        }

        public TP name(String n) {
            name = n;
            return this;
        }

        public TP addTypeBound(Types.ClassOrInterface bound) {
            if (typeBound == null) {
                typeBound = new ArrayList<>();
            }
            typeBound.add(bound.getClassOrInterface());
            return this;
        }

        public TypeParameter getType() {
            return type;
        }

        public void setType(TypeParameter type) {
            this.type = type;
        }
    }

}
