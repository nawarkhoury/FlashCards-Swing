package com.nawar.khoury.language.vocabassistant.view.panels;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.nawar.khoury.language.vocabassistant.view.GuiUtilities;
import com.nawar.khoury.language.vocabassistant.view.custom.CustomPanel;

public class ChooseLessonPanel extends CustomPanel
{
	private JButton btnNext;
	
	JComboBox<String> lessonGroupBox;
	JComboBox<String> lessonNumberBox;
	
	public ChooseLessonPanel() throws Exception
	{
		super("Choose your Lesson");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lessonNumberBox = new JComboBox<String>();
		lessonNumberBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lessonGroupBox = new JComboBox<String>(GuiUtilities.getLessonGroups());
		lessonGroupBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		add(lessonGroupBox, gbc_comboBox);
		
		lessonNumberBox.setModel(GuiUtilities.getLessonNumbers("" + lessonGroupBox.getSelectedItem()));
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
		
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 3;
		add(lessonNumberBox, gbc_comboBox_1);
		
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
				listener.next(lessonGroupBox.getSelectedItem().toString(), lessonNumberBox.getSelectedItem().toString(), null, null);
		});
		panel.add(btnNext);
	}

	private ChooseLessonPanelListener listener;

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
