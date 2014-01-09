package com.gmail.nossr50.dwarfartist.eventlistener;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

import com.gmail.nossr50.dwarfartist.datatypes.ColorEditContainer;
import com.gmail.nossr50.dwarfartist.datatypes.ValueType;

public class MyModifyListener implements ModifyListener {
	
	private ValueType valueType;
	private ColorEditContainer colorGroup;
	
	public MyModifyListener(ColorEditContainer colorGroup, ValueType valueType) {
		this.valueType = valueType;
		this.colorGroup = colorGroup;
	}

	@Override
	public void modifyText(ModifyEvent e) {
		Text source = (Text) e.getSource();
		
		//Our verify listener prevents this from ever being a non-number
		int converted = Integer.valueOf(source.getText());
		
		if(converted > 255) {
			converted = 255;
			source.setText(String.valueOf(converted));
		} else if (converted < 0) {
			converted = 0;
			source.setText(String.valueOf(converted));
		}
		
		switch(valueType) {
		case RED:
			//No need to update the slider/enum values if they aren't different
			if(colorGroup.getTilesetColor().getRedValue() != converted) {
				colorGroup.getRedSlider().setSelection(converted);
				colorGroup.getTilesetColor().setRedValue(converted);
				colorGroup.updateColorLabelPreview();
			}
			break;
		case GREEN:
			//No need to update the slider/enum values if they aren't different
			if(colorGroup.getTilesetColor().getGreenValue() != converted) {
				colorGroup.getGreenSlider().setSelection(converted);
				colorGroup.getTilesetColor().setGreenValue(converted);
				colorGroup.updateColorLabelPreview();
			}
			break;
		case BLUE:
			//No need to update the slider/enum values if they aren't different
			if(colorGroup.getTilesetColor().getBlueValue() != converted) {
				colorGroup.getBlueSlider().setSelection(converted);
				colorGroup.getTilesetColor().setBlueValue(converted);
				colorGroup.updateColorLabelPreview();
			}
			break;
		}
	}
}