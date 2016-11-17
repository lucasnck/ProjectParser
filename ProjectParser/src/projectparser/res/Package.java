/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.res;

import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.expr.NameExpr;
import java.util.ArrayList;
import java.util.List;
import projectparser.man.FileManipulator;
import projectparser.man.FolderManipulator;
import projectparser.util.Extension;
import projectparser.util.Path;

/**
 *
 * @author xp
 */
public class Package {
    
    private PackageDeclaration packageDeclaration;
    
    private Path path;
    
    private String name;

    private Package() {
    }

    public Package(String name) {
        packageDeclaration = new PackageDeclaration();
        setName(name);
    }

    public Package(String name, Path path) {
        packageDeclaration = new PackageDeclaration();
        setName(name);
        setPath(path);
    }
    
    public static Package load(Path path) {
        FolderManipulator folder = new FolderManipulator(path);
        Package _package = new Package();
        _package.setPackageDeclaration(new PackageDeclaration());
        _package.setPath(path);
        _package.setName(path.getName());
        return _package;
    }
    
    public class Units {
        
        public Units add(Unit unit) { 
            unit.setPack(Package.this);
            FileManipulator file = new FileManipulator(getPath(), unit.getName(), Extension.java);
            file.create();
            unit.setPath(file.getPath());
            file.Update().text(unit.toString()).end();
            return this;
        }
        
        public List<Unit> list() {
            FolderManipulator folder = new FolderManipulator(getPath());
            if(folder.listFiles()!=null) {
                List<FileManipulator> files = folder.listFiles();
                List<Unit> units = new ArrayList<Unit>();
                for(FileManipulator f : files) {
                    Unit unit = Unit.load(f.getPath());
                    units.add(unit);
                }
                return units;
            }
            return null;
        }
        
    }
    
    @Override
    public String toString() {
        return packageDeclaration.toString();
    }
    
    public PackageDeclaration getPackageDeclaration() {
        return packageDeclaration;
    }

    public void setPackageDeclaration(PackageDeclaration packageDeclaration) {
        this.packageDeclaration = packageDeclaration;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getName() {
        return name = packageDeclaration.getName().toString();
    }

    public void setName(String name) {
        packageDeclaration.setName(new NameExpr(name));
        this.name = name;
    }
    
    public Units Units() {
        return new Units();
    }
}
