#!/bin/bash

set -e

cd `dirname $0`

cd ../org.jeegen.jee7.util
ant clean package javadoc jacoco

cd ../org.jeegen.jee7
ant undeploy
ant clean generate deploy

cd ../org.jeegen.jee7.ui
ant undeploy
ant clean deploy

cd ../MinimalWebApplicationCdi7
ant clean package

cd ../MinimalWebApplicationDao
ant clean package

cd ../FacesGenerated
mkdir -p src/main/resources
ant clobber generate package spotbugs

cd ../FacesTest
ant clean generate package spotbugs test

cd ../org.jeegen.jee7.dbauth
ant clean generate package

echo "Ready."
