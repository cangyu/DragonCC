package compiler.ast;

public abstract class ASTNode
{
	public String [] ast_rep;
	public String [] code_rep;
	
    public abstract void accept(ASTNodeVisitor v);
    
    public String toString()
    {
    	String ans="";
    	
    	if(ast_rep!=null)
    	{
    		int n = ast_rep.length;
    		for(int i=0;i<n;i++)
    		{
    			ans+=ast_rep[i];
    			ans+="\n";
    		}
    	}
    	
    	return ans;
    }
}
