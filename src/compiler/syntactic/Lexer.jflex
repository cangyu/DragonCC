package compiler.syntactic;
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
	private int charLen;
	private char charContent;
	private StringBuffer strContent;

	private void err(String msg)
	{
		System.out.println("Scanning error in line " + yyline + ", column " + yycolumn + ": " + msg);
		System.exit(1);
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
			err("Comment symbol doesn't match on EOF!");
		if(yystate()==YYSTRING)
			err("String symbol doesn't match on EOF!");
		if(yystate()==YYCHAR)
			err("Char symbol doesn't match on EOF!");
		return token(EOF, null);
	}
%eofval}

LineTerminator= \n|\r|\r\n
WhiteSpace={LineTerminator}|[ \t\f]

DecIntegerLiteral=0|[1-9][0-9]
OctIntegerLiteral=0+[0-7]
HexIntegerLiteral=0[xX][0-9a-fA-F]

Identifier=[_a-zA-Z][_a-zA-Z0-9]*

%state YYCOMMENT, YYLINECOMMENT, YYSTRING, YYCHAR

%%

<YYINITIAL>
{
	/* Comment */
	"//" {yybegin(YYLINECOMMENT);}
	"/*" {yybegin(YYCOMMENT);}
	"*/" { error("Comment symbol doesn't match!"); }
	
	/* Char & String */
	"\‘" {charLen=0; yybegin(YYCHAR);}
	"\"" {strContent=new StringBuffer(); yybegin(YYSTRING);}
	
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

	"="  { return token(ASSIGN);}
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

	"|"  { return token(BIT_OR); }
	"^"  { return token(BIT_XOR); }
	"&"  { return token(BIT_AND); }
	"~"  { return token(BIT_NOT); }
	"<<" { return token(SHL); }
	">>" { return token(SHR); }
	
	/*Blank Space*/
	{WhiteSpace} {}
	
	/* ID */
	{Identifier} {return token(ID,yytext());}
	
	/* NUM */
	{DecIntegerLiteral} {return token(NUM, new Integer(yytext());}
	{OctIntegerLiteral} {return token(NUM, Integer.valueOf(yytext().substring(1),8));}
	{HexIntegerLiteral} {return token(NUM, Integer.valueOf(yytext().substring(2),16));}
}

<YYLINECOMMENT>
{
	{LineTerminator} {yybegin(YYINITIAL);}
	[^] {}
}

<YYCOMMENT>
{
	"*/" {yybegin(YYINTIAL);}
	[^] {}
}

<YYCHAR>
{
	"\'" 
	{
		if(charLen==1)
		{	
			yybegin(YYINITIAL);
			return token(CHARCONTENT, new Character(charContent));
		} 
		else
			err("Not a character!");
	}
	
	"\\t" {charContent='\t'; ++charLen;}
	"\\n" {charContent='\n'; ++charLen;}
	"\\r" {charContent='\r'; ++charLen;}
	"\\\\" {charContent='\\'; ++charLen;}
	"\\\'" {charContent='\''; ++charLen;}
	"\\\"" {charContent='\"'; ++charLen;}
	
	{LineTerminator} {err("Line Terminator detected! Invalid character representation!");}
	
	"\\[0-7]" {charContent=(char)Integer.parseInt(yytext().substring(1,2),8); ++charLen;}
	"\\[0-7]{2}" {charContent=(char)Integer.parseInt(yytext().substring(1,3),8); ++charLen;}
	"\\[0-1][0-7]{2}" {charContent=(char)Integer.parseInt(yytext().substring(1,4),8); ++charLen;}

	"\\x[0-9a-fA-F]" {charContent=(char)Integer.parseInt(yytext().substring(3,4),16); ++charLen;}
	"\\x[0-9a-fA-F]{2}" {charContent=(char)Integer.parseInt(yytext().substring(3,5),16); ++charLen;}
	
	[^] {charContent=yytext().charAt(0); ++charLen;}
}

<YYSTRING>
{
	"\"" {yybegin(YYINITIAL); return token(STRINGCONTENT,strContent.toString());}
	
	"\\t" {strContent.append('\t');}
	"\\n" {strContent.append('\n');}
	"\\r" {strContent.append('\r');}
	"\\\\" {strContent.append('\\');}
	"\\\'" {strContent.append('\'');}
	"\\\"" {strContent.append('\"');}
	
	{LineTerminator} {err("Line Terminator detected! Invalid string representation!");}
	
	"\\[0-7]" {strContent.append((char)Integer.parseInt(yytext().substring(1,2),8));}
	"\\[0-7]{2}" {strContent.append((char)Integer.parseInt(yytext().substring(1,3),8));}
	"\\[0-1][0-7]{2}" {strContent.append((char)Integer.parseInt(yytext().substring(1,4),8));}

	"\\x[0-9a-fA-F]" {strContent.append((char)Integer.parseInt(yytext().substring(3,4),16));}
	"\\x[0-9a-fA-F]{2}" {strContent.append((char)Integer.parseInt(yytext().substring(3,5),16));}
	
	[^] {strContent.append(yytext().charAt(0));}
}

