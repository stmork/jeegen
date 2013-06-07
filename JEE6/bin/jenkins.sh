#!/bin/bash

set -e

cd `dirname $0`

OPEN_JDK=/usr/lib/jvm/java-6-openjdk-amd64
ORACLE_JDK=/usr/lib/jvm/java-6-oracle

java -version

test -d $OPEN_JDK   && export JAVA_HOME=$OPEN_JDK
test -d $ORACLE_JDK && export JAVA_HOME=$ORACLE_JDK

cd ../de.itemis.jee6.util
ant clean package javadoc jacoco

cd ../de.itemis.jee6
ant undeploy
ant clean generate deploy

cd ../de.itemis.jee6.ui
ant clean deploy

cd ../MinimalWebApplicationCdi
ant clean package

cd ../MinimalWebApplicationDao
ant clean package

cd ../FacesGenerated
ant clobber generate package findbugs

cd ../FacesTest
ant clean generate package findbugs test
