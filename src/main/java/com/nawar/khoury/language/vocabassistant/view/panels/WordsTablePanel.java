package com.nawar.khoury.language.vocabassistant.view.panels;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import com.nawar.khoury.language.vocabassistant.controller.DatabaseManager;
import com.nawar.khoury.language.vocabassistant.model.Word;
import com.nawar.khoury.language.vocabassistant.view.custom.CustomPanel;
import java.awt.Font;

public class WordsTablePanel extends CustomPanel
{
	private JTable table;
	private WordsTablePanelModel tableModel;
	private JPopupMenu popup;
	private WordsTablePanelListener listener;
	
	public WordsTablePanel()
	{
		super("Lesson Vocab");
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		popup = new JPopupMenu();
		JMenuItem removeItem = new JMenuItem("Delete");
		popup.add(removeItem);
		JMenuItem editItem = new JMenuItem("Edit");
		popup.add(editItem);
		tableModel = new WordsTablePanelModel();
		table.setModel(tableModel);
		table.setRowHeight(0, 30);
		
		table.addMouseListener(createMouseListener());
		
		removeItem.addActionListener((event) -> {
			int row = table.getSelectedRow();
			if(listener != null)
			{
				try
				{
					String primary = tableModel.getValueAt(row, 0).toString();
					String secondary = tableModel.getValueAt(row, 1).toString();
					listener.rowDeleted(primary, secondary);
					tableModel.fireTableRowsDeleted(row, row);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		editItem.addActionListener((event) -> {
			int row = table.getSelectedRow();
			if(listener != null)
			{
				try
				{
					String primary = tableModel.getValueAt(row, 0).toString();
					String secondary = tableModel.getValueAt(row, 1).toString();
					Word[] words = DatabaseManager.getInstance().getWord(primary, secondary);
					if(words != null && words.length > 0)
					{
						EditWordsDialog d = new EditWordsDialog(words[0]);
						d.setVisible(true);
					}
					else
						JOptionPane.showMessageDialog(null, "Word not found", "Unavailable", JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Failed to edit word: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
		add(new JScrollPane(table), BorderLayout.NORTH);
	}
	
	public void refresh()
	{
		tableModel.fireTableDataChanged();
	}
	
	public void setData(LinkedList<Word> parameters)
	{
		tableModel.setData(parameters);
	}

	public void setTableListener(WordsTablePanelListener listener)
	{
		this.listener = listener;
	}
	
	private MouseListener createMouseListener()
	{
		return new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				
				if(e.getButton() == MouseEvent.BUTTON3)//right click
				{
					popup.show(table, e.getX(), e.getY());//x and y are the coordinates for where the mouse was when I clicked.
				}
			}
			
		};
	}

	public void setListener(WordsTablePanelListener listener)
	{
		this.listener = listener;
	}
	
	
}
