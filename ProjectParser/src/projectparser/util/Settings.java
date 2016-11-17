/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.util;

/**
 *
 * @author Lucas
 */
public class Settings {
    
    //path do not finish with "//"
    private static final Path main = new Path("C:\\JWIDB");
    
    public static Path getMain() {
        return main;
    }
    
}
