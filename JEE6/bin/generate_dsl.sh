#!/bin/bash

set -e

cd de.itemis.jee6
ant undeploy
ant clean generate deploy

cd ../de.itemis.jee6.ui
ant undeploy
ant clean deploy
