package com.nawar.khoury.language.vocabassistant.view.panels;

import static com.nawar.khoury.language.vocabassistant.view.GuiUtilities.sanitizeLongWord;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;

import com.nawar.khoury.language.vocabassistant.model.Word;
import com.nawar.khoury.language.vocabassistant.view.custom.CustomPanel;

public class CurrentWordInfoPanel extends CustomPanel
{
	private JLabel lblLessonGroup;
	private JLabel lblLessonName;
	private JLabel lblWordType;

	public CurrentWordInfoPanel()
	{
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_this.rowHeights = new int[]{19, 0};
		gbl_this.columnWeights = new double[]{1.0, 0.2, 1.0, 0.2, 1.0};
		gbl_this.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		this.setLayout(gbl_this);
		
		lblLessonGroup = new JLabel("Lesson Group");
		lblLessonGroup.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblLessonGroup = new GridBagConstraints();
		gbc_lblLessonGroup.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblLessonGroup.insets = new Insets(0, 0, 0, 5);
		gbc_lblLessonGroup.gridx = 0;
		gbc_lblLessonGroup.gridy = 0;
		this.add(lblLessonGroup, gbc_lblLessonGroup);
		
		lblLessonName = new JLabel("Lesson Name");
		lblLessonName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblLessonName = new GridBagConstraints();
		gbc_lblLessonName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblLessonName.insets = new Insets(0, 0, 0, 5);
		gbc_lblLessonName.gridx = 2;
		gbc_lblLessonName.gridy = 0;
		this.add(lblLessonName, gbc_lblLessonName);
		
		lblWordType = new JLabel("WordType");
		lblWordType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblWordType = new GridBagConstraints();
		gbc_lblWordType.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblWordType.gridx = 4;
		gbc_lblWordType.gridy = 0;
		this.add(lblWordType, gbc_lblWordType);
	}
	
	public void setWordInformation(Word question)
	{
		lblLessonGroup.setText("<html>Group: "+sanitizeLongWord(question.getLessonGroup())+"</html>");
		lblLessonName.setText("<html>Lesson: "+sanitizeLongWord(question.getLessonNumber())+"</html>");
		lblWordType.setText("<html>Word Type: "+sanitizeLongWord(question.getType()) + "("+question.getScore()+")"+"</html>");
	}
}
