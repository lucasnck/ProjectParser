/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.ger;

import projectparser.acc.Expressions;
import projectparser.acc.Statements;
import projectparser.acc.Types;
import japa.parser.ast.type.Type;
import projectparser.res.Method;
import projectparser.res.Parameter;
import projectparser.res.Unit;

/**
 *
 * @author lucas
 */
public class GenericDAOConstructor {
    
    private Unit unit;

    public GenericDAOConstructor(Unit unit) {
        this.unit = unit;
    }
    
    public static Parameter T() {
        return new Parameter("obj", new Types().classOrInterface("T"));
    }

    public static Method create() {
        Method method = new Method("create", new Types()._void());
        method.Parameters().add(T());
        method.Body().add(new Statements()
                .Try()
                ._try(new Statements()
                        .Block()
                        .addStatement(new Statements()
                                .expression(new Expressions()
                                        .name("this.session.save(obj)"))))
                .addCause(new Statements().Try()
                        .Cause()
                        ._catch(new Statements()
                                .Block()
                                .addStatement(new Statements().expression(new Expressions()
                                        .fieldAccess()
                                        .expression(new Expressions().name("ex"))
                                        .field("printStackTrace()")
                                        .end()
                                ))
                        )
                        .parameter(new Parameter("ex", new Types()
                                .classOrInterface("Exception"))))
                .end());
        return method;
    }

    public static Method delete() {
        Method method = new Method("delete", new Types()._void());
        method.Parameters().add(T());
        
        method.Body().add(new Statements().expression(new Expressions()
                .variableDeclaration().type(new Types()
                        .classOrInterface("Object"))
                .variables()
                .add()
                .name("merged")
                .init(new Expressions().nullLiteral())
                .end()
                .end()
                .end()));
        method.Body().add(new Statements()
                .Try()
                ._try(new Statements()
                        .Block()
                        .addStatement(new Statements()
                                .expression(new Expressions()
                                        .name("merged = this.session.merge(obj)"))))
                .addCause(new Statements().Try()
                        .Cause()
                        ._catch(new Statements()
                                .Block()
                                .addStatement(new Statements().expression(new Expressions()
                                        .fieldAccess()
                                        .expression(new Expressions().name("ex"))
                                        .field("printStackTrace()")
                                        .end()
                                ))
                        )
                        .parameter(new Parameter("ex", new Types()
                                .classOrInterface("Exception"))))
                .end());
        method.Body().add(new Statements()
                .expression(new Expressions()
                        .name("this.session.delete(merged)")));
        
        return method;
    }

    public static Method update() {
        Method method = new Method("update", new Types()._void());
        method.Parameters().add(T());
        method.Body().add(new Statements()
                .Try()
                ._try(new Statements()
                        .Block()
                        .addStatement(new Statements()
                                .expression(new Expressions()
                                        .name("this.session.merge(obj)"))))
                .addCause(new Statements().Try()
                        .Cause()
                        ._catch(new Statements()
                                .Block()
                                .addStatement(new Statements().expression(new Expressions()
                                        .fieldAccess()
                                        .expression(new Expressions().name("ex"))
                                        .field("printStackTrace()")
                                        .end()
                                ))
                        )
                        .parameter(new Parameter("ex", new Types()
                                .classOrInterface("Exception"))))
                .end());
        return method;
    }

    public Method list() {
        Method method = new Method("list", new ListConstructor(unit).generateListType("T"));
        method.Parameters().add(T());
        method.Body().add(
                new Statements()
                    .expression(new Expressions().name("Criteria c = this.session.createCriteria(obj.getClass())"))
        );
        method.Body().add(new Statements()._return(new Expressions().name("(List<T>) c.list()")));
        return method;
    }

    public static Method getByID() {
        Method method = new Method("getByID", new Types().classOrInterface("T"));
        method.Parameters().add(new Parameter("_class", new Types().classOrInterface("Class")));
        method.Parameters().add(new Parameter("id", new Types().classOrInterface("Long")));
        method.Body().add(new Statements()._return(new Expressions().name("(T) getSession().get(_class, id)")));
        return method;
    }
}
