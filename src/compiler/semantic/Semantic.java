package compiler.semantic;

import java.util.*;
import compiler.ast.*;

public class Semantic
{
    private Table env;

    public Semantic()
    {
        env = new Table();
    }

    public void check(Object _obj) throws Exception
    {
        if(_obj instanceof Program)
            checkProgram((Program) _obj);
        else
            throw new Exception("Invalid Program!");
    }

    public void checkProgram(Program _prog) throws Exception
    {
        for (GeneralDeclaration gd : _prog.comp)
        {
            if(gd instanceof Decl)
                checkDecl((Decl) gd);
            else
                checkFuncDef((FuncDef) gd);
        }
    }

    public void checkDecl(Decl _decl) throws Exception
    {
        // get current defined type of this declaration
        Type def_type = checkTypeSpecifier(_decl.type_specifier);

        // if no init_declarators, this is just a type declaration
        if(_decl.init_declarators == null)
            return;

        // check each init_declarator
        for (InitDeclarator elem : _decl.init_declarators.comp)
        {
            // take the stars into consideration, get real type of the identifier
            Type real_type = checkDeclarator(elem.declarator, def_type);

            // get the type of the initializer, array nested in array or plain
            Type init_type = checkInitializer(elem.initializer);

            // 2 possibilities: function declaration / variable definition
            Declarator cd = elem.declarator;
            if(cd.isFunc)
            {
                // as this simplified grammar doesn't support function pointer,
                // there should be no initializer for a function identifier
                if(init_type != null)
                    throw new Exception("Can't intialize function: " + cd.plain_declarator.id);
            }
            else
            {
                // init_type must be consistent with real_type
                if(init_type != null && !real_type.isAssignable(init_type))
                    throw new Exception("Can't initialize variable: " + cd.plain_declarator.id);
            }
        }
    }

    public Type checkTypeSpecifier(TypeSpecifier _ts) throws Exception
    {
        // current type
        TypeSpecifier.Type ct = _ts.type;

        // use static instance to speed up
        if(ct == TypeSpecifier.Type.VOID)
            return Void.getInstance();
        else if(ct == TypeSpecifier.Type.INT)
            return Int.getInstance();
        else if(ct == TypeSpecifier.Type.CHAR)
            return Char.getInstance();
        else if(ct == TypeSpecifier.Type.STRUCT || ct == TypeSpecifier.Type.UNION)
        {
            // routines for struct and union are quite alike
            String prefix = ct == TypeSpecifier.Type.STRUCT ? "struct " : "union ";

            // unique tag name
            String tag = (prefix + _ts.id).intern();

            // related entry
            Entry ce = env.get(Symbol.symbol(tag));

            // 2 possibilities: usage / declaration
            if(_ts.non_init_decls == null)
            {
                // no non_init_declarations, just usage
                if(ce == null)
                    return new Name(tag, env);
                else
                    return ce.type;
            }
            else
            {
                // type declaration
                if(ce != null)
                    throw new Exception(tag + " has already been defined!");
                else
                {
                    env.beginScope();
                    LinkedList<RecordField> field = checkRecord(_ts.non_init_decls);
                    env.endScope();

                    Record rec;
                    if(ct == TypeSpecifier.Type.STRUCT)
                        rec = new Struct(_ts.id, field);
                    else
                        rec = new Union(_ts.id, field);

                    return rec;
                }
            }
        }
        else
            return null;
    }

    public Type checkPlainDeclarator(PlainDeclarator _pdr, Type _def_type) throws Exception
    {
        if(_pdr.stars == null || _pdr.stars.cnt == 0)
            return _def_type;
        else
        {
            Type real_type = _def_type;
            for (int i = 0; i < _pdr.stars.cnt; i++)
                real_type = new Pointer(real_type);

            return real_type;
        }
    }

