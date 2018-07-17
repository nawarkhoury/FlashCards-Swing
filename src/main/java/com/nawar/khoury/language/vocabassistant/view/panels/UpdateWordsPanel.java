package com.nawar.khoury.language.vocabassistant.view.panels;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nawar.khoury.language.vocabassistant.configurations.Settings;
import com.nawar.khoury.language.vocabassistant.controller.DatabaseManager;
import com.nawar.khoury.language.vocabassistant.model.Word;
import com.nawar.khoury.language.vocabassistant.view.GuiUtilities;
import com.nawar.khoury.language.vocabassistant.view.custom.CustomPanel;

public class UpdateWordsPanel extends CustomPanel
{
	private UpdateWordsPanelListener listener = null;

	private JTextField textSecondary;
	private JTextField textPrimary;
	private JComboBox<String> comboLessonNumber;
	private JComboBox<String> comboType;
	private JComboBox<String> comboLessonGroup;
	
	private JButton btnSave;

	public UpdateWordsPanel(Word word) throws Exception
	{
		this();
		textSecondary.setText(word.getSecondary());
		textPrimary.setText(word.getPrimary());
		comboType.setSelectedItem(word.getType());
		comboLessonGroup.setSelectedItem(word.getLessonGroup());
		comboLessonNumber.setSelectedItem(word.getLessonNumber());
	}
	
	public UpdateWordsPanel() throws Exception
	{
		super("Words Management");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]
		{ 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[]
		{ 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[]
		{ 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblSecondary = new JLabel(Settings.secondaryLanguage);
		lblSecondary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblSecondary = new GridBagConstraints();
		gbc_lblSecondary.anchor = GridBagConstraints.EAST;
		gbc_lblSecondary.insets = new Insets(0, 0, 5, 5);
		gbc_lblSecondary.gridx = 1;
		gbc_lblSecondary.gridy = 1;
		add(lblSecondary, gbc_lblSecondary);

		textSecondary = new JTextField();
		textSecondary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_textSecondary = new GridBagConstraints();
		gbc_textSecondary.insets = new Insets(0, 0, 5, 5);
		gbc_textSecondary.fill = GridBagConstraints.HORIZONTAL;
		gbc_textSecondary.gridx = 2;
		gbc_textSecondary.gridy = 1;
		add(textSecondary, gbc_textSecondary);
		textSecondary.setColumns(10);

		JLabel lblPrimary = new JLabel(Settings.primaryLanguage);
		lblPrimary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblPrimary = new GridBagConstraints();
		gbc_lblPrimary.anchor = GridBagConstraints.EAST;
		gbc_lblPrimary.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrimary.gridx = 1;
		gbc_lblPrimary.gridy = 2;
		add(lblPrimary, gbc_lblPrimary);

		textPrimary = new JTextField();
		textPrimary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_textPrimary = new GridBagConstraints();
		gbc_textPrimary.insets = new Insets(0, 0, 5, 5);
		gbc_textPrimary.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPrimary.gridx = 2;
		gbc_textPrimary.gridy = 2;
		add(textPrimary, gbc_textPrimary);
		textPrimary.setColumns(10);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 1;
		gbc_lblType.gridy = 3;
		add(lblType, gbc_lblType);

		comboType = new JComboBox<String>(GuiUtilities.getWordTypes());
		comboType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboType.setEditable(true);
		GridBagConstraints gbc_comboType = new GridBagConstraints();
		gbc_comboType.insets = new Insets(0, 0, 5, 5);
		gbc_comboType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboType.gridx = 2;
		gbc_comboType.gridy = 3;
		add(comboType, gbc_comboType);

		JLabel lblLessonGroup = new JLabel("Lesson Group");
		lblLessonGroup.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblLessonGroup = new GridBagConstraints();
		gbc_lblLessonGroup.anchor = GridBagConstraints.EAST;
		gbc_lblLessonGroup.insets = new Insets(0, 0, 5, 5);
		gbc_lblLessonGroup.gridx = 1;
		gbc_lblLessonGroup.gridy = 4;
		add(lblLessonGroup, gbc_lblLessonGroup);
		
		comboLessonNumber = new JComboBox<String>();
		comboLessonNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboLessonGroup = new JComboBox<String>();
		comboLessonGroup.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboLessonGroup.setModel(GuiUtilities.getLessonGroups());
		comboLessonGroup.setEditable(true);
		comboLessonGroup.addActionListener((event) -> {
			try
			{
				comboLessonNumber.setModel(GuiUtilities.getLessonNumbers(comboLessonGroup.getSelectedItem().toString()));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		});
		GridBagConstraints gbc_comboLessonGroup = new GridBagConstraints();
		gbc_comboLessonGroup.insets = new Insets(0, 0, 5, 5);
		gbc_comboLessonGroup.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboLessonGroup.gridx = 2;
		gbc_comboLessonGroup.gridy = 4;
		add(comboLessonGroup, gbc_comboLessonGroup);

		JLabel lblLessonNumber = new JLabel("Lesson Number");
		lblLessonNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblLessonNumber = new GridBagConstraints();
		gbc_lblLessonNumber.anchor = GridBagConstraints.EAST;
		gbc_lblLessonNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblLessonNumber.gridx = 1;
		gbc_lblLessonNumber.gridy = 5;
		add(lblLessonNumber, gbc_lblLessonNumber);


		comboLessonNumber.setEditable(true);
		comboLessonNumber.addActionListener((event) -> {
			try
			{
				if(listener != null)
					listener.lessonSelected(comboLessonGroup.getSelectedItem().toString(), comboLessonNumber.getSelectedItem().toString());
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		});
		GridBagConstraints gbc_comboLessonNumber = new GridBagConstraints();
		gbc_comboLessonNumber.insets = new Insets(0, 0, 5, 5);
		gbc_comboLessonNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboLessonNumber.gridx = 2;
		gbc_comboLessonNumber.gridy = 5;
		add(comboLessonNumber, gbc_comboLessonNumber);

		CustomPanel panel = new CustomPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 6;
		add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnClose.addActionListener((event) -> {
			if (listener != null)
				listener.close();
		});
		panel.add(btnClose);

		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.addActionListener((event) -> {
			String secondary = textSecondary.getText();
			String primary = textPrimary.getText();
			String type = "" + comboType.getSelectedItem();
			String lessonGroup = "" + comboLessonGroup.getSelectedItem();
			String lessonNumber = comboLessonNumber.getSelectedItem().toString();
			if (listener != null)
			{
				try
				{
					listener.save(primary, secondary, type, lessonGroup, lessonNumber);
					textSecondary.setText("");
					textPrimary.setText("");
					textSecondary.requestFocus();
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		panel.add(btnSave);
	}

	public UpdateWordsPanelListener getListener()
	{
		return listener;
	}

	public void setListener(UpdateWordsPanelListener listener)
	{
		this.listener = listener;
	}

	public JButton getDefaultButton()
	{
		return btnSave;
	}
	
}
