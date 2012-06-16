#!/bin/bash

set -e

cd de.itemis.jee6
ant generate deploy

cd ../de.itemis.jee6.ui
ant deploy
