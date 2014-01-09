package com.gmail.nossr50.dwarfartist.datatypes;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

import com.gmail.nossr50.dwarfartist.eventlistener.MyModifyListener;
import com.gmail.nossr50.dwarfartist.eventlistener.MyVerifyListener;

public class ColorEditContainer {
	private Group group;
	private Color color;
	private Shell shell;
	private TilesetColor tilesetColor;
	private Slider redSlider, greenSlider, blueSlider;
	private Text redText, greenText, blueText;
	private Label redLabel, greenLabel, blueLabel, colorLabel;

	//TODO: Have width and height be based on the boundaries of the shell
	public int width = 192;
	public int height = 85;
	
	public ColorEditContainer(Shell shell, TilesetColor tilesetColor) {
		this.shell = shell; //Need to store a reference to update the color labels
		this.tilesetColor = tilesetColor;
		group = new Group(shell, SWT.NONE); //Set this widget to be a child of the shell
		group.setText(tilesetColor.toString()); //Header name for the widget
		
		//Set children to use the FormLayout layout
		FormLayout formLayout = new FormLayout(); 
		group.setLayout(formLayout);
		
		//Setup the position/display of the RGB color preview widget
		setupColorPreview(shell);
		
		//Initialize the children of the group widget
		redSlider = new Slider(group, SWT.HORIZONTAL);
		redLabel = new Label(group, SWT.HORIZONTAL);
		redText = new Text(group, SWT.SINGLE | SWT.BORDER);
		
		greenSlider = new Slider(group, SWT.HORIZONTAL);
		greenLabel = new Label(group, SWT.HORIZONTAL);
		greenText = new Text(group, SWT.SINGLE | SWT.BORDER);
		
		blueSlider = new Slider(group, SWT.HORIZONTAL);
		blueLabel = new Label(group, SWT.HORIZONTAL);
		blueText = new Text(group, SWT.SINGLE | SWT.BORDER);
		
		//Set default values of the children widgets
		redSlider.setValues(tilesetColor.getRedValue(), 0, 256, 1, 1, 5);
		greenSlider.setValues(tilesetColor.getGreenValue(), 0, 256, 1, 1, 5);
		blueSlider.setValues(tilesetColor.getBlueValue(), 0, 256, 1, 1, 5);
		
		redText.setText(String.valueOf(tilesetColor.getRedValue()));
		greenText.setText(String.valueOf(tilesetColor.getGreenValue()));
		blueText.setText(String.valueOf(tilesetColor.getBlueValue()));
		
		redLabel.setText("R: ");
		greenLabel.setText("G: ");
		blueLabel.setText("B: ");
		
		
		//Register event listeners for some of the children widgets
		setSliderListeners();
		addTextListeners();
		
		//Setup layout/positioning of the group (parent) widget
		setupFormData();
	}
	
	/***
	 * Layout data for the parent (group) of the widgets
	 * @param formData
	 */
	public void setupFormDataGroup(FormData formData) {
		formData.width = width;
		formData.height = height;
		group.setLayoutData(formData);
	}
	
	/***
	 * Initializes the color preview widget (Label)
	 * @param shell
	 */
	public void setupColorPreview(Shell shell) {
		color = new Color(shell.getDisplay(), new RGB(0, 0, 0)); //Conveniently the game uses RGB values for its colors
		
		colorLabel = new Label(group, SWT.BORDER); //This is the actual widget which will be displaying the RGB colors
	    colorLabel.setText("	"); //This might not even be necessary
	    colorLabel.setBackground(color);
	    
	    color.dispose(); //SWT does no garbage collection for ANY of its objects, might be able to get away with having color be a variable in the scope and not using new...
	}
	
	
	/***
	 * Adds event listeners for verification (before display) and modification (after display) of Text widgets
	 */
	public void addTextListeners() {
		//We only allow numbers 'round these parts
		redText.addVerifyListener(new MyVerifyListener());
		greenText.addVerifyListener(new MyVerifyListener());
		blueText.addVerifyListener(new MyVerifyListener());
		
		//Make sure values entered are not too high or too low (< 0 || > 255)
		redText.addModifyListener(new MyModifyListener(this, ValueType.RED));
		greenText.addModifyListener(new MyModifyListener(this, ValueType.GREEN));
		blueText.addModifyListener(new MyModifyListener(this, ValueType.BLUE));
	}
	
	
	/***
	 * Adjust the information represented by these widgets depending on current values in the TileSetColor ENUMs
	 * User interaction will never call this
	 */
	public void updateWidgets() {
		redSlider.setValues(tilesetColor.getRedValue(), 0, 256, 1, 1, 5);
		greenSlider.setValues(tilesetColor.getGreenValue(), 0, 256, 1, 1, 5);
		blueSlider.setValues(tilesetColor.getBlueValue(), 0, 256, 1, 1, 5);
		
		//When we update the text fields to the current values it will cause the sliders to update because of a Modification event being fired
		//Therefore it would be redundant to update the sliders here (and could cause a stack overflow loop)
		redText.setText(String.valueOf(tilesetColor.getRedValue()));
		greenText.setText(String.valueOf(tilesetColor.getGreenValue()));
		blueText.setText(String.valueOf(tilesetColor.getBlueValue()));
		
		//Update the color label preview too
		updateColorLabelPreview();
	}
	
	/***
	 * Updates the old RGB value with the new RBG value, this causes the display to redraw
	 */
	public void updateColorLabelPreview() {
		color = new Color(shell.getDisplay(), new RGB(tilesetColor.getRedValue(), tilesetColor.getGreenValue(), tilesetColor.getBlueValue()));
		colorLabel.setBackground(color);
		color.dispose(); //SWT should hire some garbage-men
	}
	
