/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.app;

import doc.XML;
import element.Tag;
import java.io.File;
import java.text.AttributedCharacterIterator.Attribute;
import javax.lang.model.element.Element;
import projectparser.man.FileManipulator;
import projectparser.man.FolderManipulator;
import parser.Parser;
import projectparser.res.Project;
import projectparser.util.Extension;
import projectparser.util.Path;

/**
 *
 * @author Lucas
 */
public class NetBeans {

    private final String name = "NetBeans";
    private final String version = "8.0.2";

    private final IDE ide;
    private final Project project;

    public NetBeans(IDE ide, Project project) {
        this.ide = ide;
        this.project = project;
    }

    public void init() {

        this.ide.setName(name);
        this.ide.setVersion(version);

    }

    public void create() {

        createNbproject();
        createPrivate();
        createProjectXml();
        createProjectProperties();

    }

    public void createNbproject() {
        Path nb = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "nbproject");
        FolderManipulator folder = new FolderManipulator(nb);
        folder.create();
        FileManipulator genfiles = new FileManipulator(nb, "genfiles", Extension.properties);
        genfiles.create();
        FileManipulator build_impl = new FileManipulator(nb, "build-impl", Extension.xml);
        build_impl.create();
    }

    public void createPrivate() {
        Path pvt = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "nbproject" + File.separator + "private");
        FolderManipulator folder = new FolderManipulator(pvt);
        folder.create();
        FileManipulator config = new FileManipulator(pvt, "config", Extension.properties);
        config.create();
        FileManipulator pvtpro = new FileManipulator(pvt, "private", Extension.properties);
        pvtpro.create();
        FileManipulator pvtxml = new FileManipulator(pvt, "private", Extension.xml);
        pvtxml.create();
    }

    public void createProjectProperties() {
        Path nb = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "nbproject");
        FileManipulator projpro = new FileManipulator(nb, "project", Extension.properties);
        projpro.create();

        String text = "annotation.processing.enabled=true\n"
                + "annotation.processing.enabled.in.editor=true\n"
                + "annotation.processing.processor.options=-Aeclipselink.canonicalmodel.use_static_factory=false\n"
                + "annotation.processing.processors.list=\n"
                + "annotation.processing.run.all.processors=true\n"
                + "annotation.processing.source.output=${build.generated.sources.dir}/ap-source-output\n"
                + "auxiliary.org-netbeans-modules-css-prep.less_2e_compiler_2e_options=\n"
                + "auxiliary.org-netbeans-modules-css-prep.less_2e_enabled=false\n"
                + "auxiliary.org-netbeans-modules-css-prep.less_2e_mappings=/less:/css\n"
                + "auxiliary.org-netbeans-modules-css-prep.sass_2e_compiler_2e_options=\n"
                + "auxiliary.org-netbeans-modules-css-prep.sass_2e_enabled=false\n"
                + "auxiliary.org-netbeans-modules-css-prep.sass_2e_mappings=/scss:/css\n"
                + "auxiliary.org-netbeans-modules-projectapi.jsf_2e_language=JSP\n"
                + "auxiliary.org-netbeans-modules-web-clientproject-api.js_2e_libs_2e_folder=resources/js\n"
                + "build.classes.dir=${build.web.dir}/WEB-INF/classes\n"
                + "build.classes.excludes=**/*.java,**/*.form\n"
                + "build.dir=build\n"
                + "build.generated.dir=${build.dir}/generated\n"
                + "build.generated.sources.dir=${build.dir}/generated-sources\n"
                + "build.test.classes.dir=${build.dir}/test/classes\n"
                + "build.test.results.dir=${build.dir}/test/results\n"
                + "build.web.dir=${build.dir}/web\n"
                + "build.web.excludes=${build.classes.excludes}\n"
                + "client.urlPart=\n"
                + "compile.jsps=false\n"
                + "conf.dir=${source.root}/conf\n"
                + "debug.classpath=${build.classes.dir}:${javac.classpath}\n"
                + "debug.test.classpath=\\\n"
                + "    ${run.test.classpath}\n"
                + "display.browser=true\n"
                + "# Arquivos a serem excluídos do war de distribuição\n"
                + "dist.archive.excludes=\n"
                + "dist.dir=dist\n"
                + "dist.ear.war=${dist.dir}/${war.ear.name}\n"
                + "dist.javadoc.dir=${dist.dir}/javadoc\n"
                + "dist.war=${dist.dir}/${war.name}\n"
                + "endorsed.classpath=\\\n"
                + "    ${libs.javaee-endorsed-api-6.0.classpath}\n"
                + "excludes=\n"
                + "file.reference.bootstrap.jar=web/WEB-INF/lib/bootstrap.jar\n"
                + "file.reference.derbyclient.jar=web/WEB-INF/lib/derbyclient.jar\n"
                + "file.reference.primefaces-5.3.jar=web/WEB-INF/lib/primefaces-5.3.jar\n"
                + "includes=**\n"
                + "j2ee.compile.on.save=true\n"
                + "j2ee.copy.static.files.on.save=true\n"
                + "j2ee.deploy.on.save=true\n"
                + "j2ee.platform=1.7-web\n"
                + "j2ee.platform.classpath=${j2ee.server.home}/lib/annotations-api.jar:${j2ee.server.home}/lib/catalina-ant.jar:${j2ee.server.home}/lib/catalina-ha.jar:${j2ee.server.home}/lib/catalina-storeconfig.jar:${j2ee.server.home}/lib/catalina-tribes.jar:${j2ee.server.home}/lib/catalina.jar:${j2ee.server.home}/lib/ecj-4.4.jar:${j2ee.server.home}/lib/el-api.jar:${j2ee.server.home}/lib/jasper-el.jar:${j2ee.server.home}/lib/jasper.jar:${j2ee.server.home}/lib/jsp-api.jar:${j2ee.server.home}/lib/servlet-api.jar:${j2ee.server.home}/lib/tomcat-api.jar:${j2ee.server.home}/lib/tomcat-coyote.jar:${j2ee.server.home}/lib/tomcat-dbcp.jar:${j2ee.server.home}/lib/tomcat-i18n-es.jar:${j2ee.server.home}/lib/tomcat-i18n-fr.jar:${j2ee.server.home}/lib/tomcat-i18n-ja.jar:${j2ee.server.home}/lib/tomcat-jdbc.jar:${j2ee.server.home}/lib/tomcat-jni.jar:${j2ee.server.home}/lib/tomcat-spdy.jar:${j2ee.server.home}/lib/tomcat-util-scan.jar:${j2ee.server.home}/lib/tomcat-util.jar:${j2ee.server.home}/lib/tomcat-websocket.jar:${j2ee.server.home}/lib/websocket-api.jar\n"
                + "j2ee.server.type=Tomcat\n"
                + "jar.compress=false\n"
                + "javac.classpath=\\\n"
                + "${libs.jsf20.classpath}:\\\n"
                + "    ${libs.hibernate4-support.classpath}:\\\n"
                + "    ${libs.jpa2-persistence.classpath}:\\\n"
                + "    ${file.reference.bootstrap.jar}:\\\n"
                + "    ${file.reference.derbyclient.jar}:\\\n"
                + "    ${file.reference.primefaces-5.3.jar}:\\\n"
                + "# Space-separated list of extra javac options\n"
                + "javac.compilerargs=\n"
                + "javac.debug=true\n"
                + "javac.deprecation=false\n"
                + "javac.processorpath=\\\n"
                + "    ${javac.classpath}:\\\n"
                + "    ${libs.hibernate4-persistencemodelgen.classpath}\n"
                + "javac.source=1.8\n"
                + "javac.target=1.8\n"
                + "javac.test.classpath=\\\n"
                + "    ${javac.classpath}:\\\n"
                + "    ${build.classes.dir}\n"
                + "javac.test.processorpath=\\\n"
                + "    ${javac.test.classpath}\n"
                + "javadoc.additionalparam=\n"
                + "javadoc.author=false\n"
                + "javadoc.encoding=${source.encoding}\n"
                + "javadoc.noindex=false\n"
                + "javadoc.nonavbar=false\n"
                + "javadoc.notree=false\n"
                + "javadoc.preview=true\n"
                + "javadoc.private=false\n"
                + "javadoc.splitindex=true\n"
                + "javadoc.use=true\n"
                + "javadoc.version=false\n"
                + "javadoc.windowtitle=\n"
                + "lib.dir=${web.docbase.dir}/WEB-INF/lib\n"
                + "persistence.xml.dir=${conf.dir}\n"
                + "platform.active=default_platform\n"
                + "resource.dir=setup\n"
                + "run.test.classpath=\\\n"
                + "    ${javac.test.classpath}:\\\n"
                + "    ${build.test.classes.dir}\n"
                + "# Space-separated list of JVM arguments used when running a class with a main method or a unit test\n"
                + "# (you may also define separate properties like run-sys-prop.name=value instead of -Dname=value):\n"
                + "runmain.jvmargs=\n"
                + "source.encoding=UTF-8\n"
                + "source.root=src\n"
                + "src.dir=${source.root}/java\n"
                + "test.src.dir=test\n"
                + "war.content.additional=\n"
                + "war.ear.name=${war.name}\n"
                + "war.name="+project.getName()+".war\n"
                + "web.docbase.dir=web\n"
                + "webinf.dir=web/WEB-INF\n";

        projpro.Update().text(text).end();
    }

    public void createProjectXml() {
        Path nb = new Path(project.getLocal() + File.separator + project.getName() + File.separator + "nbproject");
        FileManipulator projxml = new FileManipulator(nb, "project", Extension.xml);
        projxml.create();

        XML doc = (XML) Parser.xml(null);
        doc.getRoot().createEncoding();

        Tag projectTag = new Tag("project", doc.getRoot());
        projectTag.add(new data.Attribute("xmlns", "http://www.netbeans.org/ns/project/1"));
        Tag type = new Tag("type", projectTag);
        type.add(new element.Element("org.netbeans.modules.web.project"));
        Tag configuration = new Tag("configuration", projectTag);
        Tag data = new Tag("data", configuration);
        data.add(new data.Attribute("xmlns", "http://www.netbeans.org/ns/web-project/3"));
        Tag projectName = new Tag("name", data);
        projectName.add(new element.Element(project.getName()));

        Tag minimum_ant_version = new Tag("minimum-ant-version", data);
        minimum_ant_version.add(new element.Element("1.6.5"));

        Tag web_module_libraries = new Tag("web-module-libraries", data);

        Tag library = new Tag("library", web_module_libraries);
        library.add(new data.Attribute("dirs", "200"));
        Tag file = new Tag("file", library);
        file.add(new element.Element("${libs.jsf20.classpath}"));
        Tag path_in_war = new Tag("path-in-war", library);
        path_in_war.add(new element.Element("WEB-INF/lib"));

        library = new Tag("library", web_module_libraries);
        library.add(new data.Attribute("dirs", "200"));
        file = new Tag("file", library);
        file.add(new element.Element("${libs.hibernate4-support.classpath}"));
        path_in_war = new Tag("path-in-war", library);
        path_in_war.add(new element.Element("WEB-INF/lib"));

        library = new Tag("library", web_module_libraries);
        library.add(new data.Attribute("dirs", "200"));
        file = new Tag("file", library);
        file.add(new element.Element("${libs.jpa2-persistence.classpath}"));
        path_in_war = new Tag("path-in-war", library);
        path_in_war.add(new element.Element("WEB-INF/lib"));

        library = new Tag("library", web_module_libraries);
        library.add(new data.Attribute("dirs", "200"));
        file = new Tag("file", library);
        file.add(new element.Element("${file.reference.bootstrap.jar}"));
        path_in_war = new Tag("path-in-war", library);
        path_in_war.add(new element.Element("WEB-INF/lib"));

        library = new Tag("library", web_module_libraries);
        library.add(new data.Attribute("dirs", "200"));
        file = new Tag("file", library);
        file.add(new element.Element("${file.reference.derbyclient.jar}"));
        path_in_war = new Tag("path-in-war", library);
        path_in_war.add(new element.Element("WEB-INF/lib"));

        library = new Tag("library", web_module_libraries);
        library.add(new data.Attribute("dirs", "200"));
        file = new Tag("file", library);
        file.add(new element.Element("${file.reference.primefaces-5.3.jar}"));
        path_in_war = new Tag("path-in-war", library);
        path_in_war.add(new element.Element("WEB-INF/lib"));

        Tag web_module_additional_libraries = new Tag("web-module-additional-libraries", data);
        Tag source_roots = new Tag("source-roots", data);
        Tag root = new Tag("root", source_roots);
        root.add(new data.Attribute("id", "src.dir"));
        Tag test_roots = new Tag("test-roots", data);

        projxml.Update().text(doc.toString()).end();
    }

}