    public Type checkDeclarator(Declarator _dr, Type _def_type) throws Exception
    {
        // take stars into consideration
        Type real_type = checkPlainDeclarator(_dr.plain_declarator, _def_type);

        if(_dr.isFunc)
        {
            env.beginScope();
            LinkedList<Type> params = checkParameters(_dr.param);
            env.endScope();

            Function func = new Function(_dr.plain_declarator.id, params, real_type);
            FuncEntry fe = new FuncEntry(func);

            env.put(Symbol.symbol(func.name), fe);
        }
        else
        {            
            // all the dimension settings must be constants
            Iterator<Expr> it = _dr.dimension.iterator();
            while (it.hasNext())
            {
                Expr e = it.next();
                checkExpr(e);

                int cur_dim = 0;
                if(e.isConst)
                    cur_dim = e.val;
                else
                    throw new Exception(e.toString() + "isn't a legal dimension setting!");

                real_type = new Array(cur_dim, real_type);
            }

            if(real_type instanceof Void)
                throw new Exception("Can't declare " + _dr.plain_declarator.id + "as void!");
            else if(real_type instanceof Name)
                throw new Exception("Can't declare " + _dr.plain_declarator.id + "as undefined type!");
            else
            {
                // set up new entry
                VarEntry ve = new VarEntry(real_type);
                env.put(Symbol.symbol(_dr.plain_declarator.id), ve);
            }
        }

        return real_type;
    }

    public Type checkInitializer(Initializer _ir) throws Exception
    {
        if(_ir == null)
            return null;
        else if(_ir.value != null)
            return checkExpr(_ir.value);
        else
        {
            Iterator<Initializer> it = _ir.initializers.comp.iterator();

            // resolve the type of each element
            Type et = checkInitializer(it.next());

            while (it.hasNext())
            {
                Type ct = checkInitializer(it.next());

                if(et.equals(ct) == false)
                    throw new Exception("Different types in one array!");
            }

            return new Array(_ir.initializers.comp.size(), et);
        }
    }

    public LinkedList<Type> checkParameters(Parameters _p) throws Exception
    {
        LinkedList<Type> ans = new LinkedList<Type>();

        if(_p != null)
        {
            for (PlainDecl elem : _p.comp)
            {
                Type def_type = checkTypeSpecifier(elem.type_specifier);
                Type real_type = checkDeclarator(elem.declarator, def_type);

                ans.add(real_type);
            }
        }

        return ans;
    }

    public LinkedList<RecordField> checkRecord(NonInitDecls _nids) throws Exception
    {
        LinkedList<RecordField> ans = new LinkedList<RecordField>();

        for (NonInitDecl elem : _nids.comp)
        {
            Type def_type = checkTypeSpecifier(elem.type_specifier);
            for (Declarator dr : elem.declarators.comp)
            {
                Type real_type = checkDeclarator(dr, def_type);
                RecordField crf = new RecordField(real_type, dr.plain_declarator.id);
                ans.add(crf);
            }
        }

        return ans;
    }

    public void checkFuncDef(FuncDef _func) throws Exception
    {
        Type def_type = checkTypeSpecifier(_func.type_specifier);
        Type real_type = checkPlainDeclarator(_func.id, def_type);

        // since the grammar is simplified, we won't encounter pointers
        if(real_type instanceof Name)
            throw new Exception("Undefined struct/union shouldn't be the return type of function: " + _func.id.id);

        String u = _func.id.id.intern();
        Entry ce = env.get(Symbol.symbol(u));

        if(ce != null)
            throw new Exception(u + " has already been defined in this scope!");

        env.beginScope();
        LinkedList<Type> args = checkParameters(_func.paras);
        checkStmt(_func.comp_stmt);
        env.endScope();

        Function ft = new Function(u, args, real_type);
        FuncEntry fe = new FuncEntry(ft);

        env.put(Symbol.symbol(u), fe);
    }

