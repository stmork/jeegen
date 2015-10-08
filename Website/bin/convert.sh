#!/bin/bash

for RES in 114 120 144 152 167 180
do
	let BORDER=$RES/10
	let LOW=$RES-$BORDER-$BORDER
	echo $RES $LOW $BORDER
	montage -geometry ${LOW}x${LOW}+${BORDER}+${BORDER} -colorspace RGB  images/JEE-Generator\ Marke.eps  org.jeegen.website/images/jee-logo-${RES}.png
done
