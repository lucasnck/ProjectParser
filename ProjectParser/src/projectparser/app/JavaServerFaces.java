/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.app;

import projectparser.acc.Types;
import doc.XHTML;
import element.Comment;
import element.Tag;
import java.io.File;
import projectparser.man.FileManipulator;
import projectparser.man.FolderManipulator;
import projectparser.res.Project;
import projectparser.util.Extension;
import projectparser.util.Path;
import projectparser.ger.ConstructorBuilder;
import projectparser.ger.GenericDAOConstructor;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.ModifierSet;
import java.io.IOException;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parser.Parser;
import projectparser.res.Field;
import projectparser.res.Unit;
import projectparser.res.UnitMember;

/**
 *
 * @author Lucas
 */
public class JavaServerFaces {

    private final String name = "JavaServerFaces";
    private final String version = "2.2";

    private final Framework framework;
    private final Project project;

    public JavaServerFaces(Framework framework, Project project) {
        this.framework = framework;
        this.project = project;
    }

    public void init() {

        this.framework.setName(name);
        this.framework.setVersion(version);

    }

    public void create() {
        createEntitiesPackage();
        createDAOPackage();
        createBeansPackage();
        createFiltersPackage();
        createUtilPackage();
        createWebHome();
        createGenericDAO();
        createDAOFactory();
        generateWEB();
        generateContext();
        copyFiles();
        createMessage();
        createUpdate();
    }

