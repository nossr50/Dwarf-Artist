package com.gmail.nossr50.dwarfartist.manager;

import org.eclipse.swt.widgets.Shell;

import com.gmail.nossr50.dwarfartist.datatypes.ModificationState;
import com.gmail.nossr50.dwarfartist.io.FileManager;

public class TitleManager {
	
	private Shell shell;
	private FileManager fm;
	private ModificationState modificationState;
	private String programTitle = "DwarfArtist";
	public String versionNumber = "ver01";
	
	public TitleManager(Shell shell, FileManager fm, ModificationState modificationState) {
		this.shell = shell;
		this.fm = fm;
		this.modificationState = modificationState;
	}
	
	public void updateName() {
		String newTitle = "";
		String spacer = " - ";
		
		newTitle += programTitle;
		newTitle += " ["+versionNumber+"]";
		
		if(fm.isLoaded()) {
			newTitle += spacer;
			newTitle += fm.getFileName();
		} else {
			//TODO
		}
		
		//Change the title depending on whats going on with the edits
		switch(modificationState) {
		case UNSAVED:
			newTitle+= " (UNSAVED)";
			break;
		default:
			break;
		}
		
		shell.setText(newTitle);
	}
	
	public String getProgramTitle() {return programTitle;}
}