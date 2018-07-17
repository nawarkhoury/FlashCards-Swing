package com.nawar.khoury.language.vocabassistant.view.panels;

public interface ChooseLessonPanelListener
{
	public void close();
	public void next(String lessonGroup, String lessonName, String wordsType, Integer limit);
}
