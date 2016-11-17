/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.acc;

import japa.parser.ast.type.Type;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import java.util.ArrayList;
import java.util.List;
import projectparser.res.Annotation;

/**
 *
 * @author xp
 */
public class Expressions {
    
    private Expression valueExpr = null;

    public Expression getValueExpr() {
        return valueExpr;
    }

    public void setValueExpr(Expression valueExpr) {
        this.valueExpr = valueExpr;
    }
    
    public Expressions name(String name) {
        valueExpr = new NameExpr(name);
        return this;
    }

    public Expressions _boolean(boolean value) {
        valueExpr = new BooleanLiteralExpr(value);
        return this;
    }

    public Expressions charLiteral(String value) {
        valueExpr = new CharLiteralExpr(value);
        return this;
    }

    public Expressions _class(Types t) {
        valueExpr = new ClassExpr(t.getType());
        return this;
    }

    public Expressions doubleLiteral(String value) {
        valueExpr = new DoubleLiteralExpr(value);
        return this;
    }

    public Expressions enclosed(Expressions inner) {
        valueExpr = new EnclosedExpr(inner.valueExpr);
        return this;
    }
    
    public Expressions integerLiteral(String value) {
        valueExpr = new IntegerLiteralExpr(value);
        return this;
    }
    
    public Expressions LongLiteral(String value) {
        valueExpr = new LongLiteralExpr(value);
        return this;
    }
    
    public Expressions annotation(Annotation annotation) {
        valueExpr = annotation.getAnnotationExpr();
        return this;
    }
    
    public Expressions nullLiteral() {
        valueExpr = new NullLiteralExpr();
        return this;
    }
    
    public Expressions stringLiteral(String value) {
        valueExpr = new StringLiteralExpr(value);
        return this;
    }
    
    public Expressions _super(Expressions e) {
        valueExpr = new SuperExpr(e.valueExpr);
        return this;
    }
    
    public Expressions _this(Expressions e) {
        valueExpr = new ThisExpr(e.valueExpr);
        return this;
    }
    
    public Conditional conditional() { return new Conditional(); }
    public class Conditional {
        Expression condition;
        Expression _then;
        Expression _else;
        AssignExpr.Operator operator;
        public Expressions end() { 
            Expressions.this.valueExpr = new ConditionalExpr(condition,_then,_else);
            return Expressions.this; 
        }
        public Conditional condition(Expressions expr) {
            condition = expr.valueExpr;
            return this;
        }
        public Conditional _then(Expressions expr) {
            _then = expr.valueExpr;
            return this;
        }
        public Conditional _else(Expressions expr) {
            _else = expr.valueExpr;
            return this;
        }
    }

    public Assign assign() { return new Assign(); }
    public class Assign {
        Expression target;
        Expression value;
        AssignExpr.Operator operator;
        public Expressions end() { 
            Expressions.this.valueExpr = new AssignExpr(target,value,operator);
            return Expressions.this; 
        }
        public Assign target(Expressions expr) {
            target = expr.valueExpr;
            return this;
        }
        public Assign value(Expressions expr) {
            value = expr.valueExpr;
            return this;
        }
        public Assign operator(AssignExpr.Operator op) {
            operator = op;
            return this;
        }
    }

    public ArrayAccess arrayAccess() { return new ArrayAccess(); }
    public class ArrayAccess {
        Expression name;
        Expression index;
        public Expressions end() { 
            Expressions.this.valueExpr = new ArrayAccessExpr(name,index);
            return Expressions.this; 
        }
        public ArrayAccess name(Expressions expr) {
            name = expr.valueExpr;
            return this;
        }
        public ArrayAccess index(Expressions expr) {
            index = expr.valueExpr;
            return this;
        }
    }

    public ArrayInitializer arrayInitializer() { return new ArrayInitializer(); }
    public class ArrayInitializer { 
        List<Expression> expressions = new ArrayList<>();
        public Expressions end() { 
            Expressions.this.valueExpr = new ArrayInitializerExpr(expressions);
            return Expressions.this; 
        }
        public ArrayInitializer add(Expressions expr) {
            expressions.add(expr.valueExpr);
            return this;
        }
    }
    
    public Binary binary() { return new Binary(); }
    public class Binary { 
        Expression left;
        Expression right;
        BinaryExpr.Operator operator;
        public Expressions end() { 
            Expressions.this.valueExpr = new BinaryExpr(left,right,operator);
            return Expressions.this; 
        }
        public Binary left(Expressions expr) {
            left = expr.valueExpr;
            return this;
        }
        public Binary right(Expressions expr) {
            right = expr.valueExpr;
            return this;
        }
        public Binary operator(BinaryExpr.Operator op) {
            operator = op;
            return this;
        }
    }
    
    public ArrayCreation arrayCreation() { return new ArrayCreation(); }
    public class ArrayCreation { 
        Type type;
        int arraycount;
        List<Expression> dimensions = new ArrayList<>();
        public Expressions end() { 
            Expressions.this.valueExpr = new ArrayCreationExpr(type,dimensions,arraycount);
            return Expressions.this; 
        }
        public ArrayCreation type(Types t) {
            type = t.getType();
            return this;
        }
        public ArrayCreation add(Expressions expr) {
            dimensions.add(expr.valueExpr);
            return this;
        }
        public ArrayCreation arraycount(int count) {
            arraycount = count;
            return this;
        }
    }
    
