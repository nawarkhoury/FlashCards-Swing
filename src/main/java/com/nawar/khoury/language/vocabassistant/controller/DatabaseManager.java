package com.nawar.khoury.language.vocabassistant.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.JournalMode;
import org.sqlite.SQLiteConfig.SynchronousMode;

import com.nawar.khoury.language.vocabassistant.configurations.Settings;
import com.nawar.khoury.language.vocabassistant.model.Word;

public class DatabaseManager
{
	private Connection connection = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	private static final String CREATE_CUSTOM_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS "+Settings.TABLE_NAME_WORDS+" ("+Settings.primaryLanguage+" varchar, "+Settings.secondaryLanguage+" varchar, type varcahr, lesson_group varchar, lesson_number varchar, score Integer default 0, date datetime default CURRENT_TIMESTAMP)";
	private static final String UPGRADE_WORDS_TABLE_QUERY_ADD_SCORE = "ALTER TABLE " + Settings.TABLE_NAME_WORDS + " ADD COLUMN score Integer default 0";
	private static final String CREATE_VERSION_TRACKING_TABLE = "CREATE TABLE IF NOT EXISTS version (db_schema_version)";
	
	private static DatabaseManager instance; 
	public static DatabaseManager getInstance() throws Exception
	{
		if(instance == null)
			instance = new DatabaseManager();
		return instance;
	}
	
	private DatabaseManager() throws Exception
	{
		executeUpdateQuery(CREATE_CUSTOM_TABLE_QUERY);
		
		String[] result = null;
		try
		{
			result = getStringList("SELECT db_schema_version FROM version");
		}
		catch(Exception ex)
		{
//			ex.printStackTrace();
			//version table was not yet created
			
		}
//		System.out.println("The result is: " + Arrays.toString(result)); 
		if(result == null || result.length == 0)
		{
			executeUpdateQuery(CREATE_VERSION_TRACKING_TABLE);
			executeUpdateQuery("INSERT INTO version VALUES ('" + Settings.version + "')");
			executeUpdateQuery(UPGRADE_WORDS_TABLE_QUERY_ADD_SCORE);
		}
		else
		{
			executeUpdateQuery("Update version set db_schema_version = '"+Settings.version+"'");
		}
	}
	
	public Word[] getLessonWords(String lessonGroup, String lessonNumber, Integer limit) throws Exception
	{
//		System.out.println("Getting words for lesson group: " + lessonGroup + " - lessonNumber: " + lessonNumber + " - limit: " + limit);
		StringBuilder conditionsBuilder = new StringBuilder("1=1 ");
		LinkedList<Object> placeHolderValues = new LinkedList<Object>(); 
		if(lessonGroup != null && !lessonGroup.equals(""))
		{
			conditionsBuilder.append(" AND lesson_group = ? ");
			placeHolderValues.add(lessonGroup);
		}
		if(lessonNumber != null && !lessonNumber.equals(""))
		{
			conditionsBuilder.append(" AND lesson_number = ? ");
			placeHolderValues.add(lessonNumber);
		}
		if(limit != null)
		{
			conditionsBuilder.append(" ORDER BY score ASC limit ? ");
			placeHolderValues.add(limit);
		}
		else
			conditionsBuilder.append(" ORDER BY score ASC");
		return getWords(conditionsBuilder.toString(), placeHolderValues.toArray(new Object[placeHolderValues.size()]));
	}
	
	public Word[] getWord(String primary, String secondary) throws Exception
	{
		String conditions = Settings.primaryLanguage + " = ? AND "+Settings.secondaryLanguage+" = ?";
		return getWords(conditions, primary, secondary);
	}
	
	private Word[] getWords(String conditions, Object... placeHolders) throws Exception
	{
		try
		{
			String query = "SELECT "+Settings.primaryLanguage+", "+Settings.secondaryLanguage+", type, lesson_group, lesson_number, date, score FROM " + Settings.TABLE_NAME_WORDS + " WHERE " + conditions;
//			System.out.println("Executing query: " + query);
			connectToDatabase();
			preparedStatement = connection.prepareStatement(query);
			for(int i=0; i<placeHolders.length; i++)
			{
				if(placeHolders[i] instanceof String)
					preparedStatement.setString(i+1, (String)placeHolders[i]);
				else
					preparedStatement.setInt(i+1, (Integer)placeHolders[i]);
			}
			resultSet = preparedStatement.executeQuery();
			
			LinkedList<Word> words = new LinkedList<Word>();
			while(resultSet.next())
			{
				Word word = new Word();
				word.setPrimary(resultSet.getString(Settings.primaryLanguage));
				word.setSecondary(resultSet.getString(Settings.secondaryLanguage));
				word.setType(resultSet.getString("type"));
				word.setLessonGroup(resultSet.getString("lesson_group"));
				word.setLessonNumber(resultSet.getString("lesson_number"));
				word.setScore(resultSet.getInt("score"));
				words.add(word);
			}
			return words.toArray(new Word[words.size()]);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			terminateDbConnection();
		}
	}

