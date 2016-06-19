#!/bin/bash

VERSION_JEE6_OLD=1.2.3
VERSION_JEE6_NEW=1.2.4

VERSION_JEE7_OLD=1.2.3
VERSION_JEE7_NEW=1.2.4

for FILE in JEE?/org.jeegen.jee?/pom.xml JEE?/org.jeegen.jee?.ui/pom.xml JEE?/org.jeegen.jee?.feature/pom.xml
do
	echo $FILE
	sed -e "0,/${VERSION_JEE6_OLD}/s//${VERSION_JEE6_NEW}-SNAPSHOT/" -e "0,/${VERSION_JEE7_OLD}/s//${VERSION_JEE7_NEW}-SNAPSHOT/" -i ${FILE}
done

for FILE in JEE?/org.jeegen.jee?.feature/feature.xml JEE?/org.jeegen.jee?/META-INF/MANIFEST.MF JEE?/org.jeegen.jee?.ui/META-INF/MANIFEST.MF
do
	echo $FILE
	sed -e "s/${VERSION_JEE6_OLD}/${VERSION_JEE6_NEW}.qualifier/g" -e "s/${VERSION_JEE7_OLD}/${VERSION_JEE7_NEW}.qualifier/g" -i ${FILE}
done

for FILE in Updatesite/org.jeegen.updatesite/*.xml
do
	echo $FILE
	sed -e "s/${VERSION_JEE6_OLD}.qualifier/${VERSION_JEE6_NEW}.qualifier/g" -e "s/${VERSION_JEE7_OLD}.qualifier/${VERSION_JEE7_NEW}.qualifier/g" -i ${FILE}
done

sed -e "s/%RELEASE%/false/g" -e "s/%SNAPSHOT%/true/g" p2.template >JEE6/org.jeegen.jee6.feature/p2.inf
cp -a JEE6/org.jeegen.jee6.feature/p2.inf JEE7/org.jeegen.jee7.feature/p2.inf
