package compiler.translate;

public class QuadList
{
    public Quad head;
    public QuadList next;
    
    public QuadList(Quad _quad, QuadList _next)
    {
        head = _quad;
        next = _next;
    }
}
