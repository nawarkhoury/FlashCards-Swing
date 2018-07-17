package com.nawar.khoury.language.vocabassistant.view;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import com.nawar.khoury.language.vocabassistant.controller.DatabaseManager;

public class GuiUtilities
{
	public static ComboBoxModel<String> getLessonGroups() throws Exception
	{
		DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel<String>();
		String[] lessonGroups = DatabaseManager.getInstance().getLessonGroups();
		
		if(lessonGroups == null)
			return empModel;
		
		empModel.addElement("");
		for(int i=0; i<lessonGroups.length; i++)
			empModel.addElement(lessonGroups[i]);
		return empModel;
	}

	public static ComboBoxModel<String> getLessonNumbers(String lessonGroup) throws Exception
	{
		DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel<String>();
		String[] lessonNumber = DatabaseManager.getInstance().getLessonNumbers(lessonGroup);
		
		if(lessonNumber == null)
			return empModel;
		
		empModel.addElement("");
		for(int i=0; i<lessonNumber.length; i++)
			empModel.addElement(lessonNumber[i]);
		return empModel;
	}
	
	public static ComboBoxModel<String> getWordTypes() throws Exception
	{
		DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel<String>();
		String[] wordTypes = DatabaseManager.getInstance().getWordTypes();
		for(int i=0; i<wordTypes.length; i++)
			empModel.addElement(wordTypes[i]);
		return empModel;
	}
	
	public static ComboBoxModel<String> getWordTypesWithEmptyOption() throws Exception
	{
		DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel<String>();
		String[] wordTypes = DatabaseManager.getInstance().getWordTypes();
		empModel.addElement("");
		for(int i=0; i<wordTypes.length; i++)
			empModel.addElement(wordTypes[i]);
		return empModel;
	}
	
	public static String sanitizeLongWord(String word)
	{
		if(word.length() < 15)
			return word;
		else
			return word.substring(0, 13) + "...";
	}
}
