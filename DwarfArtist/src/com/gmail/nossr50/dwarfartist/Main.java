package com.gmail.nossr50.dwarfartist;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.gmail.nossr50.dwarfartist.datatypes.ModificationState;
import com.gmail.nossr50.dwarfartist.io.FileManager;
import com.gmail.nossr50.dwarfartist.manager.LayoutManager;
import com.gmail.nossr50.dwarfartist.manager.TitleManager;

public class Main {

	public static void main(String[] args) {
		ModificationState modificationState = ModificationState.NEW; //TODO: Finish this
		Display display = new Display();
		Shell shell = new Shell(display);
		FileManager fm = new FileManager(); //Handles the IO interactions
		TitleManager tm = new TitleManager(shell, fm, modificationState); //Handles updates to the title of the shell
		
		//FormLayout is the best!
		FormLayout formLayout = new FormLayout();
		shell.setLayout(formLayout);
		
		
		//SetMargins
		formLayout.marginHeight = 5;
		formLayout.marginWidth = 5;
        
        //Title
        shell.setText("DwarfArtist");
        
        //Layout manager will handle the arrangement of widgets and their interactions with each other
      	LayoutManager lm = new LayoutManager(shell, fm, tm);
      	lm.setup();
      	
      	tm.updateName(); //This will set the name of the shell
        
        shell.pack(); //Resizes the shell to the minimum size needed to fit all of its children
		shell.open(); //Opens the shell on the display
		
		shell.setMinimumSize(shell.getBounds().width, shell.getBounds().height); //Prevent the shell from being resized to something too small
		
		//Run the event loop as long as the window is open
		while (!shell.isDisposed()) {
			//read the next OS event queue and transfer it to a SWT event
			if(!display.readAndDispatch()) {
				//if nothing is going on sleep until the next OS event
				display.sleep();
			}
		}
		
		//Garbage collection
		display.dispose();
	}
}