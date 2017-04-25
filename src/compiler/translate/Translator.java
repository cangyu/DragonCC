package compiler.translate;

import compiler.ast.*;
import java.util.Stack;
import compiler.semantic.Table;

public class Translator implements ASTNodeVisitor
{
	private Program prog;
	private Table vscope;
	private int offset;
	private Stack<Integer> offsets;
	private Stack<Label> breakLabels, continueLabels;
	private Label exit;
	private Temp fp, sp, zero, a0, v0, ra;
	
	private static void panic(String msg) throws Exception
	{
		throw new Exception(msg);
	}
	
	public Translator(Program x)
	{
		prog = x;
		vscope = new Table();
		offset = 0;
		offsets = new Stack<Integer>();
		exit = new Label();
	}
	
	public void translate() throws Exception
	{
		prog.accept(this);
	}
	
	@Override
	public void visit(Expression x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AssignmentExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BinaryExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CastExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(UnaryExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PostfixExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PrimaryExpr x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StmtList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ExpressionStmt x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CompoundStmt x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SelectionStmt x) throws Exception
	{
	    
	}

	@Override
	public void visit(JumpStmt x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IterationStmt x) throws Exception
	{
		if(x.iteration_type == IterationStmt.Type.WHILE)
		{
			Label begin = new Label();
			Label end = new Label();
			
			x.judge.accept(this);
			x.stmt.accept(this);
		}
		else
		{
			
		}
	}

	@Override
	public void visit(StarList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Declaration x) throws Exception
	{
	    if(x.init_declarator_list == null)
	        return;
	    
	    Label label;
	    
	    

	}

	@Override
	public void visit(Declarator x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DeclarationList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DeclaratorList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(InitDeclarator x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(InitDeclaratorList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Initializer x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(InitializerList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NonInitDeclaration x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NonInitDeclarationList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PlainDeclaration x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PlainDeclarator x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FuncDef x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArgumentList x) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ParameterList x) throws Exception
	{
	    
	}

	@Override
	public void visit(TypeName x) throws Exception
	{
	    x.type_specifier.accept(this);
	    x.star_list.accept(this);
	}

	@Override
	public void visit(TypeSpecifier x) throws Exception
	{
	    if(x.comp!=null)
	        x.comp.accept(this);
	}

	@Override
	public void visit(Program x) throws Exception
	{
		while(x!=null)
		{
			x.head.accept(this);
			x = x.next;
		}
	}

}
