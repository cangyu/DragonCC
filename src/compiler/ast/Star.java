package compiler.ast;

public class Star 
{
	public Integer cnt;
	
	public Star(int _ic)
	{
		cnt = _ic;
	}
	
	@Override
	public String toString()
	{
		String ans = "";
		
		for(int i = 0;i < cnt; i++)
			ans += "*";
		
		return ans;
	}
}
