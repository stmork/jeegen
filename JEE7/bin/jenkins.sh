#!/bin/bash

set -e

cd `dirname $0`

cd ../de.itemis.jee7.util
ant clean package javadoc jacoco

cd ../de.itemis.jee7
ant undeploy
ant clean generate deploy

cd ../de.itemis.jee7.ui
ant undeploy
ant clean deploy

cd ../MinimalWebApplicationCdi7
ant clean package

cd ../MinimalWebApplicationDao
ant clean package

cd ../FacesGenerated
ant clobber generate package findbugs

cd ../FacesTest
ant clean generate package findbugs test

cd ../de.itemis.jee7.dbauth
ant clean generate package
