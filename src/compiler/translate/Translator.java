package compiler.translate;

import compiler.ast.*;
import compiler.ast.BinaryExpr.Operator;
import java.util.*;
import compiler.semantic.*;

public class Translator
{
    private Program prog;
    private Table vscope, env, tag_env;
    private int offset;
    private Stack<Integer> offset_info;
    private Stack<Label> begin_label, end_label;
    private Label exit;
    private Temp fp, sp, zero, a0, v0, ra;
    private LinkedList<Quad> global_var_init;

    private static void panic(String msg) throws Exception
    {
        throw new Exception(msg);
    }

    public Translator(Program x, Table var_info, Table record_info)
    {
        prog = x;
        vscope = new Table();
        env = var_info;
        tag_env = record_info;
        offset = 0;
        offset_info = new Stack<Integer>();
        exit = new Label();
        global_var_init = new LinkedList<Quad>();
    }

    public void translate() throws Exception
    {
        translate(prog);
    }

    private void translate(Program x) throws Exception
    {
        while (x != null)
        {
            if (x.head instanceof Declaration)
                translate((Declaration) x.head);
            else
                translate((FuncDef) x.head);

            x = x.next;
        }
    }

    // Global declaration
    private void translate(Declaration x) throws Exception
    {
        if (x.init_declarator_list == null)
            return;

        InitDeclaratorList y = x.init_declarator_list;
        while (y != null)
        {
            InitDeclarator z = y.head;

            //Allocate space
            String vn = z.declarator.plain_declarator.identifier;
            Symbol csym = Symbol.getSymbol(vn);
            VarEntry ve = (VarEntry) env.get(csym);
            ve.offset = offset;
            offset += ve.size;
            offset_info.push(ve.offset);
            
            //Handle initialization
            //Note that global initializer must be constant, which helps a lot.
            //I guess this is the reason why they set this rule.
            if(z.initializer!=null)
            {
                Initializer w = z.initializer;
                if(w.expr!=null)
                {
                    Oprand val = new CONST((int)w.expr.value);
                    Oprand dst = new MEM(ve.offset);
                    Quad iq = new MOVE(val, dst);
                    global_var_init.add(iq);
                }
                else
                {
                    //TODO
                    //Recursive check
                }
            }
            
            y = y.next;
        }
    }

    private void translate(FuncDef x) throws Exception
    {
        String fn = x.func_name.identifier;
        Symbol csym = Symbol.getSymbol(fn);
        FuncEntry fe = (FuncEntry) env.get(csym);

        // denote the each function with a label
        Label fl = new Label(fn);
        fe.code = new LinkedList<Quad>();
        fe.code.add(new LabelQuad(fl));

        // At present, ignore parameters
        // translate(x.params);

        // handle local declarations
        DeclarationList dl = x.comp_stmt.declaration_list;
        while (dl != null)
        {
            Declaration cvd = dl.head;
            if (cvd.init_declarator_list != null)
            {
                Type ct = cvd.type_specifier.detail;
                InitDeclaratorList vidl = cvd.init_declarator_list;
                while (vidl != null)
                {
                    //At present, ignore initializer
                    fe.offset.add(getVarSize(ct, vidl.head.declarator));
                    vidl = vidl.next;
                }
            }
            dl = dl.next;
        }

        translate(x.comp_stmt);
    }

    private int getVarSize(Type t, Declarator x)
    {        
        int ret = x.plain_declarator.star_list.cnt != 0 ? 4 : t.size;
        Iterator<Expr> it = x.dimension.iterator();
        while(it.hasNext())
        {
            Expr e = it.next();
            ret *= (int)e.value;
        }

        return ret;
    }

    private void translate(CompoundStmt x) throws Exception
    {
        translate(x.stmt_list);
    }

    private void translate(StmtList x) throws Exception
    {
        while (x != null)
        {
            translate(x.head);
            x = x.next;
        }
    }

    private void translate(Stmt x) throws Exception
    {
        if (x instanceof ExpressionStmt)
            translate((ExpressionStmt) x);
        else if (x instanceof CompoundStmt)
        {
            vscope.beginScope();
            translate((CompoundStmt) x);
            vscope.endScope();
        }
        else if (x instanceof SelectionStmt)
            translate((SelectionStmt) x);
        else if (x instanceof JumpStmt)
            translate((JumpStmt) x);
        else if (x instanceof IterationStmt)
            translate((IterationStmt) x);
        else
            panic("Internal Error!");
    }

