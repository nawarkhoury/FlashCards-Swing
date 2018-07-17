package com.nawar.khoury.language.vocabassistant.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import com.nawar.khoury.language.vocabassistant.configurations.Settings;
import com.nawar.khoury.language.vocabassistant.model.Word;

public class Tester 
{
	private boolean shuffleEnabled;
	private Word[] Words;
	
	private ArrayList<Word> WordList;
	private ArrayList<Word> failedWordList;
	
	private ArrayList<Word> currentSubset;
	private ArrayList<Word> failedCurrentSubset;
	
	private static ArrayList<String> specialWords = new ArrayList<String>(Arrays.asList(new String[] {
		"SICH", "DER", "DIE", "DAS"	
	}));
	
	private Word currentQuestion;
	
	private long startDate;
	private long endDate;
	
//	private int numberOfFailedRetests;
	
//	private boolean lastQuestionSuccess = true;
//	private boolean incrementSuccessScore = true;
	
	public Tester(String lessonGroup, String lessonNumber, String type, Integer limit, boolean shuffleEnabled) throws Exception
	{
		this.shuffleEnabled = shuffleEnabled;
		
		//TODO add limit and type
		this.Words = DatabaseManager.getInstance().getLessonWords(lessonGroup, lessonNumber, limit);
		
		initializeQuestionLists();
//		lastQuestionSuccess = true;
//		incrementSuccessScore = true;
		startDate = System.currentTimeMillis();
//		numberOfFailedRetests = 0;
	}
	
	public Word nextQuestion()
	{
		return currentQuestion;
	}
	
	private void shiftSubset()
	{
		currentSubset = new ArrayList<Word>();
		failedCurrentSubset = new ArrayList<Word>();
//		for(int i=0; (i<SUBSET_SIZE && i<WordList.size()); i++)
		while(currentSubset.size() < Settings.subsetSize && !WordList.isEmpty())
		{
			Word word = WordList.get(getRandomNumber(WordList.size()));
			currentSubset.add(word);
			WordList.remove(word);
		}
	}
	
	public boolean submit(String answer)
	{
		if(validateAnswerAgainstExpected(answer, currentQuestion.getSecondary()))
		{
			rightAnswer();
			return true;
		}
		else
		{
			wrongAnswer();
			return false;
		}
	}
	
	private void rightAnswer()
	{
		currentSubset.remove(currentQuestion);
		
		if(!currentQuestion.isPrompted())
		{
			try
			{
				DatabaseManager.getInstance().incrementWordScore(currentQuestion);
				currentQuestion.setScore(currentQuestion.getScore() + 2);//hack just to keep the displayed score accurate on the UI
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Score was not updated in the database. " + ex.getMessage());
			}
		}
		
		currentQuestion.markAsPrompted();
		currentQuestion = getRandomQuestion();
		if(currentQuestion == null)
			endDate = System.currentTimeMillis();
	}
	
	private void wrongAnswer()
	{
		if(!failedWordList.contains(currentQuestion))
		{
			failedWordList.add(currentQuestion);
		}
		
		currentQuestion.markAsPrompted();
		try
		{
			DatabaseManager.getInstance().decrementWordScore(currentQuestion);
			currentQuestion.setScore(currentQuestion.getScore() - 1);//hack just to keep the score displayed accurate on the UI
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Score was not updated in the database. " + ex.getMessage());
		}
		failedCurrentSubset.add(currentQuestion);
	}

	private void initializeQuestionLists()
	{
		WordList = new ArrayList<Word>(Arrays.asList(Words));
		failedWordList = new ArrayList<Word>();
		shiftSubset();
		currentQuestion = getRandomQuestion();
	}

	private Word getRandomQuestion() 
	{
		if(currentSubset.size() == 0)
		{
			if(failedCurrentSubset.size() == 0)
			{
				if(WordList.size() == 0)
				{
					if(failedWordList.size() == 0)
					{
						return null;
					}
					else
					{
						WordList.addAll(failedWordList);
//						if(numberOfFailedRetests>0)
						failedWordList.clear();
//						numberOfFailedRetests ++;
						shiftSubset();
					}
				}
				else
				{
					shiftSubset();
				}
			}
			else
			{
				currentSubset.addAll(failedCurrentSubset);
				failedCurrentSubset.clear();
			}
		}
		
		int nextQuestionIndex = 0;
		if(shuffleEnabled)
			nextQuestionIndex = getRandomNumber(currentSubset.size());
		
		return currentSubset.get(nextQuestionIndex);
	}

	protected boolean validateAnswerAgainstExpected(String answer, String expected)
	{
		if(isStringSimilar(answer, expected))
			return true;
		else if(Settings.firstWordMatching)
		{
			String expectedShort = expected.split(" ")[0];
			if(isStringSimilar(answer, expectedShort) && !specialWords.contains(answer.trim().toUpperCase()))
				return true;
			return false;
		}
		else
			return false;
	}

	private boolean isStringSimilar(String answer, String expected) 
	{
		answer = removeNonAlphaNumeric(answer);
		expected = removeNonAlphaNumeric(expected);
		return answer.equalsIgnoreCase(expected);
	}

	private String removeNonAlphaNumeric(String string) 
	{
		return string.replaceAll("[^a-zA-Z]", "");
	}

	public String getElapsedTime()
	{
		long millis = endDate - startDate;
		long second = (millis / 1000) % 60;
		long minute = (millis / (1000 * 60)) % 60;
		long hour = (millis / (1000 * 60 * 60)) % 24;

		return String.format("%02d:%02d:%02d", hour, minute, second);
	}
	
	public static int getRandomNumber(int max)
	{
		int rand = ((int)System.currentTimeMillis()) % max;
		if(rand < 0)
			return -1 * rand;
		else
			return rand;
	}
	
	public Word getCurrentQuestion()
	{
		return currentQuestion;
	}
	
	public int getTotalSize()
	{
		return WordList.size();
	}
	
	public int failedSize()
	{
		return failedWordList.size();
	}
	
	public int currentSubsetSize()
	{
		return currentSubset.size();
	}
	
	public int currentFailedSubsetSize()
	{
		return failedCurrentSubset.size();
	}
}
