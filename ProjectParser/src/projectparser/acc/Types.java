/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.acc;

import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xp
 */
public class Types {
    
    private Type type = null;

    public Type getType() {
        return type;
    }

    public Types classOrInterface(String name) {
        type = new ClassOrInterfaceType(name);
        return this;
    }
    
    public Types primitive(PrimitiveType.Primitive primitive) {
        type = new PrimitiveType(primitive);
        return this;
    }
    
    public Reference reference() { return new Reference(); }
    public class Reference {
        Type t;
        public Types end() { type = new ReferenceType(t); return Types.this; }
        public Reference type(Types type) {
            t = type.type;
            return this;
        }
    }
    
    public Types _void() {
        type = new VoidType();
        return this;
    }
    
    public Wildcard wildcard() { return new Wildcard(); }
    public class Wildcard {
        Type ext;
        Type sup;
        public Types end() { 
            if(sup!=null)
                type = new WildcardType(new ReferenceType(ext),new ReferenceType(sup)); 
            else
                type = new WildcardType(new ReferenceType(ext)); 
            return Types.this; 
        }
        public Wildcard referenceExt(Types type) {
            ext = type.type;
            return this;
        }
        public Wildcard referenceSup(Types type) {
            sup = type.type;
            return this;
        }
    }
    
    public ClassOrInterface classOrInterface() { return new ClassOrInterface(); }
    public class ClassOrInterface {
        private ClassOrInterfaceType ClassOrInterface = new ClassOrInterfaceType(1, 1, 1, 1, null, null, null);
        List<Type> argumments;
        public Types end() { 
            type = ClassOrInterface;
            return Types.this; 
        }
        public ClassOrInterface name(String name) {
            ClassOrInterface.setName(name);
            return this;
        }
        public ClassOrInterface scope(Types.ClassOrInterface t) {
            ClassOrInterface.setScope(t.ClassOrInterface);
            return this;
        }
        public ClassOrInterface addArgumment(Types type) {
            if(argumments == null) 
                argumments = new ArrayList<Type>();
            argumments.add(type.type);
            ClassOrInterface.setTypeArgs(argumments);
            return this;
        }

        public ClassOrInterfaceType getClassOrInterface() {
            return ClassOrInterface;
        }

    }

    public Types setType(Type type) {
        this.type = type;
        return this;
    }
    
}
