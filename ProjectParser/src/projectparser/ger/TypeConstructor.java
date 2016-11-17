/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.ger;

import static projectparser.ger.ListConstructor.isList;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;
import java.util.ArrayList;
import java.util.List;
import static projectparser.ger.ListConstructor.isList;

/**
 *
 * @author Lucas
 */
public class TypeConstructor {
    
    public static boolean isClassOrInterface(Type type) {
        return type instanceof ClassOrInterfaceType;
    }
    

}
