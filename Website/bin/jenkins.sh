#!/bin/bash

DST=_site

BASE=`dirname $0`
cd $BASE/../de.itemis.jeegen.website
test -d $DST && rm -rf $DST
jekyll build
rm -rf $DST/bin
rsync -av _site/ stmork,jeegen@web.sourceforge.net:/home/project-web/jeegen/htdocs/
