#!/bin/bash

TMP_WEBSITE=/tmp/website

rsync -av --delete ${PWD}/website/ ${TMP_WEBSITE}/
find  ${TMP_WEBSITE} -type d -name .svn | xargs rm -rf
chmod -R g+rX,o+rX ${TMP_WEBSITE}/

scp -rp ${TMP_WEBSITE}/* root@updates.itemis.de:/data/jee6-generator/

echo "Website deployed"
