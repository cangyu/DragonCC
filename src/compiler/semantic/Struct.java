package compiler.semantic;

import java.util.LinkedList;

public final class Struct extends Record 
{
	public Struct(String _name, LinkedList<RecordField> _field)
	{
		super(_name, _field);
	}
}
