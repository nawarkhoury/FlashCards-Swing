package com.nawar.khoury.language.vocabassistant.controller;

import com.nawar.khoury.language.vocabassistant.model.Word;

public class WordsUpdateManager
{
	public static void updateWord(String originalPrimary, String originalSecondary, String primary, String secondary, String type, String lessonGroup, String lessonNumber) throws Exception
	{
		Word word = new Word(primary, secondary, type, lessonGroup, lessonNumber);
		DatabaseManager.getInstance().updateWord(originalPrimary, originalSecondary, word);
	}
}
