package compiler.ast;

public class TypeName 
{
	public TypeSpecifier type_specifier;
	public Star stars;
	
	public TypeName(TypeSpecifier _ts, Star _ss)
	{
		type_specifier = _ts;
		stars = _ss;
	}
}
