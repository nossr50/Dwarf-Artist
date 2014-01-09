package com.gmail.nossr50.dwarfartist.datatypes;

import org.eclipse.swt.graphics.RGB;

public enum TilesetColor {
	BLACK,
	BLUE,
	GREEN,
	CYAN,
	RED,
	MAGENTA,
	BROWN,
	LGRAY,
	DGRAY,
	LBLUE,
	LGREEN,
	LCYAN,
	LRED,
	LMAGENTA,
	YELLOW,
	WHITE;
	
	private int red = 0, blue = 0, green = 0;
	
	public int getRedValue() {return red;};
	public void setRedValue(int newValue) {
		if(newValue > 255)
			red = 255;
		else if(newValue < 0)
			red = 0;
		else
			red = newValue;
		
		//System.out.println(this.toString()+" (RED) :"+red);
	}
	
	public int getBlueValue() {return blue;};
	public void setBlueValue(int newValue) {
		if(newValue > 255)
			blue = 255;
		else if(newValue < 0)
			blue = 0;
		else
			blue = newValue;
	}
	
	public int getGreenValue() {return green;};
	public void setGreenValue(int newValue) {
		if(newValue > 255)
			green = 255;
		else if(newValue < 0)
			green = 0;
		else
			green = newValue;
		//System.out.println(this.toString()+" (GREEN) :"+green);
	}
	
	public String getFilePrefixRed() {return "["+this.toString()+"_R:";}
	public String getFilePrefixGreen() {return "["+this.toString()+"_G:";}
	public String getFilePrefixBlue() {return "["+this.toString()+"_B:";}
	
	public String getFileFormattedRed() {return "["+this.toString()+"_R:"+String.valueOf(red)+"]";}
	public String getFileFormattedGreen() {return "["+this.toString()+"_G:"+String.valueOf(green)+"]";}
	public String getFileFormattedBlue() {return "["+this.toString()+"_B:"+String.valueOf(blue)+"]";}
	
	public String getShortFilePrefix() {return "["+this.toString()+"_";}
	
	public char getFilePrefixColor(String string) {return string.charAt(getShortFilePrefixLength());}
	
	public int getShortFilePrefixLength() {return getShortFilePrefix().length();}
	
	public boolean isFilePrefix(String string) {
		if(string.length() >= getShortFilePrefixLength()) {
			for(int pos = 0; pos < getShortFilePrefixLength(); pos++) {
				if(string.charAt(pos) == getShortFilePrefix().charAt(pos)) {
					continue;
				} else {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public String getColorValue(String string) {
		String snipped = string.substring(this.getShortFilePrefixLength());
		String newString = "";
		
		for(char x : snipped.toCharArray()) {
			try {
		        Integer.parseInt(String.valueOf(x));
		        newString+=x;
		    } catch (NumberFormatException nfe) {
		        continue;
		    }
		}
		
		return newString;
	}
	
	public RGB getRGBValue() {return new RGB(red, green, blue);}
}