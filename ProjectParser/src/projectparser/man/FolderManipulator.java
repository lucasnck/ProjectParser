/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.man;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import projectparser.util.Extension;
import projectparser.util.Path;

/**
 *
 * @author Lucas
 */
public class FolderManipulator {

    /* required */
    private Path path;

    /* product */
    private File folder;

    /* Constructors */
    public FolderManipulator(Path path) {
        setPath(path);
    }
    
    public FolderManipulator() {
    }

    /* Methods */
    public void create() {
        if (!getFolder().exists() && !getFolder().isFile()) {
            getFolder().mkdirs();
        }
    }

    public static void delete(Path path) {
        File file = new File(path.toString());
        if (file.isDirectory()) {
            //directory is empty, then delete it
            if (file.list().length == 0) {
                file.delete();
            } else {
                //list all the directory contents
                File files[] = file.listFiles();
                for (File temp : files) {
                    //construct the file structure
                    File fileDelete = new File(temp.getPath());
                    //recursive delete
                    delete(new Path(temp.getPath()));
                }
                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                }
            }
        } else {
            //if file, then delete it
            file.delete();
        }
    }
    
    public static void deleteFilesOfFolder(Path path) {
        File file = new File(path.toString());
        if (file.isDirectory()) {
                //list all the directory contents
                File files[] = file.listFiles();
                for (File temp : files) {
                    //construct the file structure
                    File fileDelete = new File(temp.getPath());
                    //recursive delete
                    delete(new Path(temp.getPath()));
                }
        }
    }

    public class Up {

        private List<String> folders = new ArrayList<String>();

        /**
         *
         * @param name The folder name that you want to up.
         * @return {@link low.FolderManipulator.Methods.Up} The link for Up.
         */
        public Up addFolder(String name) {
            this.folders.add(name);
            return this;
        }

        public Up addFolders(List<String> names) {
            for (String name : names) {
                this.folders.add(name);
            }
            return this;
        }

        public FolderManipulator end() {
            Path pathToUp = getPath();
            for (String folder : folders) {
                pathToUp.addFolder(folder);
            }
            setPath(pathToUp);
            getFolder().mkdirs();
            return FolderManipulator.this;
        }
    }

    public List<FolderManipulator> listSubFolders() {
        if (getFolder().listFiles() != null) {
            if (getFolder().listFiles().length != 0) {
                List<FolderManipulator> folders = new ArrayList<FolderManipulator>();
                for (File file : getFolder().listFiles()) {
                    FolderManipulator f = new FolderManipulator(new Path(file.getPath()));
                    if (f.getPath().isDirectory()) {
                        folders.add(f);
                    } else if (f.getPath().getExtension() == null) {
                        folders.add(f);
                    }
                }
                return folders;
            }
        }
        return null;
    }
    
    public List<Path> list() {
        if (getFolder().listFiles() != null) {
            if (getFolder().listFiles().length != 0) {
                List<Path> paths = new ArrayList<Path>();
                for (File file : getFolder().listFiles()) {
                    paths.add(new Path(file.getPath()));
                }
                return paths;
            }
        }
        return null;
    }
    
    public List<FileManipulator> listFiles() {
        if (getFolder().listFiles() != null) {
            if (getFolder().listFiles().length != 0) {
                List<FileManipulator> files = new ArrayList<FileManipulator>();
                for (File file : getFolder().listFiles()) {
                       Path path = new Path(file.getPath());
                       files.add(new FileManipulator(path, path.getNameWithoutExtension(), path.getExtension()));
                }
                return files;
            }
        }
        return null;
    }

    /* Getters & Setters */
    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public File getFolder() {
        return folder = new File(path.toString());
    }

    /* Subclass call */
    public Up up() {
        return new Up();
    }

}
