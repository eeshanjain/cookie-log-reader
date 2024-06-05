# cookie-log-reader
This is a simple Cookie Log Reader.
It is designed to read CSV files and find the most active cookies on a given day.

The objective is to run this program on the command line.
Therefore, kindly follow the below steps:

1. Navigate to the root directory of the project in your command line.
2. Run `mvn clean package` - This is to ensure that you're running the Unit Tests and creating the JAR that can then be executed via the `java -jar` command on the command line.
3. Run `java -jar  target\cookie-log-reader-1.0-SNAPSHOT.jar -f <absolute path to the CSV file> -d <date in yyyy-MM-dd format>`

You should be able to see the expected results.