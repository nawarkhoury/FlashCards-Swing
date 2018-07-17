package com.nawar.khoury.language.vocabassistant.view.panels;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.nawar.khoury.language.vocabassistant.controller.WordsUpdateManager;
import com.nawar.khoury.language.vocabassistant.model.Word;

public class EditWordsDialog extends JDialog
{
	private UpdateWordsPanel wordPanel;
	private String originalPrimary;
	private String orginalSecondary;
	
	public EditWordsDialog(Word wordToUpdate)
	{
		try
		{
			wordPanel = new UpdateWordsPanel(wordToUpdate);
			originalPrimary = wordToUpdate.getPrimary();
			orginalSecondary = wordToUpdate.getSecondary();
			layoutDialog();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Unable to initialize the edit words dialog, reason: " + ex.getMessage(), "Error initializing edit dialog", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void layoutDialog()
	{
		getContentPane().setLayout(new BorderLayout(0, 0));
		setBounds(300, 200, 450, 300);
//		setResizable(false);
		
		getContentPane().add(wordPanel, BorderLayout.CENTER);
		
		this.getRootPane().setDefaultButton(wordPanel.getDefaultButton());
		
		wordPanel.setListener(new UpdateWordsPanelListener()
		{
			@Override
			public void save(String english, String german, String type, String lessonGroup, String lessonNumber)
					throws Exception
			{
				WordsUpdateManager.updateWord(originalPrimary, orginalSecondary, english, german, type, lessonGroup, lessonNumber);
				dispose();
			}
			
			@Override
			public void lessonSelected(String lessonGroup, String lessonNumber) throws Exception
			{
				// nothing to do.
			}
			
			@Override
			public void close()
			{
				dispose();
			}
		});
	}
}
