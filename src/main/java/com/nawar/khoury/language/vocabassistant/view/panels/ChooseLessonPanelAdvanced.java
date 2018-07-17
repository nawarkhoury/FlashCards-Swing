package com.nawar.khoury.language.vocabassistant.view.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;

import com.nawar.khoury.language.vocabassistant.view.GuiUtilities;
import com.nawar.khoury.language.vocabassistant.view.custom.CustomPanel;

public class ChooseLessonPanelAdvanced extends CustomPanel
{
	private JButton btnNext;
	
	private JComboBox<String> lessonGroupBox;
	private JComboBox<String> lessonNumberBox;
	private JComboBox<String> wordsType;
	private JSlider sliderNumberOfWords;
	
	private ChooseLessonPanelListener listener;
	
	public ChooseLessonPanelAdvanced() throws Exception
	{
		super("Choose the criteria for the words to practice");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		lessonGroupBox = new JComboBox<String>(GuiUtilities.getLessonGroups());
		lessonGroupBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_wordsType = new GridBagConstraints();
		gbc_wordsType.insets = new Insets(0, 0, 5, 5);
		gbc_wordsType.fill = GridBagConstraints.HORIZONTAL;
		gbc_wordsType.gridx = 1;
		gbc_wordsType.gridy = 1;
		add(lessonGroupBox, gbc_wordsType);
		lessonGroupBox.addActionListener((event) -> {
			try
			{
				lessonNumberBox.setModel(GuiUtilities.getLessonNumbers("" + lessonGroupBox.getSelectedItem()));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		});
		
		lessonNumberBox = new JComboBox<String>();
		lessonNumberBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		lessonNumberBox.setModel(GuiUtilities.getLessonNumbers("" + lessonGroupBox.getSelectedItem()));
		
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 2;
		add(lessonNumberBox, gbc_comboBox_1);
		
		wordsType = new JComboBox<String>();
		wordsType.setModel(GuiUtilities.getWordTypesWithEmptyOption());
		wordsType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_wordsType2 = new GridBagConstraints();
		gbc_wordsType2.insets = new Insets(0, 0, 5, 5);
		gbc_wordsType2.fill = GridBagConstraints.HORIZONTAL;
		gbc_wordsType2.gridx = 1;
		gbc_wordsType2.gridy = 3;
		add(wordsType, gbc_wordsType2);
		
		sliderNumberOfWords = new JSlider();
		sliderNumberOfWords.setSnapToTicks(true);
		sliderNumberOfWords.setPaintLabels(true);
		sliderNumberOfWords.setPaintTicks(true);
		sliderNumberOfWords.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		sliderNumberOfWords.setToolTipText("Number of words to practice");
		sliderNumberOfWords.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sliderNumberOfWords.setValue(20);
		sliderNumberOfWords.setMinimum(4);
		sliderNumberOfWords.setMaximum(200);
		sliderNumberOfWords.setMajorTickSpacing(20);
		sliderNumberOfWords.setMinorTickSpacing(4);
		
		GridBagConstraints gbc_sliderNumberOfWords = new GridBagConstraints();
		gbc_sliderNumberOfWords.fill = GridBagConstraints.BOTH;
		gbc_sliderNumberOfWords.insets = new Insets(0, 0, 5, 5);
		gbc_sliderNumberOfWords.gridx = 1;
		gbc_sliderNumberOfWords.gridy = 4;
		add(sliderNumberOfWords, gbc_sliderNumberOfWords);
		
		CustomPanel panel = new CustomPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 5;
		add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnBack.addActionListener((event) -> {
			if(listener != null)
				listener.close();
		});
		panel.add(btnBack);
		
		btnNext = new JButton("Next");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNext.addActionListener((event) -> {
			if(listener != null)
				listener.next(lessonGroupBox.getSelectedItem().toString(), lessonNumberBox.getSelectedItem().toString(), wordsType.getSelectedItem().toString(), sliderNumberOfWords.getValue());
		});
		panel.add(btnNext);
	}

	public void setListener(ChooseLessonPanelListener listener)
	{
		this.listener = listener;
	}

	public JButton getDefaultButton()
	{
		return btnNext;
	}
	
	public void refresh()
	{
		try
		{
			lessonGroupBox.setModel(GuiUtilities.getLessonGroups());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
