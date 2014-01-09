package com.gmail.nossr50.dwarfartist.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.gmail.nossr50.dwarfartist.datatypes.ColorEditContainer;
import com.gmail.nossr50.dwarfartist.datatypes.TilesetColor;
import com.gmail.nossr50.dwarfartist.io.FileManager;
import com.gmail.nossr50.dwarfartist.tools.Tool;

public class LayoutManager {
	
	public Shell shell;
	public Menu menuBar;
	public FileManager fm;
	public TitleManager tm;
	private Label shamelessPlug, fileLoadedLabel;
	public ArrayList<ColorEditContainer> colorEditContainers = new ArrayList<ColorEditContainer>();
	
	public LayoutManager(Shell shell, FileManager fm, TitleManager tm) {
		this.shell = shell;
		this.fm = fm;
		this.tm = tm;
	}
	
	/***
	 * Initialize and set layout data for all of the children of shell
	 */
	public void setup() {
		setupMenu();
		setupFileLoadedLabel();
		setupFileLoadedLabelFormData();
		setupColorEditContainerArray();
		setupShamelessPlug();
	}
	
	public void setupFileLoadedLabelFormData() {
		//File Loaded Label
		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 0);
		fileLoadedLabel.setLayoutData(formData);
		fileLoadedLabel.pack();
	}
	
	public void setupFileLoadedLabel() {
		fileLoadedLabel = new Label(shell, SWT.NONE);
		fileLoadedLabel.setText("Current File: N/A");
	}
	
	/***
	 * Setup the menu widget and its children
	 */
	public void setupMenu() {
		menuBar = new Menu(shell, SWT.BAR);
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        
        //Setup the File menu
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);
        
        //Setup the children of File
        MenuItem loadFileItem = new MenuItem(fileMenu, SWT.PUSH);
        loadFileItem.setText("&Load");
        
        MenuItem saveFileItem = new MenuItem(fileMenu, SWT.PUSH);
        saveFileItem.setText("&Save");

        MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
        exitItem.setText("&Exit");
        
        shell.setMenuBar(menuBar);

        //Add listeners for the File menu items' children
        exitItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.getDisplay().dispose();
                System.exit(0);
            }
        });
        
        saveFileItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(fm.isLoaded()) {
                	MessageBox mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
                	mb.setText("Confirmation");
                	mb.setMessage("Do you want to save?");
                	if(mb.open() == SWT.YES) {
                		fm.saveFile(tm);
                	}
                } else {
                	MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                	mb.setText("Information");
                	mb.setMessage("Please load a file");
                	mb.open();
                	System.out.println("No file to save");
                }
            }
        });
        
        loadFileItem.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		FileDialog dialog = new FileDialog(shell, SWT.NULL);
        		String path = dialog.open();
        			if (path != null) {
        				File file = new File(path);
        				
        				if (file.isFile()) {
        					try {
								fm.loadFile(file, tm);
								
								for(ColorEditContainer cg : colorEditContainers) {
									cg.updateWidgets();
								}
								
								//Update File Label
								fileLoadedLabel.setText("File: "+file.getName());
								fileLoadedLabel.pack();
								
								if(fm.getValuesLoaded() < fm.getExpectedValueCount()) {
									//Tell the user about the missing values
									MessageBox mb = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
									mb.setText("Information");
									mb.setMessage(tm.getProgramTitle()+" was unable to find all of the color values in the target file."
											+ "\nLoaded: " + fm.getValuesLoaded() + " of " + fm.getExpectedValueCount()
											+ "\nMissing: " + fm.getMissingValueCount()
											+ "\nSource File: " + fm.getFileName()
											
											/* TMI PROBABLY
											+ "\nMake sure this is the correct file and it is not missing any values. "
											+ "\n"+tm.getProgramTitle()+" will replace missing values with zeros."
											+ "\nSaving will replace the file with the values in the editor."
											*/
									);
									mb.open();
								}
								
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
        					System.out.println(file.toString());
        				} else {
							//TODO: Make this do something
        				}
        		  } else {
        			  //OR ELSE HUEHUEHUE
        		  }
        	}
        });
        
        //Setup the Tools cascade menu
        MenuItem cascadeToolsMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeToolsMenu.setText("&Tools");
        
        //Setup Tools menu
        Menu toolsMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeToolsMenu.setMenu(toolsMenu);
        
        MenuItem brightenAllItem = new MenuItem(toolsMenu, SWT.PUSH);
        brightenAllItem.setText("&Brighten All Colors");
        
        MenuItem darkenAllItem = new MenuItem(toolsMenu, SWT.PUSH);
        darkenAllItem.setText("&Darken All Colors");
        
        
        //Add listeners for the toolsMenu children
        brightenAllItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                for(ColorEditContainer colorGroup : colorEditContainers) {
                	Tool.Brighten(colorGroup.getTilesetColor());
                	colorGroup.updateWidgets();
                }
            }
        });
        
        darkenAllItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                for(ColorEditContainer colorGroup : colorEditContainers) {
                	Tool.Darken(colorGroup.getTilesetColor());
                	colorGroup.updateWidgets();
                }
            }
        });
    }
	
	/***
	 * Probably going to remove this, but sets up a shameless plug for myself.
	 */
	public void setupShamelessPlug() {
		FormData formData = new FormData();
		shamelessPlug = new Label(shell, SWT.RIGHT);
		shamelessPlug.setText(tm.getProgramTitle()+" by nossr50 (nossr50@gmail.com)");
		
		formData = new FormData();
		formData.top = new FormAttachment(colorEditContainers.get(colorEditContainers.size()-4).getGroup(), 0); //Position it below the editing area
		
		shamelessPlug.setLayoutData(formData);
	}
	
	/***
	 * Initializes and sets up the ColorEditContainer objects and their properties
	 * Each ColorEditContainer corresponds to one of the RGB color values used by Dwarf Fortress
	 */
	public void setupColorEditContainerArray() {
		int pos = 0;
		int rowCount = 0;
		FormData formData = new FormData();
		
		for(TilesetColor color : TilesetColor.values()) {
			formData = new FormData();
			colorEditContainers.add(new ColorEditContainer(shell, color));
			
			if(pos == 0) {
				formData.top = new FormAttachment(fileLoadedLabel, 5);
				formData.left = new FormAttachment(shell, 5);
				colorEditContainers.get(0).setupFormDataGroup(formData);
				rowCount++;
			} else {
				if(rowCount == 0) {
					formData.top = new FormAttachment(colorEditContainers.get(pos-1).getGroup(), 5);
					formData.left = new FormAttachment(shell, 5);
					colorEditContainers.get(pos).setupFormDataGroup(formData);
					rowCount++;
				} else {
					//If one row is built we can just take away 4 to get the widget above
					//Since rows are made of 4 widgets
					//Otherwise we align it to the fileloaded label
					if(pos > 4)
						formData.top = new FormAttachment(colorEditContainers.get(pos-4).getGroup(), 5);
					else
						formData.top = new FormAttachment(fileLoadedLabel, 5);
					
					formData.left = new FormAttachment(colorEditContainers.get(pos-1).getGroup(), 5);
					colorEditContainers.get(pos).setupFormDataGroup(formData);
					
					if(rowCount == 3)
						rowCount = 0;
					else
						rowCount++;
				}
			}
			
			pos++;
		}
	}
}