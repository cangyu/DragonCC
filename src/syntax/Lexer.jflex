package syntax;
import java.io.*;

%%

%class Lexer
%cup
%unicode
%line
%column
%public
%implements Symbols

%{
	private int commentCnt=0;
	
	private void err(String msg)
	{
		System.out.println("Scanning error in line " + yyline + ", column " + yycolumn + ": " + msg);
	}
	
	private java_cup.runtime.Symbol token(int kind)
	{
		return new java_cup.runtime.Symbol(kind,yyline,yycolumn);
	}
	
	private java_cup.runtime.Symbol token(int kind, Object val)
	{
		return new java_cup.runtime.Symbol(kind, yyline, yycolumn, val);
	}
%}

%eofval{
	{
		if(yystate()==YYCOMMENT)
		{
			err("Comment symbol do not match on EOF!\n");
		}
		return token(EOF, null);
	}
%eofval}

LineTerminator= \n|\r|\r\n
WhiteSpace={LineTerminator}|[ \t\f]
InputCharacter=[^\r\n]

DecIntegerLiteral=0|[1-9][0-9]*
DecLongLiteral={DecIntegerLiteral}[lL]

OctIntegerLiteral=0+[0-7]*
OctLongLiteral={OctIntegerLiteral}[lL]

HexIntegerLiteral=0[xX][0-9a-fA-F]
HexLongLiteral={HexIntegerLiteral}[lL]

Identifier=[_a-zA-Z][_a-zA-Z0-9]*

TraditionalComment="/*"
EndOfLineComment="//"{InputCharacter}*{LineTerminator}?
Comment={TraditionalComment}|{EndOfLineComment}

<YYINITIAL>
{
	/* keyword */
	
	"void"     {return token(VOID);}
	"char"     {return token(CHAR);}
	"int"      {return token(INT);}
	"struct"   {return token(STRUCT);}
	"union"    {return token(UNION);}
	"if"       {return token(IF);}
	"else"     {return token(ELSE);}
	"while"    {return token(WHILE);}
	"for"      {return token(FOR);}
	"continue" {return token(CONTINUE);}
	"break"    {return token(BREAK);}
	"return"   {return token(RETURN);}
	"sizeof"   {return token(SIZEOF);}
	
	/* operator */

	"(" { return token(LPAREN); }
	")" { return token(RPAREN); }
	"{" { return token(LBRACE); }
	"}" { return token(RBRACE); }
	"[" { return token(LMPAREN); }
	"]" { return token(RMPAREN); }

	";" { return token(SEMI); }
	"," { return token(COMMA); }

	"+" { return token(PLUS); }
	"-" { return token(MINUS); }
	"*" { return token(TIMES); }
	"/" { return token(DIVIDE); }
	"%" { return token(MODULE); }
	
	"++" { return token(INC); }
	"--" { return token(DEC); }

	"*="  { return token(MUL_ASSIGN); }
	"/="  { return token(DIV_ASSIGN); }
	"%="  { return token(MOD_ASSIGN); }
	"+="  { return token(ADD_ASSIGN); }
	"-="  { return token(SUB_ASSIGN); }
	"<<=" { return token(SHL_ASSIGN); }
	">>=" { return token(SHR_ASSIGN); }
	"&="  { return token(AND_ASSIGN); }
	"^="  { return token(XOR_ASSIGN); }
	"|="  { return token(OR_ASSIGN); }

	
	"!"   { return token(NOT); }
	"||"  { return token(OR); }
	"&&"  { return token(AND); }

	"."  { return token(DOT); }
	"->" { return token(PTR); }

	"==" { return token(EQ); }
	"!=" { return token(NE); }
	"<"  { return token(LT); }
	"<=" { return token(LE); }
	">"  { return token(GT); }
	">=" { return token(GE); }

	"="  { return token(ASSIGN);}

	"|"  { return token(BIT_OR); }
	"^"  { return token(BIT_XOR); }
	"&"  { return token(BIT_AND); }
	"~"  { return token(BIT_NOT); }
	"<<" { return token(SHL); }
	">>" { return token(SHR); }
}




