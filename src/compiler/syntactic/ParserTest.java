package compiler.syntactic;

import java.io.*;
import com.google.gson.*;
import org.json.*;

final public class ParserTest
{	
	public static void parse(String filename) throws IOException 
	{		
		InputStream inp = new FileInputStream(filename);
		Parser parser = new Parser(inp);
		java_cup.runtime.Symbol parseTree = null;
		
		try 
		{
			parseTree = parser.parse();
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
			throw new Error(e.toString());
		}
		finally
		{
			inp.close();
		}
		
		Gson gson = new Gson();
		String uglys = gson.toJson(parseTree.value);
		JSONTokener tokener = new JSONTokener(uglys); //tokenize the ugly JSON string
		
		try 
		{
			JSONObject finalResult = new JSONObject(tokener); // convert it to JSON object
			System.out.println(finalResult.toString(4)); // To string method prints it with specified indentation.
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println(gson.toJson(parseTree.value));
	}

	public static void main(String argv[]) throws IOException
	{
		parse("tests/example2.c");
	}
}
