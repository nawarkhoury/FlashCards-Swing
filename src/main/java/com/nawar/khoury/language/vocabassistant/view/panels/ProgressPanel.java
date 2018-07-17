package com.nawar.khoury.language.vocabassistant.view.panels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nawar.khoury.language.vocabassistant.model.Word;
import com.nawar.khoury.language.vocabassistant.view.custom.CustomPanel;

public class ProgressPanel extends CustomPanel
{
	private JLabel lblTestWordsValue;
	private JLabel lblTestFiledWordsValue;
	private JLabel lblSubtestWordsValue;
	private JLabel lblSubtestFailedWordsValue;
	private CurrentWordInfoPanel panel;
	
	public ProgressPanel()
	{
		super("Progress");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.5, 0.7, 2.0, 0.5, 0.7, 2.0, 0.5, 0.7, 2.0, 0.5, 0.7};
		gridBagLayout.rowWeights = new double[]{0.5, 2.0, 1.0};
		setLayout(gridBagLayout);
		
		JLabel lblTest = new JLabel("Test:");
		lblTest.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lblTest = new GridBagConstraints();
		gbc_lblTest.insets = new Insets(0, 0, 5, 5);
		gbc_lblTest.gridx = 0;
		gbc_lblTest.gridy = 1;
		add(lblTest, gbc_lblTest);
		
		lblTestWordsValue = new JLabel("0");
		lblTestWordsValue.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lblTestWordsValue = new GridBagConstraints();
		gbc_lblTestWordsValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblTestWordsValue.gridx = 1;
		gbc_lblTestWordsValue.gridy = 1;
		add(lblTestWordsValue, gbc_lblTestWordsValue);
		
		JLabel lblTestFailedWords = new JLabel("Test Failed:");
		lblTestFailedWords.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lblTestFailedWords = new GridBagConstraints();
		gbc_lblTestFailedWords.insets = new Insets(0, 0, 5, 5);
		gbc_lblTestFailedWords.gridx = 3;
		gbc_lblTestFailedWords.gridy = 1;
		add(lblTestFailedWords, gbc_lblTestFailedWords);
		
		lblTestFiledWordsValue = new JLabel("0");
		lblTestFiledWordsValue.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lblTestFiledWordsValue = new GridBagConstraints();
		gbc_lblTestFiledWordsValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblTestFiledWordsValue.gridx = 4;
		gbc_lblTestFiledWordsValue.gridy = 1;
		add(lblTestFiledWordsValue, gbc_lblTestFiledWordsValue);
		
		JLabel lblSubtestWords = new JLabel("Subtest: ");
		lblSubtestWords.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lblSubtestWords = new GridBagConstraints();
		gbc_lblSubtestWords.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubtestWords.gridx = 6;
		gbc_lblSubtestWords.gridy = 1;
		add(lblSubtestWords, gbc_lblSubtestWords);
		
		lblSubtestWordsValue = new JLabel("0");
		lblSubtestWordsValue.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lblSubtestWordsValue = new GridBagConstraints();
		gbc_lblSubtestWordsValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubtestWordsValue.gridx = 7;
		gbc_lblSubtestWordsValue.gridy = 1;
		add(lblSubtestWordsValue, gbc_lblSubtestWordsValue);
		
		JLabel lblSubtestFailedWords = new JLabel("Subtest Failed: ");
		lblSubtestFailedWords.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lblSubtestFailedWords = new GridBagConstraints();
		gbc_lblSubtestFailedWords.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubtestFailedWords.gridx = 9;
		gbc_lblSubtestFailedWords.gridy = 1;
		add(lblSubtestFailedWords, gbc_lblSubtestFailedWords);
		
		lblSubtestFailedWordsValue = new JLabel("0");
		lblSubtestFailedWordsValue.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lblSubtestMissedWordsValue = new GridBagConstraints();
		gbc_lblSubtestMissedWordsValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubtestMissedWordsValue.gridx = 10;
		gbc_lblSubtestMissedWordsValue.gridy = 1;
		add(lblSubtestFailedWordsValue, gbc_lblSubtestMissedWordsValue);
		
		panel = new CurrentWordInfoPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 12;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
	}
	
	public void setCurrentWord(Word word)
	{
		panel.setWordInformation(word);
	}
	
	public void setProgress(int total, int totalFailed, int subtest, int subtestFailed)
	{
		this.lblTestWordsValue.setText("" + total);
		this.lblTestFiledWordsValue.setText("" + totalFailed);
		this.lblSubtestWordsValue.setText("" + subtest);
		this.lblSubtestFailedWordsValue.setText("" + subtestFailed);
	}
}
