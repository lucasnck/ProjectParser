/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.res;

import japa.parser.ast.BlockComment;
import japa.parser.ast.LineComment;
import japa.parser.ast.body.JavadocComment;

/**
 *
 * @author Claudis
 */
public class Comment {
    
    private japa.parser.ast.Comment comment;
    
    private String content;
    
    private boolean javadoc = false;
    
    private boolean line = true;
    
    private boolean block = false;
    
    public Comment(String text) {
        comment = new LineComment(text);
        setContent(text);
    }

    public Comment(japa.parser.ast.Comment comment) {
        setComment(comment);
    }

    @Override
    public String toString() {
        return comment.toString();
    }
    
    public japa.parser.ast.Comment getComment() {
        return comment;
    }

    public void setComment(japa.parser.ast.Comment comment) {
        setContent(comment.getContent());
        if(comment instanceof JavadocComment) {
            comment = new JavadocComment();
            setLine(false);
            setJavadoc(true);
        } else if(comment instanceof LineComment) {
            comment = new LineComment();
            setLine(true);
        } else {
            comment = new BlockComment();
            setLine(false);
            setBlock(true);
        }
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        comment.setContent(content);
        this.content = content;
    }

    public boolean isJavadoc() {
        return javadoc;
    }

    public void setJavadoc(boolean javadoc) {
        setLine(false);
        comment = new JavadocComment(content);
        this.javadoc = javadoc;
    }

    public boolean isLine() {
        return line;
    }

    public void setLine(boolean line) {
        comment = new LineComment(content);
        this.line = line;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        setLine(false);
        comment = new BlockComment(content);
        this.block = block;
    }
    
}
