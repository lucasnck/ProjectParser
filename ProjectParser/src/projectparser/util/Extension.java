/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.util;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author lucasnck
 */
public class Extension {
    
    public static final Extension java = new Extension(".java");
    public static final Extension xml = new Extension(".xml");
    public static final Extension configuration = new Extension(".configuration");
    public static final Extension properties = new Extension(".properties");
    public static final Extension MANIFEST = new Extension(".MF");
    public static final Extension xhtml = new Extension(".xhtml");
    public static final Extension html = new Extension(".html");
    public static final Extension rar = new Extension(".rar");
    public static final Extension jar = new Extension(".jar");
    public static final Extension CLASS = new Extension(".class");
    public static final Extension txt = new Extension(".txt");
    
    private String extension;
    
    public Extension(String extension) {
        set(extension);
    }
    
    public void set(String extension) {
        char[] extensionChar = extension.toCharArray();
        if(extensionChar[0] == '.') {
            this.extension = extension;
        } else {
            String extensionString = ".";
            extensionString += extension;
            this.extension = extensionString;
        }
    }
    
    public static Extension getExtension(File file) {
        String path = file.getAbsolutePath();
        return new Extension(FilenameUtils.getExtension(path));
    }
    
    @Override
    public String toString() {
        return extension;
    }
    
    public boolean equals(String extension) {
        return this.extension.equals(extension);
    }
    
    public boolean equals(Extension extension) {
        return this == extension;
    }
    
}
