/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectparser.acc;

import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.AssertStmt;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.Type;
import java.util.ArrayList;
import java.util.List;
import projectparser.res.Parameter;
import projectparser.res.UnitMember;

/**
 *
 * @author xp
 */
public class Statements {

    private Statement statement = null;

    public Statement getStatement() {
        return statement;
    }

    public Statements setStatement(Statement statement) {
        this.statement = statement;
        return this;
    }

    public Assert Assert() {
        return new Assert();
    }

    public class Assert {

        private Expressions check;
        private Expressions msg;

        public Statements end() {
            if (msg != null) {
                statement = new AssertStmt(check.getValueExpr());
            } else {
                statement = new AssertStmt(check.getValueExpr(), msg.getValueExpr());
            }
            return Statements.this;
        }

        public Assert check(Expressions expr) {
            check = expr;
            return this;
        }

        public Assert msg(Expressions expr) {
            msg = expr;
            return this;
        }
    }

    public Statements _break(String id) {
        statement = new BreakStmt(id);
        return this;
    }

    public Statements _continue(String id) {
        statement = new ContinueStmt(id);
        return this;
    }

    public Do Do() {
        return new Do();
    }

    public class Do {

        private Statements body;
        private Expressions condition;

        public Statements end() {
            statement = new DoStmt(body.getStatement(), condition.getValueExpr());
            return Statements.this;
        }

        public Do _do(Statements _do) {
            body = _do;
            return this;
        }

        public Do condition(Expressions expr) {
            condition = expr;
            return this;
        }
    }

    public Statements empty() {
        statement = new EmptyStmt();
        return this;
    }

    public ExplicitConstructorInvocation ExplicitConstructorInvocation() {
        return new ExplicitConstructorInvocation();
    }

    public class ExplicitConstructorInvocation {

        private boolean isThis = false;
        private Statements body;
        private Expressions expr;
        private List<Expression> args;

        public Statements end() {
            statement = new ExplicitConstructorInvocationStmt(isThis, expr.getValueExpr(), args);
            return Statements.this;
        }

        public ExplicitConstructorInvocation isThis(boolean is) {
            isThis = is;
            return this;
        }

        public ExplicitConstructorInvocation expr(Expressions expr) {
            this.expr = expr;
            return this;
        }

        public ExplicitConstructorInvocation addArg(Expressions expr) {
            if (args == null) {
                args = new ArrayList<>();
            }
            args.add(expr.getValueExpr());
            return this;
        }
    }

    public Statements expression(Expressions expr) {
        statement = new ExpressionStmt(expr.getValueExpr());
        return this;
    }
    
    public Statements expression(Expression expr) {
        statement = new ExpressionStmt(expr);
        return this;
    }

    public For For() {
        return new For();
    }

    public class For {

        private Statements body;
        private Expressions var;
        private List<Expression> init;
        private List<Expression> update;

        public Statements end() {
            statement = new ForStmt(init, var.getValueExpr(), update, body.getStatement());
            return Statements.this;
        }

        public For body(Statements b) {
            body = b;
            return this;
        }

        public For compare(Expressions comp) {
            this.var = comp;
            return this;
        }

        public For addInit(Expressions expr) {
            if (init == null) {
                init = new ArrayList<>();
            }
            init.add(expr.getValueExpr());
            return this;
        }

        public For addUpdate(Expressions expr) {
            if (update == null) {
                update = new ArrayList<>();
            }
            update.add(expr.getValueExpr());
            return this;
        }
    }

    public Foreach Foreach() {
        return new Foreach();
    }

    public class Foreach {

        private Statements body;
        private Expressions iterable;
        private VariableDeclarationExpr var;

        public Statements end() {
            statement = new ForeachStmt(var, iterable.getValueExpr(), body.getStatement());
            return Statements.this;
        }

        public Foreach iterable(Expressions expr) {
            iterable = expr;
            return this;
        }

        public Foreach body(Statements b) {
            body = b;
            return this;
        }

        public Foreach variable(Expressions.VariableDeclaration variable) {
            this.var = variable.getVariable();
            return this;
        }
    }

    public If If() {
        return new If();
    }

    public class If {
        
        private IfStmt stmt = new IfStmt();
                
        private Statements then = null;
        private Statements _else = null;
        private Expressions condition;
        

        public Statements end() {
            statement = stmt;
            return Statements.this;
        }

        public If condition(Expressions expr) {
            condition = expr;
            stmt.setCondition(condition.getValueExpr());
            return this;
        }

        public If then(Statements _then) {
            then = _then;
            stmt.setThenStmt(then.getStatement());
            return this;
        }

        public If _else(Statements _elsestmt) {
            _else = _elsestmt;
            stmt.setElseStmt(_else.getStatement());
            return this;
        }
    }

