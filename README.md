# COMP3005Assignment3
A Java application to connect to pgAdmin and execute SQL queries.

cd to the directory folder COMP3005Assignment3Question1

Create a database in pgAdmin and name it whatever (mine is named 'Assignment3Question1').
Change the url in the DatabaseInteract class to have your database name (unless you used 'Assignment3Question1').
Make sure the port number is the same in the url for your pgAdmin (mine is set to 5432).
Change the password string to have your password to log into the pgAdmin database.

You should then be able to interact with the application through a terminal once you run it.
I used IntelliJ to run it (Programming Language used is Java).

You can optionally use the .sql file provided to create and fill in the table in pgAdmin as well.

pom.xml requires dependency:

<dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.2</version>
</dependency>


Link to youtube video: https://youtu.be/hbEgaSmaIhs?si=c-b491iW-4oeswEQ
