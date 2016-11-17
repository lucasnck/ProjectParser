/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.ger;

import doc.XML;
import element.Tag;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import node.Node;
import parser.Parser;

/**
 *
 * @author Lucas
 */
public class HibernateConstructor {

    private final XML document;

    public HibernateConstructor(XML document) {
        this.document = document;
        if (document.getRoot().getEncoding() == null) {
            document.getRoot().createEncoding();
        }
        if (document.getRoot().getDoctype() == null) {
            createDOCTYPE();
        }
    }

    public static HibernateConstructor create() {
        XML doc = (XML) Parser.xml(null);
        HibernateConstructor h = new HibernateConstructor(doc);
        return h;
    }

    public void createDOCTYPE() {
        document.getRoot().createDOCTYPE();
        document.getRoot().getDoctype().setContent("<!DOCTYPE hibernate-configuration PUBLIC \"-//Hibernate/Hibernate Configuration DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd\">");
    }

    public Tag createHibernateConfiguration() {
        Tag tag = null;
        if (getHibernateConfiguration() == null) {
            tag = new Tag("hibernate-configuration", document.getRoot());
        }
        return tag;
    }

    public Tag createSessionFactory() {
        Tag tag = null;
        if (getSessionFactory() == null) {
            tag = new Tag("session-factory", getHibernateConfiguration());
        }
        return tag;
    }

    public void add(Property property) {
        getSessionFactory().add(property.getInTagFormat());
    }

    public void add(Tag tag) {
        getSessionFactory().add(tag);
    }

    public Tag getHibernateConfiguration() {
        Tag tag = null;
        if (document.getRoot().getChildrens() != null) {
            for (Node node : document.getRoot().getChildrens()) {
                if (node instanceof Tag) {
                    if (((Tag) node).getName().equals("hibernate-configuration")) {
                        tag = (Tag) node;
                        break;
                    }
                }
            }
        }
        return tag;
    }

    public Tag getSessionFactory() {
        Tag tag = null;
        if (getHibernateConfiguration().getChildrens() != null) {
            for (Node node : getHibernateConfiguration().getChildrens()) {
                if (node instanceof Tag) {
                    if (((Tag) node).getName().equals("session-factory")) {
                        tag = (Tag) node;
                        break;
                    }
                }
            }
        }
        return tag;
    }
    
    public boolean hasMapping(String value) {
        boolean has = false;
        for (Tag tag : getMapping()) {
            if (tag.getAttributes().get(0).getValue().equals(value)) {
                has = true;
            }
        }
        return has;
    }

    public List<Tag> getProperties() {
        List<Tag> properties = new ArrayList<>();
        for (Node node : getSessionFactory().getChildrens()) {
            if (node instanceof Tag) {
                if (((Tag) node).getName().equals("property")) {
                    properties.add((Tag) node);
                }
            }
        }
        return properties;
    }

    public List<Tag> getMapping() {
        List<Tag> mapping = new ArrayList<>();
        for (Node node : getSessionFactory().getChildrens()) {
            if (node instanceof Tag) {
                if (((Tag) node).getName().equals("mapping")) {
                    mapping.add((Tag) node);
                }
            }
        }
        return mapping;
    }

    public static class Property {

        private final Tag tag;

        private final Type type;

        public Property(Type type, String value) {
            tag = new Tag("property", null);
            tag.add(new data.Attribute("name", ""));
            tag.add(new element.Element(value));
            this.type = type;
        }

        public enum Type {

            connection_driver_class, connection_url, connection_username, connection_password, connection_pool_size, connection_datasource,
            jndi_url, jndi_class,
            hbm2ddl_auto, current_session_context_class, dialect, format_sql;
        }

        public Tag getInTagFormat() {
            if (null != type) {
                switch (type) {
                    case connection_datasource:
                        tag.getAttributes().get(0).setName("hibernate.connection.datasource");
                        break;
                    case connection_driver_class:
                        tag.getAttributes().get(0).setName("hibernate.connection.driver_class");
                        break;
                    case connection_url:
                        tag.getAttributes().get(0).setName("hibernate.connection.url");
                        break;
                    case connection_username:
                        tag.getAttributes().get(0).setName("hibernate.connection.username");
                        break;
                    case connection_password:
                        tag.getAttributes().get(0).setName("hibernate.connection.password");
                        break;
                    case hbm2ddl_auto:
                        tag.getAttributes().get(0).setName("hibernate.hbm2ddl.auto");
                        break;
                    case current_session_context_class:
                        tag.getAttributes().get(0).setName("hibernate.current_session_context_class");
                        break;
                    case dialect:
                        tag.getAttributes().get(0).setName("hibernate.dialect");
                        break;
                    case format_sql:
                        tag.getAttributes().get(0).setName("hibernate.format_sql");
                        break;
                    default:
                        break;
                }
            }
            return this.tag;
        }

    }

    public XML getDocument() {
        return document;
    }
    
    @Override
    public String toString() {
        return document.toString();
    }

}