	public void addNewWord(Word word) throws Exception
	{
		String query = "INSERT INTO words ("+Settings.primaryLanguage+", "+Settings.secondaryLanguage+", type, lesson_group, lesson_number) values (?,?,?,?,?)";
		executeUpdateQuery(query, word.getPrimary(), word.getSecondary(), word.getType().toString(), word.getLessonGroup(), word.getLessonNumber());
	}
	
	public void updateWord(String primary, String secondary, Word word) throws Exception
	{
		String query = "UPDATE " + Settings.TABLE_NAME_WORDS + " SET " + Settings.primaryLanguage + " = ?, " + Settings.secondaryLanguage + " = ?, type = ?, lesson_group = ?, lesson_number = ? WHERE " + Settings.primaryLanguage + " = ? AND " + Settings.secondaryLanguage + " = ?";
		executeUpdateQuery(query, word.getPrimary(), word.getSecondary(), word.getType().toString(), word.getLessonGroup(), word.getLessonNumber(), primary, secondary);
	}
	
	public void incrementWordScore(Word word) throws Exception
	{
		String query = "Update " + Settings.TABLE_NAME_WORDS + " SET score = score + 2 WHERE " + Settings.primaryLanguage + " = ? AND " + Settings.secondaryLanguage + " = ?";
//		System.out.println("Executing the query: " + query + "\nwith word: " + word);
		executeUpdateQuery(query, word.getPrimary(), word.getSecondary());
	}
	
	public void decrementWordScore(Word word) throws Exception
	{
		String query = "Update " + Settings.TABLE_NAME_WORDS + " SET score = score - 1 WHERE " + Settings.primaryLanguage + " = ? AND " + Settings.secondaryLanguage + " = ?";
//		System.out.println("Executing the query: " + query + "\nwith word: " + word);
		executeUpdateQuery(query, word.getPrimary(), word.getSecondary());
	}
	
	public void removeWord(String primary, String secondary, String lessonGroup, String lessonNumber) throws Exception
	{
		String query = "DELETE FROM " + Settings.TABLE_NAME_WORDS + " WHERE "+Settings.secondaryLanguage+" = ? AND "+Settings.primaryLanguage+" = ? AND lesson_group = ? AND lesson_number = ?";
		executeUpdateQuery(query, secondary, primary, lessonGroup, lessonNumber);
	}
	
	private void executeUpdateQuery(String query, Object...params) throws Exception 
	{
		try
		{
			connectToDatabase();
			preparedStatement = connection.prepareStatement(query);
			for(int i=0; i<params.length; i++)
			{
				if(params[i] instanceof String)
					preparedStatement.setString(i+1, (String)params[i]);
				else
					preparedStatement.setInt(i+1, (Integer)params[i]);
			}
			preparedStatement.execute();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			terminateDbConnection();
		}
	}
	
	private String[] getStringList(String query, Object...params) throws Exception
	{
		try
		{
			connectToDatabase();
			preparedStatement = connection.prepareStatement(query);
			
			for(int i=0; i<params.length; i++)
			{
				if(params[i] instanceof String)
					preparedStatement.setString(i+1, (String)params[i]);
				else
					preparedStatement.setInt(i+1, (Integer)params[i]);
			}
			
			resultSet = preparedStatement.executeQuery();
			
			LinkedList<String> results = new LinkedList<String>();
			while(resultSet.next())
			{
				results.add(resultSet.getString(1));
			}
			return results.toArray(new String[results.size()]);
		}
		finally
		{
			terminateDbConnection();
		}
	}
	
	public void connectToDatabase() throws Exception
	{
		connection = null;
		
		SQLiteConfig config = new SQLiteConfig();
		config.setSharedCache(true);
		config.setJournalMode(JournalMode.WAL);
		config.setSynchronous(SynchronousMode.OFF);
		
		connection = DriverManager.getConnection("jdbc:sqlite:" + Settings.DB_NAME,config.toProperties());
	}
	
	public void terminateDbConnection() throws SQLException
	{
		if(resultSet != null)
			resultSet.close();
		
		if(preparedStatement != null)
			preparedStatement.close();
		
		if(connection != null)
			connection.close();
	}

	public String[] getWordTypes() throws Exception
	{
		String query = "SELECT distinct type from " + Settings.TABLE_NAME_WORDS;
		return getStringList(query);
	}


	public String[] getLessonGroups() throws Exception
	{
		String query = "SELECT distinct lesson_group from " + Settings.TABLE_NAME_WORDS;
		return getStringList(query);
	}

	public String[] getLessonNumbers(String lessonGroup) throws Exception
	{
		String query = "SELECT distinct lesson_number from " + Settings.TABLE_NAME_WORDS + " WHERE lesson_group = ?";
		return getStringList(query, lessonGroup);
	}
	
	public static void main(String[] args) throws Exception
	{
//		String query = "update " + TABLE_NAME_WORDS + " set english = 'The Print' where german like '%Der Druck%'";
//		String query = "Delete from " + Settings.TABLE_NAME_WORDS + " where german = 'Der Ofen'";
//		DatabaseManager.getInstance().executeUpdateQuery(query);
		
		String query2 = "SELECT german from " + Settings.TABLE_NAME_WORDS + " WHERE english like '%work%'";
		String[] results = DatabaseManager.getInstance().getStringList(query2);
		System.out.println(Arrays.toString(results));
	}

}
