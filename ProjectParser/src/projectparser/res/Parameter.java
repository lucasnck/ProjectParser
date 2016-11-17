/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.res;

import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.type.Type;
import projectparser.acc.Types;

/**
 *
 * @author Lucas
 */
public class Parameter {
    
    private japa.parser.ast.body.Parameter parameter;
    
    private String name;
    
    private Type type;
    
    private boolean varArgs = false;
    
    private int arrayCount;

    public Parameter(String name, Types type) {
        parameter = new japa.parser.ast.body.Parameter();
        setName(name);
        setType(type);
        setVarArgs(varArgs);
    }
    
    public Parameter(String name, Type type) {
        parameter = new japa.parser.ast.body.Parameter();
        setName(name);
        setType(type);
        setVarArgs(varArgs);
    }

    public japa.parser.ast.body.Parameter getParameter() {
        return parameter;
    }

    public void setParameter(japa.parser.ast.body.Parameter parameter) {
        this.parameter = parameter;
        name = parameter.getId().getName();
        type = parameter.getType();
        arrayCount = parameter.getId().getArrayCount();
        varArgs = parameter.isVarArgs();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        parameter.setId(new VariableDeclaratorId(name));
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Types type) {
        parameter.setType(type.getType());
        this.type = type.getType();
    }
    
    public void setType(Type type) {
        parameter.setType(type);
        this.type = type;
    }

    public boolean isVarArgs() {
        return varArgs;
    }

    public void setVarArgs(boolean varArgs) {
        parameter.setVarArgs(varArgs);
        this.varArgs = varArgs;
    }

    public int getArrayCount() {
        return arrayCount;
    }

    public void setArrayCount(int arrayCount) {
        parameter.getId().setArrayCount(arrayCount);
        this.arrayCount = arrayCount;
    }
}
