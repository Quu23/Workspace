
cd src/pac
javac -p C:\javafx-sdk-17.0.1\lib --add-modules javafx.controls --add-modules javafx.media  Shooting.java
cd C:\javas\Project-Shooting-\src
java -cp . -p C:\javafx-sdk-17.0.1\lib --add-modules javafx.controls --add-modules javafx.media pac.Shooting
cd pac
del *.class
cd C:\javas\Project-Shooting-