    public void generateContext() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "web" + File.separator + "META-INF" + File.separator + "context.xml");
        FileManipulator file = new FileManipulator(path);
        file.create();

        String txt = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<Context antiJARLocking=\"true\" path=\"/" + project.getName() + "\"/>";

        file.Update().text(txt).end();
    }

    public void generateWEB() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "web" + File.separator + "WEB-INF" + File.separator + "web.xml");
        FileManipulator file = new FileManipulator(path);
        file.create();

        String txt = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<web-app version=\"3.1\" \n"
                + "         xmlns=\"http://xmlns.jcp.org/xml/ns/javaee\" \n"
                + "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n"
                + "         xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd\">\n"
                + "    <!--Project Config-->\n"
                + "    <context-param>\n"
                + "        <param-name>javax.faces.PROJECT_STAGE</param-name>\n"
                + "        <param-value>Development</param-value>\n"
                + "    </context-param>\n"
                + "    <!--Primeface-->\n"
                + "    <context-param>\n"
                + "        <param-name>primefaces.THEME</param-name>\n"
                + "        <!--<param-value>none</param-value>-->\n"
                + "        <param-value>bootstrap</param-value>\n"
                + "    </context-param>\n"
                + "    <context-param>\n"
                + "        <param-name>primefaces.FONT_AWESOME</param-name>\n"
                + "        <param-value>true</param-value>\n"
                + "    </context-param>\n"
                + "    <!--JSF Config-->\n"
                + "    <servlet>\n"
                + "        <servlet-name>Faces Servlet</servlet-name>\n"
                + "        <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>\n"
                + "        <load-on-startup>1</load-on-startup>\n"
                + "    </servlet>\n"
                + "    <servlet-mapping>\n"
                + "        <servlet-name>Faces Servlet</servlet-name>\n"
                + "        <url-pattern>*.xhtml</url-pattern>\n"
                + "    </servlet-mapping>\n"
                + "    <session-config>\n"
                + "        <session-timeout>\n"
                + "            -1\n"
                + "        </session-timeout>\n"
                + "    </session-config>\n"
                + "    <!--HOME-->\n"
                + "    <welcome-file-list>\n"
                + "        <welcome-file>home.xhtml</welcome-file>\n"
                + "    </welcome-file-list>"
                + "</web-app>";

        file.Update().text(txt).end();
    }

    public void copyFiles() {

        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "web" + File.separator + "WEB-INF" + File.separator + "lib");
        path.getFile().mkdir();

        File primegaces = new File("L:\\Projects\\LIBRARIES\\Primefaces\\primefaces-5.3.jar");
        File derby = new File("L:\\Projects\\LIBRARIES\\derbyclient.jar");
        File bootstrap = new File("L:\\Projects\\LIBRARIES\\Primefaces\\bootstrap.jar");

        File primefacesDest = new File(path.toString() + File.separator + "primefaces-5.3.jar");
        File derbyDest = new File(path.toString() + File.separator + "derbyclient.jar");
        File boostrapDest = new File(path.toString() + File.separator + "bootstrap.jar");

        try {
            FileManipulator.copy(primegaces, primefacesDest);
            FileManipulator.copy(derby, derbyDest);
            FileManipulator.copy(bootstrap, boostrapDest);
        } catch (IOException ex) {
            Logger.getLogger(JavaServerFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Package getEntity() {
        return null;
    }

    public Package getManagedBean() {
        return null;
    }

    public Package getDAO() {
        return null;
    }

    public void createWebHome() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "web");
        FileManipulator home = new FileManipulator(path, "home", Extension.xhtml);
        XHTML doc = (XHTML) Parser.xhtml(null);
        doc.getRoot().createEncoding();
        doc.getRoot().createDOCTYPE();
        doc.createHtml();
        doc.getHeader();
        doc.getHeader().add(new Comment().value("the header tag is necessary for primefaces"));
        doc.getHtml().add(new data.Attribute("xmlns:p","http://primefaces.org/ui"));
        Tag body = new Tag("h:body", doc.getHtml());
        Tag tag = new Tag("h:outputLabel", body);
        tag.add(new data.Attribute("value", "welcome to " + project.getName() + "!"));
        home.Update().text(doc.toString()).end();
    }

    public void createEntitiesPackage() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "entities");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
    }

    public void createDAOPackage() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "DAO");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
    }

    public void createBeansPackage() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "beans");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
    }

    public void createFiltersPackage() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "filters");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
    }

    public void createUtilPackage() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "util");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
    }

    public void createGenericDAO() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "DAO");
        Unit unit = Unit.create("GenericDAO", path);
        Unit.createFile(unit);
        UnitMember member = UnitMember.create(unit, "GenericDAO");
        member.setModifier(ModifierSet.addModifier(ModifierSet.PUBLIC, ModifierSet.ABSTRACT));
        List<TypeParameter> parameters = new ArrayList<>();
        parameters.add(new TypeParameter("T", null));
        member.getClassDeclaration().setTypeParameters(parameters);
        member.add(new Field("session", new Types().classOrInterface("Session")));
        unit.add(("org.hibernate.Session"));
        unit.add(("org.hibernate.Criteria"));
        member.generateGetterAndSetter(member.getFields().get(0));
        member.add(ConstructorBuilder.withFieldsAssignments("GenericDAO", member.getFields()));
        member.add(ConstructorBuilder._default("GenericDAO"));
        member.add(GenericDAOConstructor.create());
        member.add(GenericDAOConstructor.delete());
        member.add(GenericDAOConstructor.getByID());
        member.add(new GenericDAOConstructor(unit).list());
        member.add(GenericDAOConstructor.update());
    }

    public void createDAOFactory() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "DAO");
        Unit unit = Unit.create("DAOFactory", path);
        Unit.createFile(unit);
        unit.add(("hibernate.HibernateUtil"));
        UnitMember member = UnitMember.create(unit, "DAOFactory");
    }

    public void createMessage() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "util");
        FileManipulator file = new FileManipulator(path, "Message", Extension.java);
        file.create();

        String txt = ""
                + "package util;\n"
                + "\n"
                + "import javax.faces.application.FacesMessage;\n"
                + "import javax.faces.context.FacesContext;\n"
                + "\n"
                + "/**\n"
                + " *\n"
                + " * @author Lucas\n"
                + " */\n"
                + "public class Message {\n"
                + "    \n"
                + "    public static void msg(String title, String message){\n"
                + "        FacesContext context = FacesContext.getCurrentInstance();\n"
                + "        FacesMessage fm;\n"
                + "        fm = new FacesMessage(FacesMessage.SEVERITY_INFO,title,message);\n"
                + "        context.addMessage(null, fm);\n"
                + "    }\n"
                + "}";

        file.Update().text(txt).end();
    }

    public void createUpdate() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "util");
        FileManipulator file = new FileManipulator(path, "Update", Extension.java);
        file.create();

        String txt = "package util;\n"
                + "\n"
                + "import java.io.IOException;\n"
                + "import java.util.logging.Level;\n"
                + "import java.util.logging.Logger;\n"
                + "import javax.faces.context.FacesContext;\n"
                + "import org.primefaces.context.RequestContext;\n"
                + "\n"
                + "/**\n"
                + " *\n"
                + " * @author Lucas\n"
                + " */\n"
                + "public class Update {\n"
                + "    \n"
                + "    public static void UpdateAForm(String form) {\n"
                + "        RequestContext context = RequestContext.getCurrentInstance();\n"
                + "        context.update(form);\n"
                + "    }\n"
                + "    \n"
                + "    public static void ExecuteByWidgetVar(String widgetVar) {\n"
                + "        RequestContext context = RequestContext.getCurrentInstance();\n"
                + "        context.execute(\"PF('\"+widgetVar+\"').show();\");\n"
                + "    }\n"
                + "    \n"
                + "    public static void Redirect(String page){\n"
                + "        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();\n"
                + "        try {\n"
                + "            FacesContext.getCurrentInstance().getExternalContext().redirect(url + \"/\" + page);\n"
                + "        } catch (IOException ex) {\n"
                + "            Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);\n"
                + "        }\n"
                + "    }\n"
                + "}";

        file.Update().text(txt).end();
    }
}