	/***
	 * Add events for when sliders are manipulated. This will fire with user interaction or without.
	 */
	public void setSliderListeners() {
		redSlider.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		        switch (event.detail) {
		        case SWT.ARROW_DOWN:
		          break;
		        case SWT.ARROW_UP:
		          break;
		        case SWT.DRAG:
		          break;
		        case SWT.END:
		          break;
		        case SWT.HOME:
		          break;
		        case SWT.PAGE_DOWN:
		          break;
		        case SWT.PAGE_UP:
		          break;
		        }
		        redText.setText(String.valueOf(redSlider.getSelection()));
		        updateColorLabelPreview();
		      }
		    });
		
		greenSlider.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		        switch (event.detail) {
		        case SWT.ARROW_DOWN:
		          break;
		        case SWT.ARROW_UP:
		          break;
		        case SWT.DRAG:
		          break;
		        case SWT.END:
		          break;
		        case SWT.HOME:
		          break;
		        case SWT.PAGE_DOWN:
		          break;
		        case SWT.PAGE_UP:
		          break;
		        }
		        greenText.setText(String.valueOf(greenSlider.getSelection()));
		        updateColorLabelPreview();
		      }
		    });
		
		blueSlider.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		        switch (event.detail) {
		        case SWT.ARROW_DOWN:
		          break;
		        case SWT.ARROW_UP:
		          break;
		        case SWT.DRAG:
		          break;
		        case SWT.END:
		          break;
		        case SWT.HOME:
		          break;
		        case SWT.PAGE_DOWN:
		          break;
		        case SWT.PAGE_UP:
		          break;
		        }
		        blueText.setText(String.valueOf(blueSlider.getSelection()));
		        updateColorLabelPreview();
		      }
		    });
	}
	
	/***
	 * Configure layout data for children of the widget
	 * Without this the widgets position and size will be set to defaults (not good)
	 */
	public void setupFormData() {
		//Positioning should look like...
		//Slider -> Label -> Text
		// ^ Slider -> etc...
		FormData formData = new FormData();
		
		Double sliderAndMarginsWidth = width * .70; //For now set sliders to use a percentage of the available size of the parent (group) widget
		
		int borderMargin = 5;
		int widgetMargin = 1;
		int topWidgetMargin = 10;
		
		int sliderWidth = (sliderAndMarginsWidth.intValue() - (borderMargin + (widgetMargin * 2)));
		int remainingWidth = (width - sliderWidth) - 20;
		int textWidth = remainingWidth / 3;
		int labelWidth = 15;
		
		//FormAttachments are 'anchors' for the widgets, they will hug the widget specified on the side specified
		
		//Red Widgets
		formData.width = sliderWidth;
		formData.top = new FormAttachment(group, borderMargin);
		formData.left = new FormAttachment(group, borderMargin);
		
		redSlider.setLayoutData(formData);
		
		formData = new FormData();
		formData.width = labelWidth;
		formData.top = new FormAttachment(redSlider, borderMargin, SWT.CENTER);
		formData.left = new FormAttachment(redSlider, widgetMargin);
		
		redLabel.setLayoutData(formData);
		
		formData = new FormData();
		formData.width = textWidth;
		formData.top = new FormAttachment(redLabel, borderMargin, SWT.CENTER);
		formData.left = new FormAttachment(redLabel, widgetMargin);
		
		redText.setLayoutData(formData);
		
		//Green Widgets
		formData = new FormData();
		formData.width = sliderWidth;
		formData.top = new FormAttachment(redSlider, topWidgetMargin);
		formData.left = new FormAttachment(group, borderMargin);
		
		greenSlider.setLayoutData(formData);
		
		formData = new FormData();
		formData.width = labelWidth;
		formData.top = new FormAttachment(greenSlider, topWidgetMargin, SWT.CENTER);
		formData.left = new FormAttachment(greenSlider, widgetMargin);
		
		greenLabel.setLayoutData(formData);
		
		formData = new FormData();
		formData.width = textWidth;
		formData.top = new FormAttachment(greenLabel, topWidgetMargin, SWT.CENTER);
		formData.left = new FormAttachment(greenLabel, widgetMargin);
		
		greenText.setLayoutData(formData);
		
		//BlueWidgets
		formData = new FormData();
		formData.width = sliderWidth;
		formData.top = new FormAttachment(greenSlider, topWidgetMargin);
		formData.left = new FormAttachment(group, borderMargin);
		
		blueSlider.setLayoutData(formData);
		
		formData = new FormData();
		formData.width = labelWidth;
		formData.top = new FormAttachment(blueSlider, topWidgetMargin, SWT.CENTER);
		formData.left = new FormAttachment(blueSlider, widgetMargin);
		
		blueLabel.setLayoutData(formData);
		
		formData = new FormData();
		formData.width = textWidth;
		formData.top = new FormAttachment(blueLabel, topWidgetMargin, SWT.CENTER);
		formData.left = new FormAttachment(blueLabel, widgetMargin);
		
		blueText.setLayoutData(formData);
		
		//Color Preview
		formData = new FormData();
		formData.width = 8;
		formData.height = height - (topWidgetMargin * 2);
		formData.left = new FormAttachment(greenText, 5);
		formData.top = new FormAttachment(greenText, 0, SWT.CENTER);
		
		colorLabel.setLayoutData(formData);
	}
	
	public Group getGroup() {return group;}
	
	public TilesetColor getTilesetColor() {return tilesetColor;}
	
	public Slider getRedSlider() {return redSlider;}
	
	public Slider getGreenSlider() {return greenSlider;}
	
	public Slider getBlueSlider() {return blueSlider;}
}