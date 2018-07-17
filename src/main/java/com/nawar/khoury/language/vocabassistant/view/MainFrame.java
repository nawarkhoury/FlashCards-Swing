package com.nawar.khoury.language.vocabassistant.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.nawar.khoury.language.vocabassistant.controller.Tester;
import com.nawar.khoury.language.vocabassistant.controller.WordsAddManager;
import com.nawar.khoury.language.vocabassistant.model.Word;
import com.nawar.khoury.language.vocabassistant.view.panels.ChooseLessonPanelAdvanced;
import com.nawar.khoury.language.vocabassistant.view.panels.ChooseLessonPanelListener;
import com.nawar.khoury.language.vocabassistant.view.panels.MainPanel;
import com.nawar.khoury.language.vocabassistant.view.panels.MainPanelListener;
import com.nawar.khoury.language.vocabassistant.view.panels.PracticePanel;
import com.nawar.khoury.language.vocabassistant.view.panels.PracticePanelListener;
import com.nawar.khoury.language.vocabassistant.view.panels.ProgressPanel;
import com.nawar.khoury.language.vocabassistant.view.panels.UpdateWordsPanel;
import com.nawar.khoury.language.vocabassistant.view.panels.UpdateWordsPanelListener;
import com.nawar.khoury.language.vocabassistant.view.panels.WordsTablePanel;
import com.nawar.khoury.language.vocabassistant.view.panels.WordsTablePanelListener;

public class MainFrame extends JFrame
{
	private MainPanel mainPanel;
	private UpdateWordsPanel newWordsPanel;
	private PracticePanel practicePanel;
	private ChooseLessonPanelAdvanced chooseLessonPanel;
	private WordsTablePanel wordsTablePanel;
	private ProgressPanel progressPanel;
	
	private WordsAddManager wordAdd;
	private Tester tester;
	
	public MainFrame() throws Exception
	{
		super("Nawar Khoury - Flash Cards");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		this.setResizable(false);
		
		wordAdd = new WordsAddManager();

		mainPanel = new MainPanel();
		mainPanel.setListener(generateMainFrameListener());
		newWordsPanel = new UpdateWordsPanel();
		newWordsPanel.setListener(generateNewWordsPanelListener());
		practicePanel = new PracticePanel();
		practicePanel.setListener(generatePracticePanelListener());
//		chooseLessonPanel = new ChooseLessonPanel();
		chooseLessonPanel = new ChooseLessonPanelAdvanced();
		chooseLessonPanel.setListener(generateChooseLessonPanelListener());
		wordsTablePanel = new WordsTablePanel();
		wordsTablePanel.setListener(generateTablePanelListener());
		wordsTablePanel.setData(wordAdd.getLessonWords());
		wordsTablePanel.refresh();
		progressPanel = new ProgressPanel();
		
		setBounds(400, 100, 609, 155);

		setToStartMode();
		
		JLabel label = new JLabel("MESSAGE");
		label.setFont(new Font("Arial", Font.BOLD, 18));
		label.setText("<html>");
		
		label.setText(label.getText() + "</html>");
		JOptionPane.showMessageDialog(null, label, "Reminder", JOptionPane.INFORMATION_MESSAGE);
	}

	private WordsTablePanelListener generateTablePanelListener()
	{
		return new WordsTablePanelListener()
		{
			@Override
			public void rowDeleted(String english, String german) throws Exception
			{
				wordAdd.removeWord(english, german);
				wordsTablePanel.refresh();
			}
		};
	}

