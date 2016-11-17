/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.res;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.NameExpr;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import projectparser.man.FileManipulator;
import projectparser.util.Extension;
import projectparser.util.Path;

/**
 *
 * @author Lucas
 */
public class Unit {

    private projectparser.res.Package pack;

    private CompilationUnit unit;

    private Path path;

    private String name;

    private Unit() {
    }

    public static void createFile(Unit unit) {
        FileManipulator file = new FileManipulator(unit.getPath());
        file.create();
        file.Update().text(unit.getUnit().toString()).end();
    }

    public static Unit create(String name, Path packagePath) {
        Unit unit = new Unit();
        unit.setUnit(new CompilationUnit());
        unit.setName(name);
        unit.setPath(new Path(packagePath.toString() + File.separator + name + Extension.java));
        unit.setPack(projectparser.res.Package.load(packagePath));
        return unit;
    }

    public static Unit load(Path path) {
        Unit unit = new Unit();
        unit.setUnit(Unit.load(new FileManipulator(path)));
        unit.setName(path.getNameWithoutExtension());
        unit.setPath(path);
        unit.setPack(projectparser.res.Package.load(path.getParentPath()));
        return unit;
    }

    public void delete() {
        FileManipulator.delete(getPath());
    }

    public void update() {
        if (getPath() != null) {
            FileManipulator file = new FileManipulator(getPath());
            file.Update().text(unit.toString()).end();
        }
    }

    public static CompilationUnit load(FileManipulator file) {
        FileInputStream input;
        CompilationUnit unit;
        try {
            input = new FileInputStream(file.getFile());
            unit = JavaParser.parse(input);
            input.close();
        } catch (Exception | ClassFormatError ex) {
            return null;
        }
        return unit;
    }

    public void add(String _import) {
        if (unit.getImports() == null) {
            unit.setImports(new ArrayList<ImportDeclaration>());
        }
        boolean has = false;
        if(unit.getImports().size() > 0) {
            for(ImportDeclaration i : unit.getImports()) {
                if(i.getName().toString().equals(_import)) {
                    has = true;
                }
            }
        }
        if(!has) {
            unit.getImports().add(new ImportDeclaration(new NameExpr(_import), false, false));
        }
        update();
    }

    public void addToBody(UnitMember member) {
        if (unit.getTypes() == null) {
            unit.setTypes(new ArrayList<TypeDeclaration>());
        }
        unit.getTypes().add(member.getTypeDeclaration());
        update();
    }

    public void addComment(Comment comment) {
        if (unit.getComments() == null) {
            unit.setComments(new ArrayList<japa.parser.ast.Comment>());
        }
        unit.getComments().add(comment.getComment());
        update();
    }

    @Override
    public String toString() {
        return unit.toString();
    }

    public List<Import> getImports() {
        List<Import> imports = new ArrayList<>();
        if (unit.getImports() != null) {
            for (ImportDeclaration i : unit.getImports()) {
                imports.add(new Import(i));
            }
        }
        return imports;
    }

    public List<UnitMember> getMembers() {
        List<UnitMember> members = new ArrayList<UnitMember>();
        if (unit.getTypes() != null) {
            for (TypeDeclaration t : unit.getTypes()) {
                members.add(UnitMember.load(t, Unit.this));
            }
            return members;
        }
        return members;
    }

    public List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        if (unit.getComments() != null) {
            for (japa.parser.ast.Comment c : unit.getComments()) {
                comments.add(new Comment(c));
            }
        }
        return comments;
    }

    public projectparser.res.Package getPack() {
        return pack;
    }

    public void setPack(projectparser.res.Package pack) {
        unit.setPackage(pack.getPackageDeclaration());
        this.pack = pack;
    }

    public CompilationUnit getUnit() {
        FileInputStream input;
        CompilationUnit unit;
        try {
            input = new FileInputStream(new FileManipulator(path).getFile());
            unit = JavaParser.parse(input);
            input.close();
        } catch (Exception | ClassFormatError ex) {
            return null;
        }
        return unit;
    }

    public void setUnit(CompilationUnit unit) {
        this.unit = unit;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
