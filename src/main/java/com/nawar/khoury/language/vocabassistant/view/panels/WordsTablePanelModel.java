package com.nawar.khoury.language.vocabassistant.view.panels;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import com.nawar.khoury.language.vocabassistant.configurations.Settings;
import com.nawar.khoury.language.vocabassistant.model.Word;

public class WordsTablePanelModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	LinkedList<Word> words;
	private String[] colNames = new String[]{ Settings.primaryLanguage, Settings.secondaryLanguage, "Type" };

	@Override
	public String getColumnName(int column)
	{
		return colNames[column];
	}
	
	public WordsTablePanelModel()
	{
		words = new LinkedList<Word>();
	}
	
	@Override
	public int getRowCount()
	{
		return words.size();
	}

	@Override
	public int getColumnCount()
	{
		return colNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Word word = words.get(rowIndex);
		switch (columnIndex)
		{
		case 0:
			return word.getPrimary();
		case 1:
			return word.getSecondary();
		case 2:
			return word.getType();
		}
		return null;
	}
	
	public void setData(LinkedList<Word> words)
	{
		this.words = words;
	}
}
