#!/bin/bash

set -e

cd org.jeegen.jee6
ant undeploy
ant clean generate deploy

cd ../org.jeegen.jee6.ui
ant undeploy
ant clean deploy
