package compiler.translate;

public abstract class Frame
{
    public Label name;
    public AccessList formals = null;

    public abstract Frame newFrame(Label name, BoolList formals);
    public abstract Access allocLocal(boolean escape);
}
