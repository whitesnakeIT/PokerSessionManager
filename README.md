# PokerSessionManager
My indyvidual project joining my hobby from the past and actual hobby :) 

PokerSessionManager is a Java-based web application that helps to manage poker sessions. It provides functionalities to create and manage different poker sessions, manage players and their buy-ins, and track the progress of each session. PokerSessionManager can be used by players like also owner of poker clubs. This application is built using Spring Boot, Spring Security, Hibernate, SQL, jsp, bootstrap. Tests are written in spock, h2 database is also used here.

Coming soon

1. Graphs will be added + export to files
2. New graphical interface
3. More useful statistics
4. Project will be on external server

Installation

The easiest way to install is just download and open pom.xml file in your IDE like IntelliJ IDEA and run.

If You don't have IDE see below.


To install and run PokerSessionManager on your local machine, please follow the steps below:

1. Clone this repository by running the following command:

git clone https://github.com/whitesnakeIT/PokerSessionManager.git

2. Change into the cloned directory by running the following command:

cd PokerSessionManager

3. Build the application by running the following command:

mvn clean package

4. Start the application by running the following command:

java -jar target/PokerSessionManager-0.0.1-SNAPSHOT.war

5. The application should now be running on http://localhost:8080.

Note: You may need to have Maven and Java installed on your machine for the above commands to work. If you do not have these tools installed, please refer to their respective documentation for installation instructions.

Usage

To use PokerSessionManager, follow the steps below:

1. Open your web browser and navigate to http://localhost:8080.

2. You will be redirected to the login page where you can either register a new account or log in if you already have an account.

3. After logging in, you will be redirected to the dashboard where you can create new poker sessions, tournaments, their buy-ins, and track the progress of each session.
