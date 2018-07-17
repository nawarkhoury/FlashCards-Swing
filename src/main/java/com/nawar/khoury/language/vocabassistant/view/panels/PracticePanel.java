package com.nawar.khoury.language.vocabassistant.view.panels;

import static com.nawar.khoury.language.vocabassistant.view.GuiUtilities.sanitizeLongWord;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nawar.khoury.language.vocabassistant.configurations.Settings;
import com.nawar.khoury.language.vocabassistant.model.Word;
import com.nawar.khoury.language.vocabassistant.view.custom.CustomPanel;

public class PracticePanel extends CustomPanel
{
	private PracticePanelListener listener = null;
	private JTextField textSecondaryLanguage;
	private JButton btnSubmit;
	private JLabel lblPrimaryLanguage;
	
	private Word currentQuestion;
	
	public PracticePanel()
	{
		super("Practice");
		
		Dimension d = this.getPreferredSize();
		d.height = 270;
		this.setPreferredSize(new Dimension(541, 283));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.5, 1.0, 1.5, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblEnterTheSecondary = new JLabel("Enter the "+Settings.secondaryLanguage+" translation of the following "+Settings.primaryLanguage+" words");
		lblEnterTheSecondary.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lblEnterTheSecondary = new GridBagConstraints();
		gbc_lblEnterTheSecondary.insets = new Insets(0, 0, 5, 0);
		gbc_lblEnterTheSecondary.gridx = 0;
		gbc_lblEnterTheSecondary.gridy = 0;
		add(lblEnterTheSecondary, gbc_lblEnterTheSecondary);
		
		lblPrimaryLanguage = new JLabel("English word goes here");
		lblPrimaryLanguage.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_lblPrimary = new GridBagConstraints();
		gbc_lblPrimary.insets = new Insets(0, 0, 5, 0);
		gbc_lblPrimary.gridx = 0;
		gbc_lblPrimary.gridy = 2;
		add(lblPrimaryLanguage, gbc_lblPrimary);
		
		textSecondaryLanguage = new JTextField();
		textSecondaryLanguage.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_textSecLang = new GridBagConstraints();
		gbc_textSecLang.insets = new Insets(0, 0, 5, 0);
		gbc_textSecLang.fill = GridBagConstraints.HORIZONTAL;
		gbc_textSecLang.gridx = 0;
		gbc_textSecLang.gridy = 4;
		add(textSecondaryLanguage, gbc_textSecLang);
		textSecondaryLanguage.setColumns(10);
		
		CustomPanel panel = new CustomPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnClose.addActionListener((event) -> {
			if(listener != null)
				listener.close();
		});
		panel.add(btnClose);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSubmit.addActionListener((event) -> {
			listener.submitAnswer(textSecondaryLanguage.getText());
			textSecondaryLanguage.setText("");
		});
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener((event) -> {
			EditWordsDialog edit = new EditWordsDialog(currentQuestion);
			edit.setVisible(true);
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(btnEdit);
		panel.add(btnSubmit);
		
	}
	
	public PracticePanelListener getListener()
	{
		return listener;
	}
	public void setListener(PracticePanelListener listener)
	{
		this.listener = listener;
	}
	public JButton getDefaultButton()
	{
		return btnSubmit;
	}
	public void displayQuestion(Word question)
	{
		lblPrimaryLanguage.setText(question.getPrimary());
		currentQuestion = question;
		textSecondaryLanguage.requestFocus();
	}
}
