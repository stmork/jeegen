#!/bin/bash

VERSION_JEE6=1.2.8
VERSION_JEE7=1.2.8

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

sed -e "s/%RELEASE%/true/g" -e "s/%SNAPSHOT%/false/g" p2.template >JEE6/org.jeegen.jee6.feature/p2.inf
cp -a JEE6/org.jeegen.jee6.feature/p2.inf JEE7/org.jeegen.jee7.feature/p2.inf
