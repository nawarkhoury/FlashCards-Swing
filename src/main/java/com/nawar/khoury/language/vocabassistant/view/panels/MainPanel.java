package com.nawar.khoury.language.vocabassistant.view.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;

import com.nawar.khoury.language.vocabassistant.view.custom.CustomButton;
import com.nawar.khoury.language.vocabassistant.view.custom.CustomPanel;

public class MainPanel extends CustomPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainPanelListener frameListener = null;
	private CustomButton btnPractice;
	private CustomButton btnAddWords;

	public MainPanel()
	{
		super(null);
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnAddWords = new CustomButton("Manage Words");
		btnAddWords.setFont(new Font("Palatino Linotype", Font.PLAIN, 21));
		btnAddWords.setPreferredSize(new Dimension(200, 100));
		btnAddWords.addActionListener((event) -> {
			if (frameListener != null)
				frameListener.enterWords();
		});
		add(btnAddWords);

		btnPractice = new CustomButton("Practice");
		btnPractice.setFont(new Font("Palatino Linotype", Font.PLAIN, 21));
		btnPractice.setPreferredSize(new Dimension(200, 100));
		btnPractice.addActionListener((event) -> {
			if (frameListener != null)
				frameListener.practice();
		});
		add(btnPractice);
	}

	public void setListener(MainPanelListener frameListener)
	{
		this.frameListener = frameListener;
	}

	public JButton getDefaultButton()
	{
		return btnPractice;
	}

}
