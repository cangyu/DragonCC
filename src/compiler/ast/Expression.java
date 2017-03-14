package compiler.ast;

import java.util.LinkedList;

public class Expression extends Expr
{
	public LinkedList<Expr> comp;
	
	public Expression(Expr _a)
	{
		comp = new LinkedList<Expr>();
		comp.add(_a);
	}
	
	@Override
	public String toString()
	{
		String ans = "";
		
		if(comp.size() > 0)
		{
			ans += comp.get(0).toString();
			if(comp.size() > 1)
			{
				for(int i=1;i< comp.size(); i++)
					ans += (", " + comp.get(i).toString());
			}
		}
		
		return ans;
	}
}
