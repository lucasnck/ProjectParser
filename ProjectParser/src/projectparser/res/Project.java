/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.res;

import projectparser.app.Application;
import projectparser.app.ConfigurationFile;
import projectparser.app.Framework;
import projectparser.app.IDE;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectparser.man.FileManipulator;
import projectparser.man.FolderManipulator;
import projectparser.util.Path;

/**
 *
 * @author Lucas
 */
public class Project {

    private static final Logger LOG = Logger.getLogger(Path.class.getName()); //.log(Level.SEVERE, "msg", ex);

    /* required */
    private String name;
    private Path local;
    private Application application;

    /* product */
    private Date creation;
    private Path path;

    /* default */
    private String version = "1";

    /* application */
    private String author;

    private Path packagesPath;
    private Path libPath;

    /* optional */
    private List<Framework> frameworks;
    private IDE IDE;

    /* Constructors */
    public Project(String name, Path local, Class<?> application) {
        setName(name);
        setLocal(local);
        setApplication(application);
    }

    /* Constructors */
    public Project(String name, Path local) {
        setName(name);
        setLocal(local);
    }

    public Project() {
    }

    /* Methods */
    public void create() {

        setCreation(projectparser.util.Date.getCurrentDate());
        if (author == null) {
            author = "";
        }

        Applications().create();
        if (IDE != null) {
            IDES().create();
        }
        if (frameworks != null) {
            Frameworks().create();
        }

        ConfigurationFile configuration = new ConfigurationFile(this);
        configuration.create();
    }

    public void update() {

        IDES().create();
        Frameworks().create();

        ConfigurationFile configuration = new ConfigurationFile(this);
        configuration.create();

    }

    public Project load(String name, Path local) {
        Project project = new Project(name, local);
        project.setName(name);
        project.setLocal(local);
        ConfigurationFile configuration = new ConfigurationFile(project);
        configuration.load();
        return project;
    }

    public static Project loadLocal(String name, Path local) {
        Project project = new Project(name, local);
        project.setName(name);
        project.setLocal(local);
        ConfigurationFile configuration = new ConfigurationFile(project);
        configuration.load();
        return project;
    }

    public List<FolderManipulator> list() {
        FolderManipulator folder = new FolderManipulator(getPath());
        if (folder.listSubFolders() != null) {
            if (folder.listSubFolders().size() > 0) {
                List<FolderManipulator> folders = new ArrayList<FolderManipulator>();
                for (FolderManipulator f : folder.listSubFolders()) {
                    folders.add(f);
                }
                return folders;
            }
        }
        return null;
    }

    public List<FileManipulator> listFiles() {
        FolderManipulator folder = new FolderManipulator(getPath());
        if (folder.listFiles() != null) {
            if (folder.listFiles().size() > 0) {
                List<FileManipulator> files = new ArrayList<FileManipulator>();
                for (FileManipulator f : folder.listFiles()) {
                    files.add(f);
                }
                return files;
            }
        }
        return null;
    }

    public List<Package> listPackages() {
        return null;
    }

    public List listLibraries() {
        return null;
    }

    public List<FileManipulator> listConfigurationFiles() {
        return null;
    }

    @Override
    public String toString() {
        if (getPackagesPath() != null && getLibPath() != null) {
            return "Project{" + "name=" + getName()
                    + ", local=" + getLocal().toString()
                    + ", path=" + getPath().toString()
                    + ", application=" + application.getName()
                    + ", packagesPath=" + getPackagesPath().toString()
                    + ", libPath=" + getLibPath().toString()
                    + '}';
        } else {
            return "Project{" + "name=" + getName()
                    + ", local=" + getLocal().toString()
                    + ", path=" + getPath().toString()
                    + ", application=" + application.getName()
                    + '}';
        }
    }

    /* Subclasses */
    public class Frameworks {

