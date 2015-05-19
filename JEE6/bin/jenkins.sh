#!/bin/bash

set -e

cd `dirname $0`

OPEN_JDK=/usr/lib/jvm/java-6-openjdk-amd64
ORACLE_JDK=/usr/lib/jvm/java-6-oracle
APPLE_JDK=/System//Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home

java -version

test -d ${OPEN_JDK}/bin   && export JAVA_HOME=$OPEN_JDK
test -d ${ORACLE_JDK}/bin && export JAVA_HOME=$ORACLE_JDK
test -d ${APPLE_JDK}/bin  && export JAVA_HOME=$APPLE_JDK

echo $JAVA_HOME

cd ../org.jeegen.jee6.util
ant clean package javadoc jacoco

cd ../org.jeegen.jee6
ant undeploy
ant clean generate deploy

cd ../org.jeegen.jee6.ui
ant undeploy
ant clean deploy

cd ../MinimalWebApplicationCdi
ant clean package

cd ../MinimalWebApplicationCdi7
ant clean package

cd ../MinimalWebApplicationDao
ant clean package

cd ../FacesGenerated
ant clobber generate package findbugs

cd ../FacesTest
ant clean generate package findbugs test

cd ../org.jeegen.jee6.dbauth
ant clean generate package
