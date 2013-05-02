#!/bin/bash

set -e

cd `dirname $0`

JVM_PATH=/usr/lib/jvm/java-6-openjdk-amd64

test -d $JVM_PATH && export JAVA_HOME=$JVM_PATH

cd ../de.itemis.jee6.util
ant clean package javadoc jacoco

cd ../de.itemis.jee6
ant undeploy
ant clean generate deploy

cd ../de.itemis.jee6.ui
ant clean deploy

cd ../MinimalWebApplication
ant clean package

cd ../FacesGenerated
ant clobber generate package findbugs

cd ../FacesTest
ant clean generate package findbugs test
