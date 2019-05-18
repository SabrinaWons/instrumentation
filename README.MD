**Description of the project**\
This project uses a java agent to change the code of the class MyService.
MyService has one method getMessage that returns a String "hello".
The java agent reads the bytecode and changes the String to "world".

Here is how to apply a java agent :\
java -javaagent:myjavaagent.jar myapp.jar

**Run the application** \
`gradle clean build run`

