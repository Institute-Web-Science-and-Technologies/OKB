This is the Rest-Api for OKBC.
 
We worked with Intellij Idea, but it should work with Eclipse, etc as well.

To run the Rest-Api import this Project as Maven Project and Intellij will do the rest.


There are two possible ways to get and store the date:
	1. Use the mySQL DB online at the Uni-Koblenz server 
		In the project we implemented the connection to the Uni-Koblenz server. 
		Just compile the project and get the Main Class running than everything should work.
		There is also some example Data in this Db.
	2. Use a localhost mySQL DB
		To use your own mySQL DB you can set up a mySQL DB.
		In "okbc-rest\Files for setting up mySQL DB onLocalhost" you'll find the scripts that
		will create the table and inserts some example data. Warning: The first file will drop
		the okbcdb Database and the tables, it is possible that you loose some data.
		The last step is to save the connection to your DB in the project.
		To do that you have to edit the file: "okbc-rest\src\main\java\Server_files\mySQL.java"
			You have to edit the variables: 
				1 url: It should be look something like this: "jdbc:mysql://localhost:3306/" 
				2 dbName: If you used the SQL file to create the DB you don't need to change that
				3 userName: On localhost the normal user name is "root"
				4 password

The program throws a warning because of slf4j - You can ignore that, it will be fixed.

If you don't have connection th the internet, you will get a big warning, but ist will work.
	If you can see this lines
		== Spark has ignited ...
		>> Listening on 0.0.0.0:4567
	The Rest-Api is running.