        public Frameworks add(Class<?> framework) {
            if (frameworks == null) {
                frameworks = new ArrayList<Framework>();
            }
            Framework frame = new Framework(framework, Project.this);
            frameworks.add(frame);
            frame.init();
            return this;
        }

        public Frameworks addByName(String name) {
            if (frameworks == null) {
                frameworks = new ArrayList<Framework>();
            }
            try {
                frameworks.add(new Framework(Class.forName(name), Project.this));
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, name, ex);
            }
            return this;
        }

        public Frameworks load() {
            for (Framework framework : getFrameworks()) {
                framework.load();
            }
            return this;
        }

        public Frameworks create() {
            for (Framework framework : getFrameworks()) {
                framework.create();
            }
            return this;
        }
    }

    public class Applications {

        public Applications load() {
            getApplication().load();
            return this;
        }

        public Applications create() {
            getApplication().create();
            return this;
        }

        public Applications addByName(String name) {
            try {
                application = new Application(Class.forName(name), Project.this);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, name, ex);
            }
            return this;
        }
    }

    public class IDES {

        public IDES load() {
            getIDE().load();
            return this;
        }

        public IDES create() {
            getIDE().create();
            return this;
        }

        public IDES addByName(String name) {
            try {
                IDE = new IDE(Class.forName(name), Project.this);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, name, ex);
            }
            return this;
        }
    }

    public class Packages {

        public Packages add(Package pack) {
            pack.setPath(new Path(getAbsolutePackagesPath().toString() + File.separator + pack.getPackageDeclaration().getName()));
            FolderManipulator folder = new FolderManipulator(pack.getPath());
            folder.create();
            return Packages();
        }

        public List<Package> list() {
            FolderManipulator packagesFolder = new FolderManipulator(getAbsolutePackagesPath());
            if (packagesFolder.listSubFolders() != null) {
                if (packagesFolder.listSubFolders().size() > 0) {
                    List<Package> packages = new ArrayList<Package>();
                    for (FolderManipulator folder : packagesFolder.listSubFolders()) {
                        Package pack = new Package(folder.getPath().getName());
                        pack.setPath(folder.getPath());
                        packages.add(pack);
                    }
                    return packages;
                }
            }
            return null;
        }

        public Package getByName(String name) {
            for (Package pack : list()) {
                if (pack.getPackageDeclaration().getName().toString().equals(name)) {
                    return pack;
                }
            }
            return null;
        }

    }

    /* Getters & Setters */
    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public Path getLocal() {
        return local;
    }

    public final void setLocal(Path local) {
        this.local = local;
    }

    public Application getApplication() {
        return application;
    }

    public final void setApplication(Class<?> application) {
        Application app = new Application(application, this);
        app.init();
        this.application = app;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Path getPath() {
        Path path = new Path(getLocal().toString() + File.separator + getName());
        return this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Path getPackagesPath() {
        return packagesPath;
    }

    public Path getAbsolutePackagesPath() {
        return new Path(getPath() + File.separator + packagesPath);
    }

    public void setPackagesPath(Path packagesPath) {
        this.packagesPath = packagesPath;
    }

    public Path getLibPath() {
        return libPath;
    }

    public Path getAbsoluteLibPath() {
        return new Path(getPath() + File.separator + libPath);
    }

    public void setLibPath(Path lib) {
        this.libPath = lib;
    }

    public List<Framework> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(List<Framework> frameworks) {
        for (Framework f : frameworks) {
            f.init();
        }
        this.frameworks = frameworks;
    }

    public IDE getIDE() {
        return IDE;
    }

    public void setIDE(Class<?> IDE) {
        IDE ide = new IDE(IDE, this);
        ide.init();
        this.IDE = ide;
    }

    /* Subclass call */
    public Frameworks Frameworks() {
        return new Frameworks();
    }

    public Applications Applications() {
        return new Applications();
    }

    public IDES IDES() {
        return new IDES();
    }

    public Packages Packages() {
        return new Packages();
    }
}
