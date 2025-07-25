#!/bin/bash

set -e

DST=_site

BASE=`dirname $0`
cd $BASE/../org.jeegen.website

test -d $DST && rm -rf $DST
jekyll build
rm -rf $DST/bin

chmod -R o+rX $DST
rsync -av $DST/ stmork,jeegen@web.sourceforge.net:/home/project-web/jeegen/htdocs/
