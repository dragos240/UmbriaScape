import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UmbriaScape extends JFrame implements AppletStub{
	static int eocMemWorlds[] = {1, 2, 3, 5, 6, 9, 10, 12, 14, 15, 16, 18, 21, 22, 23, 24, 25, 26, 27, 28,
							     30, 31, 32, 35, 36, 37, 39, 40, 42, 44, 45, 46, 48, 49, 50, 51, 52, 53,
							     54, 56, 58, 59, 60, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74,
							     76, 77, 78, 79, 82, 83, 84, 85, 86, 87, 88, 89, 91, 92, 96, 97, 98, 99, 100,
							     103, 104, 105, 106, 114, 115, 116, 117, 119, 121, 123, 124, 134, 137, 138, 139};
	static int eocFreeWorlds[] = {3, 7, 8, 11, 17, 19, 20, 29, 33, 34, 38, 41, 43, 55, 57, 61, 80, 81, 108, 120, 135, 136};
	static int osMemWorlds[] = {2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 17, 18, 19, 20,
						 		21, 22, 25, 27, 28, 29, 30, 31, 33, 34, 36, 37, 38, 41, 42,
						 		43, 44, 45, 46, 49, 50, 51, 52, 53, 54, 57, 58, 59, 60, 61,
						 		62, 65, 66, 67, 68, 69, 70, 73, 74, 75, 76, 77, 78};
	static int osFreeWorlds[] = {1, 8, 16, 26, 35, 81, 82, 83, 84, 93, 94};

	static Scanner scanner;
	static File rsPage = new File("index.html");
	static String world;
	private static final HashMap<String, String> parameters = new HashMap();

	public static void main(String[] paramArrayOfString) {
		int worldNum;
		int mc = JOptionPane.QUESTION_MESSAGE;
		String[] version = {"Oldschool", "EoC", "Oldschool members", "EoC members"};
		int choice = JOptionPane.showOptionDialog(null, "Choose a version of Runescape", "Choose a version", 0, mc, null, version, version[0]);
		
		if(version[choice] == "Oldschool"){
			worldNum = new Random().nextInt(osFreeWorlds.length);
			world = String.format("oldschool%d", osFreeWorlds[worldNum]);
		} else if(version[choice] == "Oldschool members"){
			worldNum = new Random().nextInt(osMemWorlds.length);
			world = String.format("oldschool%d", osMemWorlds[worldNum]);
		}else if(version[choice] == "EoC"){
			worldNum = new Random().nextInt(eocFreeWorlds.length);
			world = String.format("world%d", eocFreeWorlds[worldNum]);
		} else if(version[choice] == "EoC members"){
			worldNum = new Random().nextInt(eocMemWorlds.length);
			world = String.format("world%d", eocMemWorlds[worldNum]);
		} else{
			System.out.printf("Window closed. Launcher exiting...\n");
		}

		fetchPage(world);
		new UmbriaScape().setVisible(true);
	}
	public UmbriaScape() {
		String[] params = {"", "", ""};
		String url = "";

		try {
			scanner = new Scanner(rsPage);
			
			setResizable(true);
			setTitle("UmbriaScape - A client by Nia");
			setVisible(true);
		
			params = getArgs();
			if(params[2] != ""){
				url = params[2];
				System.out.printf("Jar: http://%s.runescape.com/%s\n", world, url);
			}
			params = getArgs();
			while(params[0] != "" && params[1] != ""){
				parameters.put(params[0], params[1]);
				System.out.printf("parameters.put(\"%s\", \"%s\");\n", params[0], params[1]);
				params = getArgs();
			}

			scanner.close();

			try{
				Files.delete(rsPage.toPath());
			} catch(IOException e){
				e.printStackTrace();
			}
		
URLClassLoader localURLClassLoader = new URLClassLoader(new URL[] { new URL(String.format("http://%s.runescape.com/%s", world, url)) });

			Applet localApplet = null;
			if(world.startsWith("oldschool")){
				System.out.printf("Loading Oldschool Runescape\n");
				localApplet = (Applet)localURLClassLoader.loadClass("client").newInstance();
			} else if(world.startsWith("world")){
				System.out.printf("Loading EoC Runescape");
				localApplet = (Applet)localURLClassLoader.loadClass("Rs2Applet").newInstance();
			}
			localApplet.setStub(this);
			localApplet.init();
			localApplet.start();

			JPanel localJPanel = new JPanel(new BorderLayout());
			if(world.startsWith("world")){
				localJPanel.setPreferredSize(new Dimension(955, 675));
				setPreferredSize(new Dimension(955, 676));
			} else if(world.startsWith("oldschool")){
				localJPanel.setPreferredSize(new Dimension(776, 575));
				setPreferredSize(new Dimension(776, 575));
			}
			localJPanel.add(localApplet);
			getContentPane().add(localApplet, "Center");
			setDefaultCloseOperation(3);
			if(world.startsWith("world")){
				setSize(804, 605);
			} else if(world.startsWith("oldschool")){
				setSize(780, 535);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}

	}

	public void appletResize(int paramInt1, int paramInt2)
	{
	}

	public final URL getCodeBase() {
		try {
			return new URL(String.format("http://%s.runescape.com/", world)); } catch (Exception localException) {
		}
		return null;
	}

	public final URL getDocumentBase()
	{
		try {
			return new URL(String.format("http://%s.runescape.com/", world)); } catch (Exception localException) {
		}
		return null;
	}
	public final String getParameter(String paramString)
	{
		return (String)parameters.get(paramString);
	}

	public final AppletContext getAppletContext() {
		return null;
	}
	
	public static String[] getArgs(){
		String line;
		String args[] = {"", "", ""};
		
		String pattern1 = "param name";
		Pattern r1 = Pattern.compile(pattern1);
		String pattern2 = "\"(.*?)\"";
		Pattern r2 = Pattern.compile(pattern2);
		String pattern3 = "(?<=document.write\\('archive=).*(?= '\\);)";
		Pattern r3 = Pattern.compile(pattern3);

		Matcher m1 = null;
		Matcher m2 = null;
		Matcher m3 = null;
		while(scanner.hasNextLine()){
			line = scanner.nextLine();
			m1 = r1.matcher(line);
			m3 = r3.matcher(line);

			if(m1.find()){
				m2 = r2.matcher(line);
				m2.find();
				args[0] = m2.group(1);
				m2.find();
				args[1] = m2.group(1);
				return args;
			}else if(m3.find()){
				args[2] = m3.group(0);
				return args;
			}
		}

		return args;
	}
	
	public static void fetchPage(String world){
		String url = String.format("http://%s.runescape.com/index.html", world);
		Path target = rsPage.toPath();
		try{
			URL website = new URL(url);
			Files.copy(website.openStream(), target, StandardCopyOption.REPLACE_EXISTING);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
