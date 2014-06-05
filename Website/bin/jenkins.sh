#!/bin/bash

set -e

export LC_ALL=de_DE.UTF-8
BASE=`dirname $0`
cd $BASE/../de.itemis.jee6.website
ant clean generate
