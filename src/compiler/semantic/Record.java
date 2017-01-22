package compiler.semantic;

import java.util.LinkedList;

public abstract class Record extends Type 
{
	class RecordField
	{
		Type type;
		String name;
	}
	
	LinkedList<RecordField> fields;
}
