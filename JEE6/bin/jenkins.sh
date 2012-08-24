#!/bin/bash

set -e

cd de.itemis.jee6.util
ant clean
ant package
ant javadoc
ant emma

cd ../de.itemis.jee6
ant clean
ant generate
ant deploy

cd ../de.itemis.jee6.ui
ant clean
ant deploy

cd ../MinimalWebApplication
ant clean
ant package

cd ../FacesGenerated
ant clobber
ant generate
ant package

cd ../FacesTest
ant clean
ant generate
ant package
ant findbugs
ant test
