package compiler.semantic;

public class Pointer extends Type 
{
	Type elem_type;
	
	public Pointer(Type _t)
	{
		elem_type = _t;
	}
}
