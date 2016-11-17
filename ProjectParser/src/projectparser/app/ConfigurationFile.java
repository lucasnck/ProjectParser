/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.app;

import java.util.logging.Level;
import java.util.logging.Logger;
import projectparser.man.FileManipulator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import projectparser.res.Project;
import projectparser.util.Extension;
import projectparser.util.Path;

/**
 *
 * @author Lucas
 */
public class ConfigurationFile {
    
    private final String name = "configurationfile";
    private final Project project;

    public ConfigurationFile(Project project) {
        this.project = project;
    }
    
    public void create() {
        
        FileManipulator file = new FileManipulator(project.getPath(), name, Extension.properties);
        file.create();
        
        JSONObject projectobject = new JSONObject();
        JSONObject foldersobject = new JSONObject();
        JSONArray frameworksarray = new JSONArray();
        JSONObject container = new JSONObject();
        
        try {
            
            projectobject.put("application", "app." + project.getApplication().getName());
            if(project.getIDE()!=null)
                projectobject.put("ide", "app." + project.getIDE().getName());
            projectobject.put("name", project.getName());
            projectobject.put("author", project.getAuthor());
            projectobject.put("local", project.getLocal().toString());
            projectobject.put("path", project.getPath().toString());
            projectobject.put("creation", project.getCreation().toString());
            projectobject.put("version", project.getVersion());
            foldersobject.put("packagesPath", project.getPackagesPath().toString());
            foldersobject.put("libPath", project.getLibPath().toString());
            if(project.getFrameworks()!=null) {
                for( Framework framework : project.getFrameworks()) {
                    frameworksarray.put("app." + framework.getName());
                }
            }
            container.put("frameworks", frameworksarray);
            container.put("project", projectobject);
            container.put("folders", foldersobject);
        } catch (JSONException ex) {
            Logger.getLogger(ConfigurationFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        file.Update().text(container.toString()).end();
    }
    
    public boolean load() {
        
        FileManipulator file = new FileManipulator(project.getPath(), name, Extension.properties);
        if(file.getFile()!=null) {
            String jsonstring = file.readFileAsString();

            JSONObject container = null;

            try {
                container = new JSONObject(new JSONTokener(jsonstring));
            } catch (JSONException ex) {
                Logger.getLogger(ConfigurationFile.class.getName()).log(Level.SEVERE, null, ex);
            }

            JSONObject projectobject = null;
            JSONObject foldersobject = null;
            JSONArray frameworksarray = null;

            try {

                projectobject = container.getJSONObject("project");
                foldersobject = container.getJSONObject("folders");
                frameworksarray = container.getJSONArray("frameworks");

                project.setName(projectobject.getString("name"));
                project.setAuthor(projectobject.getString("name"));
                project.setVersion(projectobject.getString("version"));
                project.setCreation(projectparser.util.Date.getDateFromString(projectobject.getString("creation")));
                project.Applications().addByName(projectobject.getString("application"));
                if(projectobject.getString("ide")!=null)
                    project.IDES().addByName(projectobject.getString("ide"));
                if(frameworksarray!=null) {
                    for(int i = 0; i < frameworksarray.length(); i++) { 
                        project.Frameworks().addByName(frameworksarray.getString(i));
                    }
                }
                project.setPackagesPath(new Path(foldersobject.getString("packagesPath")));
                project.setLibPath(new Path(foldersobject.getString("libPath")));
                return true;
            } catch (JSONException ex) {
                Logger.getLogger(ConfigurationFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public Project getProject() {
        return project;
    }
}
