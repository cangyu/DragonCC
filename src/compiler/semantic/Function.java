package compiler.semantic;

import java.util.LinkedList;

public final class Function extends Type 
{
	String name;
	LinkedList<Type> args;
	Type ret_type;
	
	public Function(String _name, LinkedList<Type> _args, Type _ret)
	{
		name = _name;
		args = _args;
		ret_type = _ret;
	}
}
