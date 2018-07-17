package com.nawar.khoury.language.vocabassistant.view.custom;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.nawar.khoury.language.vocabassistant.configurations.Styling;

public class CustomPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomPanel()
	{
		this(null);
	}
	
	public CustomPanel(String name)
	{
		super();
//		setBackground(Styling.themeColor);
		
		if(name != null && !name.isEmpty())
		{
			Border innerBorder = BorderFactory.createTitledBorder(name);
			Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
			setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		}
	}
}
