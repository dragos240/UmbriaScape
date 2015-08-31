import javax.swing.*;

public class Dialogs{
	public static void main(String args[]){
		int mc = JOptionPane.QUESTION_MESSAGE;
		String[] opts = {"Oldschool", "EoC"};
		int ch = JOptionPane.showOptionDialog(null, "Choose a version of Runescape", "Choose a version", 0, mc, null, opts, opts[0]);
		System.out.println ("You clicked " + opts[ch]);
	}
}
