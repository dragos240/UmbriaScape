import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;

public class FetchPage{
	public static void main(String args[]){
		File file = new File("index.html");
		Path target = file.toPath();
		try{
			URL website = new URL("http://oldschool1.runescape.com/index.html");
			Files.copy(website.openStream(), target, StandardCopyOption.REPLACE_EXISTING);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
