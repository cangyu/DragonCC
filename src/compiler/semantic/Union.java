package compiler.semantic;

import java.util.LinkedList;

public final class Union extends Record 
{
	public Union(String _name, LinkedList<RecordField> _field)
	{
		super(_name, _field);
	}
}