    public Type checkExpr(Expr e) throws Exception
    {
        if(e==null)
            return null;
        else if(e instanceof Expression)
        {
            Expression ee = (Expression)e;
            Type ct=null;
            
            for(Expr elem : ee.comp)
                ct = checkExpr(elem);
            
            ee.entry=new Entry(ct, false);
            
            return ct;
        }
        else if(e instanceof AssignmentExpr)
        {
            AssignmentExpr ae = (AssignmentExpr)e;
            
            Type lt = checkExpr(ae.left);
            Type rt = checkExpr(ae.right);
            
            if(!lt.isAssignable(rt))
                throw new Exception("Incompatible Assignment between " + lt.toString() + " and " + rt.toString());
            else if(!ae.left.entry.isLValue)
                throw new Exception("Expr: " + ae.left.toString() + " is not LValue!");
            else if(ae.operation_type!=AssignmentExpr.Type.ASSIGN)
            {
                boolean ok=true;
                if(!(lt instanceof Int) && !(lt instanceof Char))
                    ok=false;
                if(!(rt instanceof Int) && !(rt instanceof Char))
                    ok=false;
                
                if(!ok)
                    throw new Exception("Can't do assignment between " + lt.toString() + " and " + rt.toString());
                else
                {
                    ae.entry=new Entry(rt,false);
                    return rt;
                }
            }
            else
            {
                ae.entry=new Entry(rt, false);
                return rt;
            }
        }
        else if(e instanceof BinaryExpr)
        {
            BinaryExpr be = (BinaryExpr)e;
            
            Type lt = checkExpr(be.left);
            Type rt = checkExpr(be.right);
            
            if(lt.canOperateWith(be.operation_type, rt))
            {
                be.entry=new Entry(rt, false);
                return rt;
            }
            else
                throw new Exception("Can't do binary operation between " + lt.toString() + " and " + rt.toString());
        }
        else if(e instanceof CastExpr)
        {
            CastExpr ce = (CastExpr)e;
            
            Type expr_type = checkExpr(ce.expr);
            Type cmd_type = checkTypeSpecifier(ce.type_name.type_specifier);
            if(ce.type_name.stars!=null)
                for(int i=0;i<ce.type_name.stars.cnt;i++)
                    cmd_type = new Pointer(cmd_type);
            
            boolean ok=true;
            if(!(expr_type instanceof Int) && !(expr_type instanceof Char) && !(expr_type instanceof Pointer))
                ok=false;
            if(!(cmd_type instanceof Int) && !(cmd_type instanceof Char) && !(cmd_type instanceof Pointer))
                ok=false;
            
            if(!ok)
                throw new Exception("Can't cast non-basic types: from " + expr_type.toString() +" to " + cmd_type.toString());
            else
            {
                ce.entry=new Entry(cmd_type, false);
                return cmd_type;
            }
        }
        else if(e instanceof PostfixExpr)
        {
            PostfixExpr pe = (PostfixExpr)e;
            
            if(pe.operation_type == PostfixExpr.Type.MPAREN)
            {
                //array or pointer
                Type param_type = checkExpr((Expr)pe.param);
                
                if((param_type instanceof Int) || (param_type instanceof Char))
                {
                    Type expr_type = checkExpr(pe.expr);
                    
                    if(expr_type instanceof Array)
                    {
                        Array aa = (Array)expr_type;
                        pe.entry=new Entry(aa.type, true);
                        return aa.type;
                    }
                    else if(expr_type instanceof Pointer)
                    {
                        Pointer pp = (Pointer)expr_type;
                        pe.entry = new Entry(pp.elem_type, true);
                        return pp.elem_type;
                    }
                    else
                        throw new Exception("Not a pointer or array!");
                }
                else
                    throw new Exception("Invalid index!");
            }
            else if(pe.operation_type == PostfixExpr.Type.PAREN)
            {
                //function call
                
            }
            else if(pe.operation_type == PostfixExpr.Type.DOT)
            {
                
            }
            else if(pe.operation_type == PostfixExpr.Type.PTR)
            {
                
            }
            else if(pe.operation_type == PostfixExpr.Type.INC)
            {
                
            }
            else if(pe.operation_type == PostfixExpr.Type.DEC)
            {
                
            }
            else
                throw new Exception("Internal Error!");
        }
        else if(e instanceof UnaryExpr)
        {
            UnaryExpr ue = (UnaryExpr)e;
            
            if(ue.operation_type == UnaryExpr.Type.INC)
            {
                
            }
            else if(ue.operation_type == UnaryExpr.Type.DEC)
            {
                
            }
            else if(ue.operation_type == UnaryExpr.Type.SIZEOF)
            {
                
            }
            else if(ue.operation_type == UnaryExpr.Type.BIT_AND)
            {
                
            }
            else if(ue.operation_type == UnaryExpr.Type.STAR)
            {
                
            }
            else
            {
                
            }
        }
        else if(e instanceof PrimaryExpr)
        {
            PrimaryExpr pe = (PrimaryExpr)e;
            
            if(pe.elem_type == PrimaryExpr.Type.ID)
            {
                
            }
            else if(pe.elem_type == PrimaryExpr.Type.STRING)
            {
                
            }
            else if(pe.elem_type == PrimaryExpr.Type.INT)
            {
                
            }
            else if(pe.elem_type == PrimaryExpr.Type.CHAR)
            {
                
            }
            else
                throw new Exception("Internal Error!");
        }
        else
            throw new Exception("Not an Expr!");
    }

    public void checkStmt(Stmt _s) throws Exception
    {

    }
}
