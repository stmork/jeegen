#!/bin/bash

DST=_site

test -d $DST && rm -rf $DST
jekyll build
rm -rf $DST/bin
