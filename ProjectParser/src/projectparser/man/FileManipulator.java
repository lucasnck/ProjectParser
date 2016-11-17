/*
 * Created by Lucasnck 
 *
 * use(this);
 * for( int i = 0; i <= whatYouWant, i++ ) { 
 *      whatYouWant[i].setPublic(true); 
 * } 
 *
 */
package projectparser.man;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectparser.util.Extension;
import projectparser.util.Path;

/**
 *
 * @author xp
 */
public class FileManipulator {

    /* required */
    private Path path;
    private String name;
    private Extension extension;

    /* product */
    private File file;

    /* Constructors */
    public FileManipulator(Path path, String name, Extension extension) {
        setPath(path);
        setName(name);
        setExtension(extension);
    }

    public FileManipulator(Path path) {
        setName(path.getNameWithoutExtension());
        setExtension(path.getExtension());
        setPath(new Path(path.getFile().getParent()));
    }

    public FileManipulator() {
    }

    public static void copy(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        System.out.println("coping: " + source);
        System.out.println("to: " + dest);
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    /* Methods */
    public void create() {
        setFile(new File(getPath().toString()));
        if (!getFile().exists()) {
            try {
                getFile().createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void delete(Path path) {
        File file = new File(path.toString());
        file.delete();
    }

    private String unionPathNameExtension(Path path, String name, Extension extension) {
        Path p = path;
        String pa = p.toString();
        pa += File.separator;
        pa += name;
        if (extension != null) {
            pa = pa + extension.toString();
        }
        return pa;
    }

    public String readFileAsString() {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(getPath().toString()));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileData.toString();
    }

    public static String readFileAsString(Path path) {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path.toString()));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileData.toString();
    }

    public class Update {

        private String text;

        public Update text(String text) {
            this.text = text;
            return this;
        }

        public FileManipulator end() {
//            try (FileWriter writer = new FileWriter(getFile())) {
//                writer.write(text);
//            } catch (IOException ex) {
//                Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return FileManipulator.this;

            try {
                FileOutputStream fos = new FileOutputStream(getFile().getAbsolutePath());
                Writer out = new OutputStreamWriter(fos, "UTF8");
                out.write(text);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return FileManipulator.this;
        }
    }

    public boolean exists() {
        return getFile().exists();
    }

    /* Getters & Setters */
    public File getFile() {
        file = new File(getPath().toString());
        file.setWritable(true);
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Path getPath() {
        if (path.getName().equals(name + extension)) {
            return path;
        } else {
            return new Path(unionPathNameExtension(path, name, extension));
        }
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* Subclass call */
    public Update Update() {
        return new Update();
    }

}
