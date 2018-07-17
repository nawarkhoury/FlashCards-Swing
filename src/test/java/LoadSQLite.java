import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.JournalMode;
import org.sqlite.SQLiteConfig.SynchronousMode;

import com.nawar.khoury.language.vocabassistant.configurations.Settings;

public class LoadSQLite
{
	private Connection connection = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	private static final String CREATE_CUSTOM_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS myriam (doc varchar)";
	
	private static LoadSQLite instance; 
	public static LoadSQLite getInstance() throws Exception
	{
		if(instance == null)
			instance = new LoadSQLite();
		return instance;
	}
	
	private LoadSQLite() throws Exception
	{
		executeUpdateQuery(CREATE_CUSTOM_TABLE_QUERY);
		
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


	
	public static void main(String[] args) throws Exception
	{
		LoadSQLite database = LoadSQLite.getInstance();
		
		File directory = new File("C:\\Users\\nawar.khoury\\Downloads\\myriam");
		for(File file : directory.listFiles())
		{
			String content = readFileAsString(file.getAbsolutePath());
			database.store(content);
		}
	}
	
	private void store(String content) throws Exception
	{
		executeUpdateQuery("insert into myriam (doc) values (?)", content);
	}

	public static String readFileAsString(String filePath) 
	{
		try
		{
			byte[] buffer = new byte[(int) new File(filePath).length()];
			FileInputStream fileInputStream = new FileInputStream(filePath);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
			bufferedInputStream.read(buffer);
			fileInputStream.close();
			bufferedInputStream.close();

			return new String(buffer);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return null;
		}

	}
}
