import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Extractor{
	static Scanner scanner;

	public static void main(String args[]){
		String[] params;

		File thePage = new File("index.html");

		try{
			scanner = new Scanner(thePage);

			params = getArgs();
			System.out.printf("parameters.put(\"%s\", \"%s\");\n", params[0], params[1]);
			params = getArgs();
			while(params[0] != "" && params[1] != ""){
				System.out.printf("parameters.put(\"%s\", \"%s\");\n", params[0], params[1]);
				params = getArgs();
			}

			scanner.close();
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}

	}
	public static String[] getArgs(){
		String line;
		String args[] = {"", ""};
		
		String pattern1 = "param name";
		Pattern r1 = Pattern.compile(pattern1);
		String pattern2 = "\"(.*?)\"";
		Pattern r2 = Pattern.compile(pattern2);

		Matcher m1 = null;
		Matcher m2 = null;
		while(scanner.hasNextLine()){
			line = scanner.nextLine();
			m1 = r1.matcher(line);

			if(m1.find()){
				m2 = r2.matcher(line);
				m2.find();
				args[0] = m2.group(1);
				m2.find();
				args[1] = m2.group(1);
				return args;
			}
		}

		return args;
	}
}
