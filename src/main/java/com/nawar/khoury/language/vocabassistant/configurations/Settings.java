package com.nawar.khoury.language.vocabassistant.configurations;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Settings
{
	public static final String version = "1.3";
	
	public static String primaryLanguage = "English";
	public static String secondaryLanguage = "German";
	public static int subsetSize = 4;
	public static boolean firstWordMatching = false;
	
	public static final String DB_NAME = "data/db/new.db";
	public static final String TABLE_NAME_WORDS = "words";
	
	private static final String PROPERTY_PRIMARY_LANGUAGE = "IamLearningItFrom";
	private static final String PROPERTY_SECONDARY_LANGUAGE = "LanguageIamLearning";
	private static final String PROPERTY_SUBSET_SIZE = "HowManyWordsAtATime";
	private static final String PROPERTY_FIRST_WORD_MATCHING = "FirstWordMatching";
	
	static
	{
		Properties prop = new Properties();
		InputStream inStream;
		try
		{
			inStream = new FileInputStream("flashcards.properties");
			prop.load(inStream);
			
			primaryLanguage = prop.getProperty(PROPERTY_PRIMARY_LANGUAGE);
			secondaryLanguage = prop.getProperty(PROPERTY_SECONDARY_LANGUAGE);
			subsetSize = Integer.parseInt(prop.getProperty(PROPERTY_SUBSET_SIZE));
			firstWordMatching = Boolean.parseBoolean(prop.getProperty(PROPERTY_FIRST_WORD_MATCHING));
			
			inStream.close();
			
		} catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Invalid properties file, please review the properties file again.\nreason: " + e.getMessage());
			System.exit(1);
		}
	}
}
