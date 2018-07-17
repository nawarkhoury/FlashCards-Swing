package com.nawar.khoury.language.vocabassistant.controller;

import java.util.Arrays;
import java.util.LinkedList;

import com.nawar.khoury.language.vocabassistant.model.Word;

public class WordsAddManager
{
	private LinkedList<Word> words;
	private String lessonGroup;
	private String lessonNumber;
	
	public WordsAddManager()
	{
		words = new LinkedList<Word>();
	}
	
	public void addNewWord(Word word) throws Exception
	{
		if(lessonGroup == null || lessonNumber == null)
		{
			lessonGroup = word.getLessonGroup();
			lessonNumber = word.getLessonNumber();
			refreshWords();
		}
		
		DatabaseManager.getInstance().addNewWord(word);
		words.add(word);
	}
	
	public void setCurrentLesson(String lessonGroup, String lessonNumber) throws Exception
	{
		this.lessonGroup = lessonGroup;
		this.lessonNumber = lessonNumber;
		refreshWords();
	}
	
	public void refreshWords() throws Exception
	{
		Word[] lessonWords = DatabaseManager.getInstance().getLessonWords(lessonGroup, lessonNumber, null);
		words.clear();
		words.addAll(Arrays.asList(lessonWords));
	}
	
	public void removeWord(String promary, String secondary) throws Exception
	{
		DatabaseManager.getInstance().removeWord(promary, secondary, lessonGroup, lessonNumber);
		refreshWords();
	}
	
	public LinkedList<Word> getLessonWords()
	{
		return words;
	}
}
