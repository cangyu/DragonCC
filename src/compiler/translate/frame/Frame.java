package compiler.translate.frame;

import compiler.translate.temp.Label;

public abstract class Frame
{
	public Label name;
	public AccessList formals = null;

	public abstract Frame newFrame(Label name, BoolList formals);
	public abstract Access allocLocal(boolean escape);
}
