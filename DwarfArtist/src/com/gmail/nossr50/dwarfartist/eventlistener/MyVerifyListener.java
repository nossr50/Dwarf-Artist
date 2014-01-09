package com.gmail.nossr50.dwarfartist.eventlistener;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class MyVerifyListener implements VerifyListener
{
	@Override
	public void verifyText(VerifyEvent e)
	{
	    // Get the source widget
	    Text source = (Text) e.getSource();

	    // Get the text
	    final String oldString = source.getText();
	    final String newString = oldString.substring(0, e.start) + e.text + oldString.substring(e.end);

	    try {
	        Integer.parseInt(String.valueOf(newString));
	        //Object is an integer
	    } catch (NumberFormatException nfe) {
	        e.doit = false;
	        //Object is not an integer
	    }
	}
}