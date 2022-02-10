
cd src/pac
javac -p C:\javafx-sdk-17.0.1\lib --add-modules javafx.controls --add-modules javafx.media  ShortDungeon.java
cd C:\Workspace\Prj-Roguelike-\src
java -cp . -p C:\javafx-sdk-17.0.1\lib --add-modules javafx.controls --add-modules javafx.media pac.ShortDungeon
cd C:\Workspace\Prj-Roguelike-