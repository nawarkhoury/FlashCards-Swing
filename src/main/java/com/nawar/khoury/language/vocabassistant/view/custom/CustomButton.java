package com.nawar.khoury.language.vocabassistant.view.custom;

import javax.swing.JButton;

import com.nawar.khoury.language.vocabassistant.configurations.Styling;

public class CustomButton extends JButton
{
	public CustomButton(String text)
	{
		super(text);
		setBackground(Styling.buttonColor);
	}
}