    public Cast cast() { return new Cast(); }
    public class Cast { 
        Type type;
        Expression express;
        public Expressions end() { 
            Expressions.this.valueExpr = new CastExpr(type,express);
            return Expressions.this; 
        }
        public Cast type(Types t) {
            type = t.getType();
            return this;
        }
        public Cast expression(Expressions expr) {
            express = expr.valueExpr;
            return this;
        }
    }
    
    public FieldAccess fieldAccess() { return new FieldAccess(); }
    public class FieldAccess { 
        String field;
        Expression express;
        public Expressions end() { 
            Expressions.this.valueExpr = new FieldAccessExpr(express,field);
            return Expressions.this; 
        }
        public FieldAccess field(String f) {
            field = f;
            return this;
        }
        public FieldAccess expression(Expressions expr) {
            express = expr.valueExpr;
            return this;
        }
    }
    
    public InstanceOf instanceOf() { return new InstanceOf(); }
    public class InstanceOf { 
        Type type;
        Expression express;
        public Expressions end() { 
            Expressions.this.valueExpr = new InstanceOfExpr(express,type);
            return Expressions.this; 
        }
        public InstanceOf type(Types t) {
            type = t.getType();
            return this;
        }
        public InstanceOf expression(Expressions expr) {
            express = expr.valueExpr;
            return this;
        }
    }
     
    public MethodCall methodCall() { return new MethodCall(); }
    public class MethodCall { 
        
        private Expression scope;
        private String name;
        private List<Expression> args = new ArrayList<>();
        private MethodCallExpr method = new MethodCallExpr();
        
        public Expressions end() { 
            valueExpr = method;
            return Expressions.this; 
        }
        public MethodCall scope(Expressions expr) {
            scope = expr.valueExpr;
            method.setScope(scope);
            return this;
        }
        public MethodCall name(String n) {
            name = n;
            method.setName(name);
            return this;
        }
        public MethodCall add(Expressions argument) {
            if(method.getArgs()==null)
                method.setArgs(new ArrayList<Expression>());
            args.add(argument.valueExpr);
            method.setArgs(args);
            return this;
        }

        public MethodCallExpr getMethod() {
            return method;
        }

        public void setMethod(MethodCallExpr method) {
            this.method = method;
        }
        
    }
    
    public ObjectCreation objectCreation() { return new ObjectCreation(); }
    public class ObjectCreation { 
        Expression scope;
        ClassOrInterfaceType type;
        List<Expression> args = new ArrayList<>();
        public Expressions end() { 
            Expressions.this.valueExpr = new ObjectCreationExpr(scope,type,args);
            return Expressions.this; 
        }
        public ObjectCreation scope(Expressions expr) {
            scope = expr.valueExpr;
            return this;
        }
        public ObjectCreation type(Types t) {
            type = (ClassOrInterfaceType) t.getType();
            return this;
        }
        public ObjectCreation add(Expressions expr) {
            args.add(expr.valueExpr);
            return this;
        }
    }
    
    public QualifiedName qualifiedName() { return new QualifiedName(); }
    public class QualifiedName { 
        NameExpr scope;
        String name;
        public Expressions end() { 
            Expressions.this.valueExpr = new QualifiedNameExpr(scope,name);
            return Expressions.this; 
        }
        public QualifiedName scope(String s) {
            scope = new NameExpr(s);
            return this;
        }
        public QualifiedName name(String n) {
            name = n;
            return this;
        }
    }
    
    public Unary unary() { return new Unary(); }
    public class Unary { 
        Expression expr;
        UnaryExpr.Operator operator;
        public Expressions end() { 
            Expressions.this.valueExpr = new UnaryExpr(expr,operator);
            return Expressions.this; 
        }
        public Unary expr(Expressions e) {
            expr = e.valueExpr;
            return this;
        }
        public Unary name(UnaryExpr.Operator op) {
            operator = op;
            return this;
        }
    }
    
    public VariableDeclaration variableDeclaration() { return new VariableDeclaration(); }
    public class VariableDeclaration { 
        private Type type;
        private List<VariableDeclarator> vars = new ArrayList<>();
        private VariableDeclarationExpr variable;
        
        public Expressions end() { 
            Expressions.this.valueExpr = new VariableDeclarationExpr(type,vars);
            return Expressions.this; 
        }
        
        public VariableDeclaration type(Types t) {
            type = t.getType();
            return this;
        }
        
        public Variables variables() { return new Variables(); }
        
        public class Variables {
            
            public VariableDeclaration end() { return VariableDeclaration.this; }
            
            public Add add() { return new Add(); }

            public class Add {
                VariableDeclarator var = new VariableDeclarator();

                public Variables end() { 
                    vars.add(var);
                    return Variables.this; 
                }

                public Add name(String name) {
                    var.setId(new VariableDeclaratorId(name));
                    return this;
                }
                 public Add init(Expressions expr) {
                    var.setInit(expr.valueExpr);
                    return this;
                 }
            }
        }

        public VariableDeclarationExpr getVariable() {
            return variable;
        }

        public void setVariable(VariableDeclarationExpr variable) {
            this.variable = variable;
        }
        
    }
    
}
