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








