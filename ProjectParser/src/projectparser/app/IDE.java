/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectparser.res.Project;

/**
 *
 * @author Lucas
 */
public class IDE<T> {

    private String name;
    private String version;
    
    private Project project;
    
    private final Class<T> clazz;
    private Object object;

    public IDE(Class<T> clazz, Project project) {
        this.clazz = clazz;
        this.project = project;
        init();
    }
    
    public IDE(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    public final void init() {
        
        try {
            Class[] arguments = new Class[] {IDE.class, Project.class};
            Constructor<?> constructor = clazz.getConstructor(arguments);
            object = constructor.newInstance(this, project);
            Method method = clazz.getMethod("init");
            method.invoke(object);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
    public void create() {
        
        try {
            
            Method method = clazz.getMethod("create");
            method.invoke(object);
            
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void load() {
        
    }
    
    public static Class getIDEFromName(String name) {
        Class app = null;
        try {
            app = Class.forName(name);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return app;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
}
