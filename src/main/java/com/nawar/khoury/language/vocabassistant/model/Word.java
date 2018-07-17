package com.nawar.khoury.language.vocabassistant.model;

public class Word
{
	private String primary;
	private String secondary;
	private String type;
	private String lessonGroup;
	private String lessonNumber;
	private int score;
	
	private boolean prompted = false;
	
	public Word()
	{
		
	}
	
	public String getPrimary()
	{
		return primary;
	}
	public void setPrimary(String english)
	{
		this.primary = english;
	}
	public String getSecondary()
	{
		return secondary;
	}
	public void setSecondary(String german)
	{
		this.secondary = german;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getLessonGroup()
	{
		return lessonGroup;
	}
	public void setLessonGroup(String lessonGroup)
	{
		this.lessonGroup = lessonGroup;
	}
	public String getLessonNumber()
	{
		return lessonNumber;
	}
	public void setLessonNumber(String lessonNumber)
	{
		this.lessonNumber = lessonNumber;
	}
	
	public void markAsPrompted()
	{
		this.prompted = true;
	}
	public boolean isPrompted()
	{
		return prompted;
	}
	
	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public Word(String primary, String secondary, String type, String lessonGroup, String lessonNumber)
	{
		super();
		this.primary = primary;
		this.secondary = secondary;
		this.type = type;
		this.lessonGroup = lessonGroup;
		this.lessonNumber = lessonNumber;
	}

	@Override
	public String toString()
	{
		return "Word [primary=" + primary + ", secondary=" + secondary + ", type=" + type + ", lessonGroup="
				+ lessonGroup + ", lessonNumber=" + lessonNumber + ", score=" + score + ", prompted=" + prompted + "]";
	}

	
}
