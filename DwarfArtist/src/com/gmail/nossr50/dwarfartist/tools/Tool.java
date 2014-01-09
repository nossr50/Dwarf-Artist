package com.gmail.nossr50.dwarfartist.tools;

import com.gmail.nossr50.dwarfartist.datatypes.TilesetColor;

public class Tool {
	public static void Brighten(TilesetColor tilesetColor) {
		double doubleRed = (double) tilesetColor.getRedValue(), 
				doubleGreen = (double) tilesetColor.getGreenValue(), 
				doubleBlue = (double) tilesetColor.getBlueValue();
		
		doubleRed = doubleRed + (doubleRed * .10);
		doubleGreen = doubleGreen + (doubleGreen * .10);
		doubleBlue = doubleBlue + (doubleBlue * .10);
		
		int red =  (int) doubleRed;
		int green = (int) doubleGreen;
		int blue = (int) doubleBlue;
		
		tilesetColor.setRedValue(red);
		tilesetColor.setGreenValue(green);
		tilesetColor.setBlueValue(blue);
	}
	
	public static void Darken(TilesetColor tilesetColor) {
		double doubleRed = (double) tilesetColor.getRedValue(), 
				doubleGreen = (double) tilesetColor.getGreenValue(), 
				doubleBlue = (double) tilesetColor.getBlueValue();
		
		doubleRed = doubleRed - (doubleRed * .10);
		doubleGreen = doubleGreen - (doubleGreen * .10);
		doubleBlue = doubleBlue - (doubleBlue * .10);
		
		int red =  (int) doubleRed;
		int green = (int) doubleGreen;
		int blue = (int) doubleBlue;
		
		tilesetColor.setRedValue(red);
		tilesetColor.setGreenValue(green);
		tilesetColor.setBlueValue(blue);
	}
}