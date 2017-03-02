package compiler.semantic;

import java.util.LinkedList;

public abstract class Record extends Type 
{    
	public String name;
	public LinkedList<RecordField> fields;
	
	public Record(String _name, LinkedList<RecordField> _field)
	{
		name = _name;
		fields = _field;
	}
}
