/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author lucasnck
 */
public final class Path {
    
    private static final Logger LOG = Logger.getLogger(Path.class.getName()); //.log(Level.SEVERE, "msg", ex);
    
    private String path;
    
    /**
     * Init the path with // or \\ and two folders
     * End the path with the folder name
     * @param path 
     */
    public Path(String path) {
        this.path = check(path);
    }
    
    public void addFolder(String name) {
        if(isDirectory())
            this.path += File.separator + name;
        if(getExtension()==null)
            this.path += File.separator + name;
    }

    public java.nio.file.Path getSystemPath() {
        return Paths.get(path);
    }
    
    public Path getParentPath() {
        return new Path(getFile().getParentFile().getPath());
    }

    public File getFile() {
        return new File(path);
    }

    public boolean isAbsolute() {
        return getSystemPath().isAbsolute();
    }

    public boolean isDirectory() {
        Extension ext = null;
        String stringExtension = FilenameUtils.getExtension(path);
        if(!stringExtension.isEmpty())
            ext = new Extension(stringExtension);
        else
            ext = null;
        if(ext!=null)
            return false;
        else if(isAbsolute())
            return getFile().isDirectory();
        else  
            return !getFile().isFile();
    }
    
    public Extension getExtension() {
        if(!isDirectory()) {
            Extension ext = null;
            String stringExtension = FilenameUtils.getExtension(path);
                if(!stringExtension.isEmpty())
                    ext = new Extension(stringExtension);
                return ext;
        } else
            return null;
    }
    
    public String getName() {
        return getSystemPath().getFileName().toString();
    }
    
    public String getNameWithoutExtension() {
        String[] n;
        String name = "";
        if(!isDirectory()) {
             n = getFile().getName().split("\\.");
            if(n.length>2) {
                name = n[0];
                name += '.';
                name += n[1];
            } else {
                name = n[0];
            }
        }
        return name;
    }
    
    public String getPatentName() {
        return getFile().getParent();
    }
    
    public String getRoot() {
        return getSystemPath().getRoot().toString();
    }
    
    public String getUserHome() {
        return System.getProperty("user.home");
    }
    
    public String getUserWorkingDirectory() {
        return System.getProperty("user.dir");
    }
    
    public String getUserName() {
        return System.getProperty("user.name");
    }
    
    public static Path unionAbsoluteToRelative(Path absolute, Path relative) {
        String union = absolute.toString();
        union += File.separator;
        union += relative.toString();
        return new Path(union);
    }
    
    public static Path unionRelativePaths(Path father, Path child) {
        String union = father.toString();
        union += child.toString();
        return new Path(union);
    }
    
    private String check(String path) {
        String p = path;
        if(path.startsWith(File.separator))
            p = path.substring(1);
        return p;
    }
    
    @Override
    public String toString() {
        return getSystemPath().toString();
    }
    
}
