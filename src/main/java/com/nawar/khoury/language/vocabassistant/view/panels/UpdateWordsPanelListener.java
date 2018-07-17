package com.nawar.khoury.language.vocabassistant.view.panels;

public interface UpdateWordsPanelListener
{
	public void close();
	public void save(String english, String german, String type, String lessonGroup, String lessonNumber) throws Exception;
	public void lessonSelected(String lessonGroup, String lessonNumber) throws Exception;
}
