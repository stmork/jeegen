#!/bin/bash

set -e

cd de.itemis.jee6.util
ant clean
ant package

cd ../de.itemis.jee6
ant clean
ant generate
ant deploy

cd ../de.itemis.jee6.ui
ant clean
ant deploy

cd ../FacesTest
ant clean
ant generate
ant package
ant findbugs

cd ../FacesGenerated
ant clean
ant generate
