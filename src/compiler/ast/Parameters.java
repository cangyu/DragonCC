package compiler.ast;

import java.util.LinkedList;

public class Parameters 
{
	public LinkedList<PlainDeclaration> plain_declaration_list;
	
	public Parameters(PlainDeclaration _pdecl)
	{
		plain_declaration_list=new LinkedList<PlainDeclaration>();
		plain_declaration_list.add(_pdecl);
	}
}
