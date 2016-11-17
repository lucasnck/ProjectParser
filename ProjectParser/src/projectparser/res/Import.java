/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.res;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.expr.NameExpr;

/**
 *
 * @author Lucas
 */
public class Import {
    
    private String name;
    
    private ImportDeclaration _import;
    
    private boolean isAsterisk = false;
    
    private boolean isStatic = false;

    public Import(String name) {
        setName(name);
    }

    public Import(ImportDeclaration imp) {
        setImport(imp);
    }

    @Override
    public String toString() {
        return _import.toString();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        _import = new ImportDeclaration(new NameExpr(name), isStatic, isAsterisk);
        this.name = name;
    }

    public ImportDeclaration getImport() {
        return _import;
    }

    public void setImport(ImportDeclaration _import) {
        name = _import.getName().toString();
        isAsterisk = _import.isAsterisk();
        isStatic = _import.isStatic();
        this._import = _import;
    }

    public boolean isIsAsterisk() {
        return isAsterisk;
    }

    public void setIsAsterisk(boolean isAsterisk) {
        _import = new ImportDeclaration(new NameExpr(name), isStatic, isAsterisk);
        this.isAsterisk = isAsterisk;
    }

    public boolean isIsStatic() {
        return isStatic;
    }

    public void setIsStatic(boolean isStatic) {
        _import = new ImportDeclaration(new NameExpr(name), isStatic, isAsterisk);
        this.isStatic = isStatic;
    }
    
}
