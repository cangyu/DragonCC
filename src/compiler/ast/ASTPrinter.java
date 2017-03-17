package compiler.ast;

public class ASTPrinter implements ASTNodeVisitor
{
    public int cur_depth;
    public int cur_line, cur_colum;

    public StringBuilder sbuf;

    public ASTPrinter()
    {
        cur_depth = 0;
        cur_line = 0;
        cur_colum = 0;

        sbuf = new StringBuilder();
    }

    /* Expr */
    public void visit(Expression exp)
    {
    }

    public void visit(AssignmentExpr ae)
    {
        /*
        String opl;
        switch (ae.op)
        {
        case ASSIGN:
            opl = "=";
        case MUL_ASSIGN:
            opl = "*=";
        case DIV_ASSIGN:
            opl = "/=";
        case MOD_ASSIGN:
            opl = "%=";
        case ADD_ASSIGN:
            opl = "+=";
        case SUB_ASSIGN:
            opl = "-=";
        case SHL_ASSIGN:
            opl = "<<=";
        case SHR_ASSIGN:
            opl = ">>=";
        case AND_ASSIGN:
            opl = "&=";
        case XOR_ASSIGN:
            opl = "^=";
        case OR_ASSIGN:
            opl = "|=";
        default:
            opl = "";
        }

        System.out.print(opl + " ");
        ae.left.accept(this);
        System.out.print(" ");
        ae.right.accept(this);
        */
    }

    public void visit(BinaryExpr be)
    {
        /*
        String opl;
        switch (be.op)
        {
        case BIT_AND:
            opl = "&";
        case BIT_XOR:
            opl = "^";
        case BIT_OR:
            opl = "|";
        case AND:
            opl = "&&";
        case OR:
            opl = "||";
        case EQ:
            opl = "==";
        case NE:
            opl = "!=";
        case LT:
            opl = "<";
        case GT:
            opl = ">";
        case LE:
            opl = "<=";
        case GE:
            opl = ">=";
        case SHL:
            opl = "<<";
        case SHR:
            opl = ">>";
        case PLUS:
            opl = "+";
        case MINUS:
            opl = "-";
        case TIMES:
            opl = "*";
        case DIVIDE:
            opl = "/";
        case MODULE:
            opl = "%";
        default:
            opl = "";
        }

        System.out.print(opl + " ");
        be.left.accept(this);
        System.out.print(" ");
        be.right.accept(this);
        */
    }

    public void visit(CastExpr ce)
    {
        /*
        System.out.print(" (");
        ce.type_name.accept(this);
        System.out.print(") ");
        ce.expr.accept(this);
        */
    }

    public void visit(UnaryExpr ue)
    {
        /*
        switch(operation_type)
        {
        case BIT_AND:
            return "&" + expr.toString();
        case STAR:
            return "*" + expr.toString();
        case POSITIVE:
            return "+" + expr.toString();
        case NEGATIVE:
            return "-" + expr.toString();
        case BIT_NOT:
            return "~" + expr.toString();
        case NOT:
            return "!" + expr.toString();
        case SIZEOF:
            return "SIZEOF" + (type_name == null ? expr.toString() : type_name.toString());
        case INC:
            return "++" + expr.toString();
        case DEC:
            return "--" + expr.toString();
        default:
            return "";
        }
        */
    }

    public void visit(PostfixExpr pe)
    {
        /*
        switch(op)
        {
        case MPAREN:
            return expr.toString() + "[" + ((Expr)param).toString() + "]";
        case PAREN:
            return expr.toString() + "(" + (param==null ? "" : ((Expr)param).toString()) + ")";
        case DOT:
            return expr.toString() + "." + (String)param;
        case PTR:
            return expr.toString() + "->" + (String)param;
        case INC:
            return expr.toString() + "++";
        case DEC:
            return expr.toString() + "--";
        default:
            return "";
        }
        */
    }

    public void visit(PrimaryExpr pe)
    {
        /*
         *      switch(elem_type)
        {
        case ID:
            return (String)elem;
        case STRING:
            return "\"" + (String)elem + "\"";
        case INT:
            return ((Integer)elem).toString();
        case CHAR:
            return "\'" + (String)elem + "\'";
        case PAREN:
            return '(' + ((Expression)elem).toString() + ')';
        default:
            return "";
        }*/
    }

    /* Stmt */
    public void visit(Stmt s)
    {
    }

    public void visit(StmtList _sl)
    {
    }

    public void visit(ExpressionStmt es)
    {
    }

    public void visit(CompoundStmt cs)
    {
    }

    public void visit(SelectionStmt ss)
    {
    }

    public void visit(JumpStmt js)
    {
    }

    public void visit(IterationStmt is)
    {
    }

    /* Decl */
    public void visit(StarList _p)
    {
        /*
        String ans = "";
        
        for(int i = 0;i < cnt; i++)
            ans += "*";
        
        return ans;
        */
    }

    public void visit(Declaration decl)
    {
    }

    public void visit(Declarator d)
    {
    }

    public void visit(DeclarationList ds)
    {
    }

    public void visit(DeclaratorList ds)
    {
    }

    public void visit(InitDeclarator id)
    {
    }

    public void visit(InitDeclaratorList ids)
    {
    }

    public void visit(Initializer ini)
    {
    }

    public void visit(InitializerList ir)
    {
    }

    public void visit(NonInitDeclaration nid)
    {
    }

    public void visit(NonInitDeclarationList nids)
    {
    }

    public void visit(PlainDeclaration pd)
    {
    }

    public void visit(PlainDeclarator pdr)
    {
    }

    /* Func */
    public void visit(FuncDef func)
    {
    }

    public void visit(ArgumentList args)
    {
        /*
        System.out.print(" ");
        args.arg.accept(this);
        args.next.accept(this);
        */
    }

    public void visit(ParameterList pl)
    {
    }

    /* Type */
    public void visit(TypeName t)
    {
    }

    public void visit(TypeSpecifier ts)
    {
    }

    /* Program */
    public void visit(Program prog)
    {
    }

    public void visit(ProgramComp pc)
    {
    }
}
