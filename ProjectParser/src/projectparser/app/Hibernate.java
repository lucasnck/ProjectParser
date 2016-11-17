/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.app;

import element.Comment;
import element.Tag;
import projectparser.ger.HibernateConstructor;
import java.io.File;
import java.text.AttributedCharacterIterator.Attribute;
import javax.lang.model.element.Element;
import projectparser.man.FileManipulator;
import projectparser.man.FolderManipulator;
import projectparser.res.Project;
import projectparser.util.Extension;
import projectparser.util.Path;

/**
 *
 * @author Lucas
 */
public class Hibernate {

    private final String name = "Hibernate";
    private final String version = "4.3.1";

    private final Framework framework;
    private final Project project;

    public Hibernate(Framework framework, Project project) {
        this.framework = framework;
        this.project = project;
    }

    public void init() {

        this.framework.setName(name);
        this.framework.setVersion(version);

    }

    public void create() {
        createHibernateXml();
        createHibernateUtil();
        generateCreateTables();
        generateDBFilter();
    }

    public void createHibernateXml() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "hibernate");
        FolderManipulator folder = new FolderManipulator(path);
        folder.create();
        FileManipulator hibernate = new FileManipulator(path, "hibernate.cfg", Extension.xml);

        hibernate.create();

        HibernateConstructor cfg = HibernateConstructor.create();

        cfg.createHibernateConfiguration();
        Tag sessionFactory = cfg.createSessionFactory();

        sessionFactory.add(new Comment().value("config"));

        Tag property = new Tag("property", sessionFactory);
        property.add(new data.Attribute("name", "hibernate.dialect"));
        property.add(new element.Element("org.hibernate.dialect.DerbyDialect"));

        Tag property1 = new Tag("property", sessionFactory);
        property1.add(new data.Attribute("name", "hibernate.connection.driver_class"));
        property1.add(new element.Element("org.apache.derby.jdbc.ClientDriver"));

        sessionFactory.add(new Comment().value("conection to db"));

        Tag property2 = new Tag("property", sessionFactory);
        property2.add(new data.Attribute("name", "hibernate.connection.url"));
        property2.add(new element.Element("jdbc:derby://localhost:1527/" + project.getName() + "DB"));

        Tag property3 = new Tag("property", sessionFactory);
        property3.add(new data.Attribute("name", "hibernate.connection.username"));
        property3.add(new element.Element("app"));

        Tag property4 = new Tag("property", sessionFactory);
        property4.add(new data.Attribute("name", "hibernate.connection.password"));
        property4.add(new element.Element("app"));

        sessionFactory.add(new Comment().value("anothers"));

        Tag property5 = new Tag("property", sessionFactory);
        property5.add(new data.Attribute("name", "hibernate.hbm2ddl.auto"));
        property5.add(new element.Element("update"));

        Tag property6 = new Tag("property", sessionFactory);
        property6.add(new data.Attribute("name", "hibernate.current_session_context_class"));
        property6.add(new element.Element("thread"));

        sessionFactory.add(new Comment().value("entities"));

        hibernate.Update().text(cfg.toString()).end();
    }

    public void createHibernateUtil() {

        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "hibernate");
        FolderManipulator filters = new FolderManipulator(path);
        filters.create();
        
        FileManipulator hibernate = new FileManipulator(path, "HibernateUtil", Extension.java);
        hibernate.create();
        
        String txt = "package hibernate;\n"
                + "import org.hibernate.SessionFactory;\n"
                + "import org.hibernate.cfg.AnnotationConfiguration;\n"
                + "import org.jboss.logging.Logger;\n"
                + "public class HibernateUtil {\n"
                + "    \n"
                + "    private static final SessionFactory session = buildSessionFactory();\n"
                + "    \n"
                + "    private static SessionFactory buildSessionFactory(){\n"
                + "        \n"
                + "        try{\n"
                + "            AnnotationConfiguration cfg = new AnnotationConfiguration();\n"
                + "            cfg.configure(\"/hibernate/hibernate.cfg.xml\");\n"
                + "            return cfg.buildSessionFactory();\n"
                + "        } catch (Exception ex) {\n"
                + "            Logger.getLogger(HibernateUtil.class).log(Logger.Level.ERROR, \"Error on create Data Base, check Data Base is running.\", ex);\n"
                + "            return null;\n"
                + "        }\n"
                + "        \n"
                + "    }\n"
                + "    \n"
                + "    public static SessionFactory getSessionFactory(){\n"
                + "        return session;\n"
                + "    }\n"
                + "}";

        hibernate.Update().text(txt).end();
    }

    public void generateCreateTables() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "hibernate");
        FileManipulator hibernate = new FileManipulator(path, "CreateTables", Extension.java);

        String txt = "package hibernate;\n"
                + "import org.hibernate.cfg.AnnotationConfiguration;\n"
                + "import org.hibernate.tool.hbm2ddl.SchemaExport;\n"
                + "public class CreateTables {\n"
                + "public static void main(String args[]){\n"
                + "AnnotationConfiguration cfg = new AnnotationConfiguration();\n"
                + "        cfg.configure(\"/hibernate/hibernate.cfg.xml\");\n"
                + "        SchemaExport se = new SchemaExport(cfg);\n"
                + "        se.create(true,true);\n"
                + "}\n"
                + "}";

        hibernate.Update().text(txt).end();
    }

    public void generateDBFilter() {
        Path path = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "src" + File.separator + "java" + File.separator + "filters");
        FileManipulator hibernate = new FileManipulator(path, "DataBaseFilter", Extension.java);
        hibernate.create();
        
        String txt = "package filters;\n"
                + "import java.io.IOException;\n"
                + "import javax.servlet.Filter;\n"
                + "import javax.servlet.FilterChain;\n"
                + "import javax.servlet.FilterConfig;\n"
                + "import javax.servlet.ServletException;\n"
                + "import javax.servlet.ServletRequest;\n"
                + "import javax.servlet.ServletResponse;\n"
                + "import javax.servlet.annotation.WebFilter;\n"
                + "import org.hibernate.SessionFactory;\n"
                + "import hibernate.HibernateUtil;\n"
                + "\n"
                + "/**\n"
                + " *\n"
                + " * @author aluno\n"
                + " */\n"
                + "@WebFilter(urlPatterns = {\"*\"})\n"
                + "public class DataBaseFilter implements Filter {\n"
                + "    \n"
                + "    private SessionFactory sf;\n"
                + "    \n"
                + "    public DataBaseFilter() {\n"
                + "        \n"
                + "    }    \n"
                + "\n"
                + "    @Override\n"
                + "    public void init(FilterConfig filterConfig) throws ServletException {\n"
                + "        this.sf = HibernateUtil.getSessionFactory();\n"
                + "    }\n"
                + "\n"
                + "    @Override\n"
                + "    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {\n"
                + "        try{\n"
                + "            this.sf.getCurrentSession().beginTransaction(); //init transaction\n"
                + "            chain.doFilter(request, response); //filer the request \n"
                + "            this.sf.getCurrentSession().getTransaction().commit(); //get transaction and commit then\n"
                + "        } catch (Exception ex) {\n"
                + "            ex.printStackTrace();\n"
                + "            if(this.sf.getCurrentSession().getTransaction().isActive()){\n"
                + "                this.sf.getCurrentSession().getTransaction().rollback();\n"
                + "            }\n"
                + "        } finally {\n"
                + "            this.sf.getCurrentSession().close(); // close session\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    @Override\n"
                + "    public void destroy() {\n"
                + "        \n"
                + "    }\n"
                + "        \n"
                + "}";
        hibernate.Update().text(txt).end();
    }

}
