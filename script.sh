#compile the framework and change to jar
cd ./Framework
javac -d . *.java
jar cf ../MyFramework.jar etu2050
cd ../
#move the jar to the lib file
mv MyFramework.jar ./MyApps/WEB-INF/lib

cp -r ./Framework/etu2050 ./src
#compile the model of the user
cd ./src
javac -d . *.java
cd ../
cp -r ./src/models ./MyApps/WEB-INF/classes

cd ./MyApps
jar -cfv  ../MyProject.war *.jsp WEB-INF
cd ../

mv MyProject.war /home/liantsiky/ITU/tools/apache-tomcat-10.0.27/webapps/