#!/bin/bash

for FILE in JEE?/org.jeegen.jee?/pom.xml JEE?/org.jeegen.jee?.ui/pom.xml JEE?/org.jeegen.jee?.feature/pom.xml\
	JEE?/org.jeegen.jee?.feature/feature.xml JEE?/org.jeegen.jee?/META-INF/MANIFEST.MF JEE?/org.jeegen.jee?.ui/META-INF/MANIFEST.MF\
	Updatesite/org.jeegen.updatesite/*.xml
do
	echo $FILE
	svn revert $FILE
done
