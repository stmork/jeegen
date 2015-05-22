#!/bin/bash

VERSION_JEE6=1.1.14
VERSION_JEE7=1.0.6

for FILE in JEE?/org.jeegen.jee?/pom.xml JEE?/org.jeegen.jee?.ui/pom.xml JEE?/org.jeegen.jee?.feature/pom.xml
do
	echo $FILE
	sed -e "0,/${VERSION_JEE6}-SNAPSHOT/s//${VERSION_JEE6}/" -e "0,/${VERSION_JEE7}-SNAPSHOT/s//${VERSION_JEE7}/" -i ${FILE}
done

for FILE in JEE?/org.jeegen.jee?.feature/feature.xml JEE?/org.jeegen.jee?/META-INF/MANIFEST.MF JEE?/org.jeegen.jee?.ui/META-INF/MANIFEST.MF
do
	echo $FILE
	sed -e "s/${VERSION_JEE6}.qualifier/${VERSION_JEE6}/g" -e "s/${VERSION_JEE7}.qualifier/${VERSION_JEE7}/g" -i ${FILE}
done
