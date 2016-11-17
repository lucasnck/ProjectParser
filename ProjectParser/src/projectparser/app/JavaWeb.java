/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.app;

import java.io.File;
import projectparser.man.FileManipulator;
import projectparser.man.FolderManipulator;
import projectparser.res.Project;
import projectparser.util.Extension;
import projectparser.util.Path;

/**
 *
 * @author xp
 */
public class JavaWeb {
    
    private final String name = "JavaWeb";
    private final String version = "1.0";
    
    private final Application application;
    private final Project project;

    public JavaWeb(Application application, Project project) {
        this.application = application;
        this.project = project;
    }
    
    public void init() {
        
        this.application.setName(name);
        this.application.setVersion(version);
        
    }
    
    public void create() {
        
        createJava();
        createConf();
        createWeb();
        createWebinf();
        createMetainf();
        
    }
    
    public void createConf() {
        Path path = new Path(project.getLocal()+File.separator+project.getName()+File.separator+"src"+File.separator+"conf");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
        FileManipulator manifest = new FileManipulator(path, "MANIFEST", Extension.MANIFEST);
        manifest.create();
        
        manifest.Update().text("Manifest-Version: 1.0\n").end();
    }
    
    public void createJava() {
        Path path = new Path(project.getLocal()+File.separator+project.getName()+File.separator+"src"+File.separator+"java");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
        project.setPackagesPath(new Path(File.separator+"src"+File.separator+"java"));
    }
    
    public void createWeb() {
        Path path = new Path(project.getLocal()+File.separator+project.getName()+File.separator+"web");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
    }
    
    public void createWebinf() {
        Path path = new Path(project.getLocal()+File.separator+project.getName()+File.separator+"web"+File.separator+"WEB-INF");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
        FileManipulator file = new FileManipulator(path, "web", Extension.xml);
        file.create();
    }
    
    public void createMetainf() {
        Path path = new Path(project.getLocal()+File.separator+project.getName()+File.separator+"web"+File.separator+"META-INF");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
        FileManipulator file = new FileManipulator(path, "context", Extension.xml);
        file.create();
    }
    
}
