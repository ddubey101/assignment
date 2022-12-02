************************ README ************************************

Assumptions: HSQLDB is installed

Step1. Run following create table command in hsqlDg manager 

CREATE TABLE EVENTS
(
   id VARCHAR(30) NOT NULL,
   duration BIGINT NOT NULL,
   type VARCHAR(20),
   host  VARCHAR(50),
   alert BOOLEAN DEFAULT false NOT NULL,
   PRIMARY KEY (id) 
);

Step2. Go to data folder inside hsqldb directory and Run following command

java -cp ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:sampledb/sampledb --dbname.0 sampledb


Step3. Run main method inside LogProcessor class

I couldn't write the Junits because of time constraints however I have created skeleton classes