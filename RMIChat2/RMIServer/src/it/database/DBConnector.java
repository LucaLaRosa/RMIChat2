package it.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import it.datamodel.Group;
import it.datamodel.Message;
import it.datamodel.User;

public class DBConnector extends AbstractDBConnector{
	/**
	 * ?AbstractDBConnector?
	 * Proprietà di un oggetto di interfacciarsi ad un database:
	 * - createConnection();
	 * - disconnect();
	 */ 

	
	//torna tutti gli utenti
	public LinkedList<User> getUsers() {
		LinkedList<User> list = new LinkedList<>(); 
		
		try {
			java.sql.Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("select * from utente");
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				int id = resultSet.getInt("id");
				String nick = resultSet.getString("nick");
				String pass = resultSet.getString("pass"); 
				String status_message = resultSet.getString("status_message");
				Date date_creation = resultSet.getDate("date_creation");
				
				list.add(new User(id,nick,pass,status_message,date_creation));
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	private User getUserById(int id) {
		User user = null;
		
		try {
			java.sql.Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("select * from utente where id=?");
			statement.setInt(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				
				String nick = resultSet.getString("nick");
				String pass = resultSet.getString("pass"); 
				String status_message = resultSet.getString("status_message");
				Date date_creation = resultSet.getDate("date_creation");
				
				user = new User(id,nick,pass,status_message,date_creation);
				
			}
			
			disconnect(connection, statement, resultSet);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return user;
		
	}
	public Boolean login(String nick ,String pass) {
		
		try {
			java.sql.Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("select * from utente where nick=? and pass=?");
			statement.setString(1, nick);
			statement.setString(2, pass);
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {}
			
			disconnect(connection, statement, resultSet);
			
		} catch (SQLException e) {
			
			return false;
		}
		
		return true;
		
	}
	
	//ritorna tutte le amicizie di un utente
	public LinkedList<User> getFriendList(int user_id) {
		LinkedList<User> list = new LinkedList<>(); 
		
		try {
			java.sql.Connection connection = getConnection();
			String sql = "SELECT AMICIZIA.id_utente2"
					+" FROM AMICIZIA"
					+" WHERE AMICIZIA.id_utente1 = ?,  "/*id utente di cui cercare i messaggi*/
					+" ORDER BY AMICIZIA.id_utente2";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
			
			/*resultSet contiene gli id degli amici*/
			ResultSet resultSet = statement.executeQuery();
			
			LinkedList<Integer> friendsId = new LinkedList<>();
			while(resultSet.next()){
				friendsId.add(resultSet.getInt("utente2")); 
			}
			
			disconnect(connection, statement, resultSet);
			
			for(int id : friendsId){
				list.add(getUserById(id));
			}
			
			disconnect(connection, statement, resultSet);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	//ritorna tutti i gruppi di appartenenza di un utente
	public LinkedList<Group> getUserGroups(int user_id) {
		LinkedList<Group> list = new LinkedList<>();
		
		String sql = "SELECT GRUPPO_DI_APPARTENENZA.group_id"
		+" FROM GRUPPO_DI_APPARTENENZA" 
		+" WHERE GRUPPO_DI_APPARTENENZA.user_id = ?" 
		+" ORDER BY GRUPPO_DI_APPARTENEZA.date";
		
		try {
			java.sql.Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, user_id);
			
			ResultSet resultSet = statement.executeQuery();
			
			//lista con i gruppi cercati
			while(resultSet.next()){
				Date date = new Date(resultSet.getLong("date_creation"));
				Group g = new Group(user_id, date);
				list.add(g);
				
			}
			
			disconnect(connection, statement, resultSet);
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return list;
		
		
	}

	//ritorna la conversazione di due utenti NB: costosa, usare con cautela!
	public LinkedList<Message> getConversation(int id1, int id2) {
		LinkedList<Message> list = new LinkedList<>(); 
		
		try {
			
			java.sql.Connection connection = getConnection();
			
			String sql = "SELECT MESSAGGI.body "+ 
					"FROM MESSAGGI "+ 
					"WHERE (MESSAGGI.sender=? AND MESSAGGI.receiver=?) "+
						"OR(MESSAGGI.sender=? AND MESSAGGI.receiver=?) "+
					"ORDER BY MESSAGGI.date;";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id1);
			statement.setInt(2, id2);
			statement.setInt(3, id2);
			statement.setInt(4, id1);
			
			/*resultSet contiene i messaggi in ordine cronologico*/
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				String body = resultSet.getString("body");
				Date date = resultSet.getDate("date");
				int sender = resultSet.getInt("sender");
				int receiver = resultSet.getInt("receiver");
				int group = resultSet.getInt("gruppo");
				
				list.add(new Message(body, date, sender, receiver, group));
				
				
			}
			
			disconnect(connection, statement, resultSet);
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return list;
	}

	//ritorna solo gli ultimi 10 messaggi della conversazione tra 2 utenti NB: versione economica per il client
	public LinkedList<Message> getLastMessages(int id1, int id2) {
		LinkedList<Message> list = new LinkedList<>(); 

		try {

			java.sql.Connection connection = getConnection();

			String sql = "SELECT TOP 10 MESSAGGI.body "+ 
					"FROM MESSAGGI "+ 
					"WHERE (MESSAGGI.sender=? AND MESSAGGI.receiver=?) "+
						"OR(MESSAGGI.sender=? AND MESSAGGI.receiver=?) "+
					"ORDER BY MESSAGGI.date;";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id1);
			statement.setInt(2, id2);
			statement.setInt(3, id2);
			statement.setInt(4, id1);

			/*resultSet contiene i messaggi in ordine cronologico*/
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()){
				String body = resultSet.getString("body");
				Date date = resultSet.getDate("date");
				int sender = resultSet.getInt("sender");
				int receiver = resultSet.getInt("receiver");
				int group = resultSet.getInt("gruppo");

				list.add(new Message(body, date, sender, receiver, group));


			}

			disconnect(connection, statement, resultSet);


		} catch (SQLException e) {

			e.printStackTrace();
		}

		return list;
	}

	public void insertMessage(Message message) {
		
		try {
			
			String sql = "insert into MESSAGGI(body,data,sender,receiver,gruppo) "+
							"values(?,?,?,?,?)";
			
			/*
			message_id INT NOT NULL PRIMARY KEY  GENERATED BY DEFAULT AS IDENTITY ,
			   body VARCHAR(255) ,
			   data DATE ,
			   sender INT NOT NULL REFERENCES UTENTE(user_id),
			   receiver INT REFERENCES UTENTE(user_id) ,
			   gruppo INT REFERENCES GRUPPO(group_id)

			*/
			
			java.sql.Connection connection = getConnection();
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, message.getBody());
			statement.setDate(2, message.getDate());
			statement.setInt(3, message.getSender());
			statement.setInt(4, message.getReceiver());
			statement.setInt(5, message.getGroup());

			statement.execute(sql);
			
			disconnect(connection, statement);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	public void insertUser(User user) {

		try {
			
			String sql = "insert into UTENTE(nick,pass,status_message,date_creation) "+
							"values(?,?,?,?)";
			
			/*
			CREATE TABLE UTENTE (
   				user_id INT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
   				nick VARCHAR(50),
   				pass VARCHAR(50),
   				status_message VARCHAR(255),
   				date_creation DATE
			);
			*/
			
			java.sql.Connection connection = getConnection();
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getName());
			statement.setString(2, user.getPass());
			statement.setString(3, user.getStatus_message());
			statement.setDate(4, user.getDate_creation());
			
			statement.execute(sql);
			
			disconnect(connection, statement);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public void insertFacebookUser(String nick,String pwd) {

		try {
			
			String sql = "insert into UTENTE(nick,pass) "+
							"values(?,?)";
			
			/*
			CREATE TABLE UTENTE (
   				user_id INT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
   				nick VARCHAR(50),
   				pass VARCHAR(50),
   				status_message VARCHAR(255),
   				date_creation DATE
			);
			*/
			
			java.sql.Connection connection = getConnection();
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nick);
			statement.setString(2, pwd);
			
			statement.execute(sql);
			
			disconnect(connection, statement);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public void insertGroup(Group group) {
		try {
			
			String sql = "insert into GRUPPO(date_creation) "+
							"values(?)";
			
			/*
			CREATE TABLE UTENTE (
   				user_id INT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
   				nick VARCHAR(50),
   				pass VARCHAR(50),
   				status_message VARCHAR(255),
   				date_creation DATE
			);
			*/
			
			java.sql.Connection connection = getConnection();
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDate(1, group.getDate_creation());
			
			statement.execute(sql);
			
			disconnect(connection, statement);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void insertFriendship(int id1, int id2) {
		try {

			String sql = "insert into UTENTE(id_utente1,id_utente2,date_creation) "+
					"values(?,?,?)";

			/*
			CREATE TABLE AMICIZIA (
   				id_utente1 INT REFERENCES UTENTE(user_id) ,
   				id_utente2 INT REFERENCES UTENTE(user_id),
   				date_creation DATE
			);

			 */

			java.sql.Connection connection = getConnection();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id1);
			statement.setInt(2, id2);
			
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			Calendar cal = Calendar.getInstance();
			String sdate = dateFormat.format(cal.getTime()); //2014/08/06 16:00:22
			Date ddate = Date.valueOf(sdate);
			statement.setDate(3, ddate);

			statement.execute(sql);

			disconnect(connection, statement);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public boolean userChatValidate(String nick, String pass) {
		
		/*
		 *CREATE TABLE UTENTE (
		 *		user_id INT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
		 *		nick VARCHAR(50),
		 * 		pass VARCHAR(50),
		 *		status_message VARCHAR(255),
		 *		date_creation DATE
		 *);
		*/
		
		try {
			java.sql.Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement
					("select * from UTENTE "+
					 "where nick = ? ");
			statement.setString(1, nick);
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				
				if(pass.equals(resultSet.getString("pass")))
					return true;

			}
			
			disconnect(connection, statement, resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
		};
		
		return false;
		
	}
	
}
	