	private ChooseLessonPanelListener generateChooseLessonPanelListener()
	{
		return new ChooseLessonPanelListener()
		{
			@Override
			public void next(String lessonGroup, String lessonName, String type, Integer limit)
			{
				try
				{
					tester = new Tester(lessonGroup, lessonName, type, limit, true);
					setToPracticeMode();
					displayNextQuestion();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}

			@Override
			public void close()
			{
				setToStartMode();
			}
		};
	}

	private MainPanelListener generateMainFrameListener()
	{
		return new MainPanelListener()
		{
			@Override
			public void practice()
			{
				setToChooseLessonMode();
			}

			@Override
			public void enterWords()
			{
				setToNewWordsMode();
			}
		};
	}

	private PracticePanelListener generatePracticePanelListener()
	{
		return new PracticePanelListener()
		{
			@Override
			public void close()
			{
				setToStartMode();
			}

			@Override
			public void submitAnswer(String german)
			{
				boolean result = tester.submit(german);
				if(!result)
				{
					String errorMessage = "<html>The right answer is: <br/><br/><b>"+ tester.getCurrentQuestion().getSecondary() + "<b></html>";
					JLabel label = new JLabel(errorMessage);
					label.setFont(new Font("Arial", Font.PLAIN, 20));
					JOptionPane.showMessageDialog(null,label,"Wrong answer",JOptionPane.INFORMATION_MESSAGE);
				}
				displayNextQuestion();
			}

		};
	}

	private void displayNextQuestion()
	{
		Word nextQuestion = tester.nextQuestion();
		if(nextQuestion == null)
		{
			JLabel label = new JLabel(tester.getElapsedTime());
			label.setFont(new Font("Arial", Font.PLAIN, 20));
			JOptionPane.showMessageDialog(null,label,"Done!",JOptionPane.INFORMATION_MESSAGE);
			
			setToStartMode();
			return;
		}
		practicePanel.displayQuestion(nextQuestion);
		progressPanel.setProgress(tester.getTotalSize(), tester.failedSize(), tester.currentSubsetSize(), tester.currentFailedSubsetSize());
		progressPanel.setCurrentWord(nextQuestion);
	}
	
	private UpdateWordsPanelListener generateNewWordsPanelListener()
	{
		return new UpdateWordsPanelListener()
		{
			@Override
			public void close()
			{
				setToStartMode();
			}

			@Override
			public void save(String english, String german, String type, String lessonGroup, String lessonNumber) throws Exception
			{
				Word word = new Word(english, german, type, lessonGroup, lessonNumber);
				wordAdd.addNewWord(word);
				wordsTablePanel.refresh();
			}

			@Override
			public void lessonSelected(String lessonGroup, String lessonNumber) throws Exception
			{
				wordAdd.setCurrentLesson(lessonGroup, lessonNumber);
				wordsTablePanel.refresh();
			}
		};
	}

	private void setToStartMode()
	{
		Rectangle r = getBounds();
		r.height = 155;
		setBounds(r);
		getContentPane().removeAll();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.getRootPane().setDefaultButton(mainPanel.getDefaultButton());
	}

	private void setToNewWordsMode()
	{
		Rectangle r = getBounds();
		r.height = 600;
		setBounds(r);
		getContentPane().removeAll();
		getContentPane().add(newWordsPanel, BorderLayout.NORTH);
		getContentPane().add(wordsTablePanel, BorderLayout.CENTER);
		this.getRootPane().setDefaultButton(newWordsPanel.getDefaultButton());
	}

	private void setToChooseLessonMode()
	{
		Rectangle r = getBounds();
		r.height = 300;
		setBounds(r);
		getContentPane().removeAll();
		getContentPane().add(chooseLessonPanel, BorderLayout.CENTER);
		this.getRootPane().setDefaultButton(chooseLessonPanel.getDefaultButton());
		chooseLessonPanel.refresh();
	}

	private void setToPracticeMode()
	{
		Rectangle r = getBounds();
		r.height = 430;
		setBounds(r);
		getContentPane().removeAll();
		getContentPane().add(practicePanel, BorderLayout.NORTH);
		getContentPane().add(progressPanel, BorderLayout.CENTER);
		this.getRootPane().setDefaultButton(practicePanel.getDefaultButton());
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		{
			if ("Nimbus".equals(info.getName()))
			{
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
		// UIManager.setLookAndFeel(UIManager.get);
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	
}
