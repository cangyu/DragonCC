package compiler.ast;

public class PlainDeclarator
{
	public Star stars;
	public String id;
	
	public PlainDeclarator(Star _s, String _id)
	{
		stars = _s;
		id = _id;
	}
}