    public Labeled Labeled() {
        return new Labeled();
    }

    public class Labeled {

        private Statements stmt;
        private String label;

        public Statements end() {
            statement = new LabeledStmt(label, stmt.getStatement());
            return Statements.this;
        }

        public Labeled label(String name) {
            label = name;
            return this;
        }

        public Labeled statement(Statements statement) {
            stmt = statement;
            return this;
        }
    }

    public Statements _return(Expressions expr) {
        statement = new ReturnStmt(expr.getValueExpr());
        return this;
    }

    public Switch Switch() {
        return new Switch();
    }

    public class Switch {

        private List<Statement> stmts;
        private Expressions label;

        public Statements end() {
            statement = new SwitchEntryStmt(label.getValueExpr(), stmts);
            return Statements.this;
        }

        public Switch label(Expressions expression) {
            label = expression;
            return this;
        }

        public Switch addStatement(Statements statement) {
            if (stmts == null) {
                stmts = new ArrayList<>();
            }
            stmts.add(statement.getStatement());
            return this;
        }
    }

    public Block Block() {
        return new Block();
    }

    public class Block {

        private List<Statement> stmts = new ArrayList<>();
        private BlockStmt block = new BlockStmt(stmts);

        public Statements end() {
            statement = block;
            return Statements.this;
        }

        public Block addStatement(Statements statement) {
            if (stmts == null) {
                stmts = new ArrayList<>();
            }
            stmts.add(statement.getStatement());
            return this;
        }

        public BlockStmt getBlock() {
            return block;
        }

        public void setBlock(BlockStmt block) {
            this.block = block;
        }
    }

    public Synchronized Synchronized() {
        return new Synchronized();
    }

    public class Synchronized {

        private BlockStmt block;
        private Expressions expr;

        public Statements end() {
            statement = new SynchronizedStmt(expr.getValueExpr(), block);
            return Statements.this;
        }

        public Synchronized expr(Expressions expression) {
            expr = expression;
            return this;
        }

        public Synchronized addStatement(Statements statement) {
            block = new BlockStmt();
            if (block.getStmts() == null) {
                block.setStmts(new ArrayList<Statement>());
            }
            block.getStmts().add(statement.getStatement());
            return this;
        }

        public Synchronized block(Statements.Block b) {
            block = b.getBlock();
            return this;
        }
    }

    public Statements _throw(Expressions expr) {
        statement = new ThrowStmt(expr.getValueExpr());
        return this;
    }

    public Statements unitTypeMember(UnitMember member) {
        statement = new TypeDeclarationStmt(member.getTypeDeclaration());
        return this;
    }
    
    public Try Try() {
        return new Try();
    }
    
    public Statements Try(Statements.Try try_) {
        statement = try_.stmt;
        return this;
    }

    public class Try {

        private BlockStmt tryBlock;
        private BlockStmt finallyBlock;
        private List<CatchClause> causes;
        private TryStmt stmt = new TryStmt();

        public Statements end() {
            if(causes != null)
                stmt.setCatchs(causes);
            statement = stmt;
            return Statements.this;
        }

        public Try addCause(Cause cause) {
            if (causes == null) {
                causes = new ArrayList<>();
            }
            causes.add(cause.getCause());
            return this;
        }

        public Try _try(Statements.Block b) {
            tryBlock = b.getBlock();
            stmt.setTryBlock(tryBlock);
            return this;
        }

        public Try _finally(Statements.Block b) {
            finallyBlock = b.getBlock();
            stmt.setFinallyBlock(finallyBlock);
            return this;
        }

        public Cause Cause() {
            return new Cause();
        }

        public class Cause {

            private BlockStmt catchBlock;
            private Parameter parameter;
            private CatchClause cause = new CatchClause();

            public Try end() {
                return Try.this;
            }
            
            public Cause close() {
                return this;
            }
            
            public Cause parameter(Parameter param) {
                parameter = param;
                cause.setExcept(parameter.getParameter());
                return this;
            }

            public Cause _catch(Statements.Block b) {
                catchBlock = b.getBlock();
                cause.setCatchBlock(catchBlock);
                return this;
            }

            public CatchClause getCause() {
                return cause;
            }

            public void setCause(CatchClause cause) {
                this.cause = cause;
            }
            
        }
    }
    
    public While While() {
        return new While();
    }

    public class While {

        private Statements stmt;
        private Expressions condition;

        public Statements end() {
            statement = new WhileStmt(condition.getValueExpr(),stmt.getStatement());
            return Statements.this;
        }

        public While statement(Statements body) {
            stmt = body;
            return this;
        }
        
        public While condition(Expressions conditional) {
            condition = conditional;
            return this;
        }
    }
}
