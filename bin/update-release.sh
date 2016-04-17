#!/bin/bash

rsync -av --delete\
	Updatesite/org.jeegen.updatesite/target/repository/\
	stmork,jeegen@web.sourceforge.net:/home/project-web/jeegen/htdocs/updates/release/
