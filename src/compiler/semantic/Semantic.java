package compiler.semantic;

import java.io.*;
import java.util.*;
import compiler.ast.*;
import compiler.syntactic.*;

public class Semantic
{
    private Table env;
    private Program prog_start;

    public Semantic(Object prog) throws Exception
    {
        if(!(prog instanceof Program))
            throw new Exception("Invalid Program Entrance!");

        env = new Table();
        prog_start = (Program) prog;
    }

    public void checkProgram() throws Exception
    {
        for (GeneralDeclaration gd : prog_start.comp)
        {
            if(gd instanceof Decl)
                checkDecl((Decl) gd);
            else
                checkFuncDef((FuncDef) gd);
        }

        System.out.println("Program checking completed!");
    }

    public void checkDecl(Decl _decl) throws Exception
    {
        System.out.println("Checking declaration: " + _decl.toString());

        // get current defined type of this declaration
        Type def_type = checkTypeSpecifier(_decl.type_specifier);

        // if no init_declarators, this is just a type declaration
        if(_decl.init_declarators == null)
            return;

        // check each init_declarator
        for (InitDeclarator elem : _decl.init_declarators.comp)
        {
            // take the stars into consideration, get real type of the
            // identifier
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
                if(e.isConst && e.val instanceof Integer)
                    cur_dim = (Integer) (e.val);
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

    // resolve the type of each element recursively
    // aims to get the nesting description
    public Type checkInitializer(Initializer _ir) throws Exception
    {
        if(_ir == null)
            return null;
        else if(_ir.value != null)
            return checkExpr(_ir.value);
        else
        {
            // get the type of the first initializer
            Iterator<Initializer> it = _ir.initializers.comp.iterator();
            Type et = checkInitializer(it.next());

            // checking following initializers
            // all initializers should hold same type
            while (it.hasNext())
            {
                Type ct = checkInitializer(it.next());

                if(et.equals(ct) == false)
                    throw new Exception("Different types in one array!");
            }

            // TODO
            // not proper to use initializer's size as length of the array
            return new Array(_ir.initializers.comp.size(), et);
        }
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

    public Type checkExpr(Expr e) throws Exception
    {
        if(e == null)
            return null;
        else if(e instanceof PrimaryExpr)
            return checkExpr_PrimaryExpr((PrimaryExpr) e);
        else if(e instanceof PostfixExpr)
            return checkExpr_PostfixExpr((PostfixExpr)e);
        else if(e instanceof UnaryExpr)
            return checkExpr_UnaryExpr((UnaryExpr) e);
        else
            throw new Exception("Not an Expr!");
    }

    public Type checkExpr_PrimaryExpr(PrimaryExpr pe) throws Exception
    {
        if(pe.elem_type == PrimaryExpr.Type.ID)
        {
            String vn = (String) pe.elem;
            Entry ve = env.get(Symbol.symbol(vn));
            
            if(ve != null)
            {
                //TODO
                //at present, may be not proper
                pe.isConst=false;
                
                //derive from entry
                pe.isLValue=ve.isLValue;
                pe.type=ve.type;
                
                //TODO
                //will be set later, according to entry
                pe.val=null;
                
                return pe.type;   
            }
            else
                throw new Exception("Identifier " + vn + " Use before declaration!");
        }
        else if(pe.elem_type == PrimaryExpr.Type.STRING)
        {
            pe.isConst = true;
            pe.isLValue = false;
            pe.type = new Pointer(Char.getInstance());
            pe.val = (String) pe.elem;

            return pe.type;
        }
        else if(pe.elem_type == PrimaryExpr.Type.INT)
        {
            pe.isConst = true;
            pe.isLValue = false;
            pe.type = Int.getInstance();
            pe.val = (Integer) pe.elem;

            return pe.type;
        }
        else if(pe.elem_type == PrimaryExpr.Type.CHAR)
        {
            pe.isConst = true;
            pe.isLValue = false;
            pe.type = Char.getInstance();
            pe.val = (Character) pe.elem;

            return pe.type;
        }
        else if(pe.elem_type == PrimaryExpr.Type.PAREN)
        {          
            pe.isConst=false;
            pe.isLValue=false;
            pe.type=checkExpr((Expression)pe.elem);
            pe.val=((Expression)pe.elem).comp.getLast().val;
            
            return pe.type;
        }
        else
            throw new Exception("Internal Error!");
    }

    public Type checkExpr_PostfixExpr(PostfixExpr pe) throws Exception
    {
        if(pe.operation_type==PostfixExpr.Type.MPAREN)
        {
            Expr param_expr = (Expr)pe.param;
            Type param_type = checkExpr(param_expr);
            
            if(param_type instanceof Int || param_type instanceof Char)
            {
                Type et = checkExpr(pe.expr);
                
                if(et instanceof Pointer)
                {
                    pe.isConst=false;
                    pe.isLValue=true;
                    pe.type=((Pointer)et).elem_type;
                    pe.val = null;
                    
                    return pe.type;
                }
                else if(et instanceof Array)
                {
                    pe.isConst=false;
                    pe.isLValue=true;
                    pe.type=((Array)et).elem_type;
                    pe.val=null;
                    
                    return pe.type;
                }
                else
                    throw new Exception(pe.expr.toString() + " is not an array nor a pointer!");
            }
            else
                throw new Exception(param_expr.toString() + " is not a valid index!");
        }
        else if(pe.operation_type==PostfixExpr.Type.PAREN)
        {
            Type ft = checkExpr(pe.expr);
            if(ft instanceof Function)
            {
                Function cft = (Function)ft;
                
                Arguments 
                
                return cft.ret_type;
            }
            else
                throw new Exception(pe.expr.toString() + " is not a function!");
        }
        else
            throw new Exception("Internal Error!");
    }
    
    public Type checkExpr_UnaryExpr(UnaryExpr ue) throws Exception
    {        
        if(ue.operation_type == UnaryExpr.Type.INC)
        {
            Type ct = checkExpr(ue.expr);
            if((ct instanceof Int) || (ct instanceof Char))
            {
                return ct;
            }
            else if(ct instanceof Pointer)
            {
                
                return ct;
            }
            else
                throw new Exception("Cannot increase " + ue.expr.toString());
        }
        else if(ue.operation_type == UnaryExpr.Type.DEC)
        {
            
        }
        else if(ue.operation_type == UnaryExpr.Type.BIT_AND)
        {
            
        }
        else if(ue.operation_type == UnaryExpr.Type.STAR)
        {
            
        }
        else if(ue.operation_type == UnaryExpr.Type.SIZEOF)
        {
            
        }
        else if(ue.operation_type == UnaryExpr.Type.POSITIVE)
        {
            
        }
        else if(ue.operation_type == UnaryExpr.Type.NEGATIVE)
        {
            
        }
        else if(ue.operation_type == UnaryExpr.Type.BIT_NOT)
        {
            
        }
        else if(ue.operation_type == UnaryExpr.Type.NOT)
        {
            
        }
        else
            throw new Exception("Internal Error!");
    }

    public void checkFuncDef(FuncDef _func) throws Exception
    {
        System.out.println("Checking function: " + _func.func_name.id);
    }
}
