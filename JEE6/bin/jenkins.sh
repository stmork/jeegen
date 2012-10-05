#!/bin/bash

set -e

cd de.itemis.jee6.util
ant clean package javadoc emma

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
