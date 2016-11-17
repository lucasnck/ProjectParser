/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.acc;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.TypeDeclaration;

/**
 *
 * @author Lucas
 */
public class Members {
    
    private TypeDeclaration typeDeclaration;
    
    public Class Class() {
        return new Class();
    }
    
    public class Class {
        
        private ClassOrInterfaceDeclaration _class = new ClassOrInterfaceDeclaration();

        public Class() {
        }

        public Class(ClassOrInterfaceDeclaration _class) {
            this._class = _class;
        }
        
        public Members end() {
            _class.setInterface(false);
            typeDeclaration = _class;
            return Members.this;
        }
        
        public Class create(Bodys.ClassOrInterface _class) {
            this._class = _class.getClassType();
            return this;
        }
        
    }
    
    public Interface Interface() {
        return new Interface();
    }
    
    public class Interface {
        
        private ClassOrInterfaceDeclaration _interface = new ClassOrInterfaceDeclaration();

        public Interface() {
        }

        public Interface(ClassOrInterfaceDeclaration _interface) {
            this._interface = _interface;
        }
        
        public Members end() {
            _interface.setInterface(true);
            typeDeclaration = _interface;
            return Members.this;
        }
        
        public Interface create(Bodys.ClassOrInterface _class) {
            this._interface = _class.getClassType();
            return this;
        }
        
    }

    public TypeDeclaration getTypeDeclaration() {
        return typeDeclaration;
    }

    public void setTypeDeclaration(TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }
    
}
