/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.res;

import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import java.util.ArrayList;
import java.util.List;
import projectparser.acc.Expressions;

/**
 *
 * @author Lucas
 */
public class Annotation {

    private AnnotationExpr annotationExpr;
    private String name;
    private List<MemberValuePair> pairs = new ArrayList<>();
    private Expression expr;

    public Annotation(String name) {
        setName(name);
    }

    public Annotation create() {
        if (expr != null) {
            annotationExpr = new SingleMemberAnnotationExpr(new NameExpr(name), expr);
        } else if (pairs != null) {
            annotationExpr = new NormalAnnotationExpr(new NameExpr(name), pairs);
        } else {
            annotationExpr = new MarkerAnnotationExpr(new NameExpr(name));
        }
        return this;
    }

    public void update() {
        if ((pairs.isEmpty() || pairs == null) && expr == null) {
            annotationExpr = new MarkerAnnotationExpr(new NameExpr(name));
        } else if (pairs != null && pairs.isEmpty()) {
            annotationExpr = new SingleMemberAnnotationExpr(new NameExpr(name), expr);
        } else if (expr == null) {
            annotationExpr = new NormalAnnotationExpr(new NameExpr(name), pairs);
        }

        setAnnotationExpr(annotationExpr);
    }
    
    public Annotation add(MemberValuePair pair) {
        pairs.add(pair);
        return this;
    }

    public class Members {

        public Annotation end() {
            return Annotation.this;
        }

        public Add add() {
            return new Add();
        }

        public class Add {

            public Values values() {
                return new Values();
            }

            Expression addPairExpr;
            MemberValuePair member = new MemberValuePair();

            public Members addPair() {
                member.setValue(addPairExpr);
                pairs.add(member);
                return Members.this;
            }

            public Members addValue() {
                expr = addPairExpr;
                return Members.this;
            }

            public Add name(String name) {
                member.setName(name);
                return this;
            }

            public class Values {

                Expression valueExpr = null;
                Expressions expressions = null;

                public Add end() {
                    valueExpr = expressions.getValueExpr();
                    addPairExpr = valueExpr;
                    return Add.this;
                }

                public Values select(Expressions e) {
                    expressions = e;
                    return this;
                }
            }
        }
    }

    @Override
    public String toString() {
        return annotationExpr.toString();
    }

    public AnnotationExpr getAnnotationExpr() {
        return annotationExpr;
    }

    public void setAnnotationExpr(AnnotationExpr annotationExpr) {
        if (annotationExpr instanceof MarkerAnnotationExpr) {
            MarkerAnnotationExpr a = new MarkerAnnotationExpr(annotationExpr.getName());
            setName(a.getName().toString());
            this.annotationExpr = a;
        } else if (annotationExpr instanceof SingleMemberAnnotationExpr) {
            SingleMemberAnnotationExpr a = new SingleMemberAnnotationExpr(annotationExpr.getName(), (Expression) annotationExpr.getData());
            setName(a.getName().toString());
            setExpr(a.getMemberValue());
            this.annotationExpr = a;
        } else if(annotationExpr instanceof NormalAnnotationExpr) {
            NormalAnnotationExpr a = new NormalAnnotationExpr(annotationExpr.getName(), (List<MemberValuePair>) annotationExpr.getData());
            setName(a.getName().toString());
            setPairs(a.getPairs());
            this.annotationExpr = a;
        } else {
            setName(annotationExpr.getName().toString());
            this.annotationExpr = annotationExpr;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MemberValuePair> getPairs() {
        if (annotationExpr instanceof NormalAnnotationExpr) {
            return pairs = ((NormalAnnotationExpr) annotationExpr).getPairs();
        } else {
            return null;
        }
    }
    
    public Expression getPair() {
        if (annotationExpr instanceof SingleMemberAnnotationExpr) {
            return ((SingleMemberAnnotationExpr) annotationExpr).getMemberValue();
        } else {
            return null;
        }
    }

    public void setPairs(List<MemberValuePair> pairs) {
        this.pairs = pairs;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public Members members() {
        return new Members();
    }
}