    private void translate(ExpressionStmt x) throws Exception
    {
        if (x.e != null)
            translate(x.e);
    }

    private void translate(SelectionStmt x) throws Exception
    {
        QuadList ret = null;

        Label l1 = new Label();
        Label l2 = new Label();
        Oprand e = translate(x.cond);

        Branch b = new Branch(Operator.EQ, e, new Const(0), l2);
        QuadList ife = translate(x.if_clause);
        Jump j = new Jump(l1);
        QuadList ese = translate(x.else_clause);

        code.add(b);
        QuadList tmp = ife;
        while (tmp != null)
        {
            code.add(tmp.head);
            tmp = tmp.next;
        }

        code.add(j);
        code.add(new LabelQuad(l2));
        tmp = ese;
        while (ese != null)
        {
            code.add(tmp.head);
            tmp = tmp.next;
        }
        code.add(new LabelQuad(l1));
    }

    private void translate(JumpStmt x) throws Exception
    {
        switch (x.type)
        {
        case CONTINUE:
            code.add(new Jump(begin_label.peek()));
            begin_label.pop();
            break;
        case BREAK:
            code.add(new Jump(end_label.peek()));
            end_label.pop();
            break;
        case RETURN:
            Temp ret_val = translate(x.expr);
            code.add(new Return(new TempOprand(ret_val)));
            break;
        default:
            panic("Internal Error!");
        }
    }

    private void translate(IterationStmt x) throws Exception
    {
        switch (x.iteration_type)
        {
        case WHILE:
            translate_while_stmt(x);
            break;
        case FOR:
            translate_for_stmt(x);
            break;
        default:
            panic("Internal Error!");
        }
    }

    private void translate_while_stmt(IterationStmt x) throws Exception
    {
        Label beginwhile = new Label();
        Label endwhile = new Label();

        Temp judge = translate(x.judge);
        TempOprand judge_oprand = new TempOprand(judge);
        QuadList ql = translate(x.stmt);

        code.add(new LabelQuad(beginwhile));
        code.add(new Branch(Operator.EQ, judge_oprand, new Const(0), endwhile));
        while (ql != null)
        {
            code.add(ql.head);
            ql = ql.next;
        }
        code.add(new LabelQuad(endwhile));
    }

    private void translate_for_stmt(IterationStmt x) throws Exception
    {

    }

    private void translate(Expr x) throws Exception
    {
        if (x instanceof PrimaryExpr)
            translate((PrimaryExpr) x);
        else if (x instanceof PostfixExpr)
            translate((PostfixExpr) x);
        else if (x instanceof UnaryExpr)
            translate((UnaryExpr) x);
        else if (x instanceof CastExpr)
            translate((CastExpr) x);
        else if (x instanceof BinaryExpr)
            translate((BinaryExpr) x);
        else if (x instanceof AssignmentExpr)
            translate((AssignmentExpr) x);
        else if (x instanceof Expression)
            translate((Expression) x);
        else
            panic("Internal Error!");
    }

    private Quad translate(PrimaryExpr x) throws Exception
    {
        switch (x.elem_type)
        {
        case INT:
            return new Const((int) x.elem);
        case CHAR:
            return new Const((int) x.elem);
        case STRING:
            return new Label((String) x.elem);
        case ID:
            return new Label((String) x.elem);
        case PAREN:
            translate((Expression) x.elem);
            break;
        default:
            panic("Internal Error!");
        }
    }

    private Quad translate(PostfixExpr x) throws Exception
    {
        switch (x.op)
        {
        case MPAREN:
            Temp index = new Temp();
            break;
        }
    }

    private Quad translate(BinaryExpr x) throws Exception
    {
        Oprand left = translate(x.left);
        Oprand right = translate(x.right);
        TempOprand ans = new TempOprand(new Temp());

        return new BinOp(x.op, left, right, ans);
    }

    private void translate(Expression x) throws Exception
    {
        while (x != null)
        {
            translate(x.head);
            x = x.next;
        }
    }
}
