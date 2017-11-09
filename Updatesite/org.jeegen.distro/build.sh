#!/bin/bash

# JEE versions:
# JEE6-Generator: 1.2.4
# JEE7-Generator: 1.2.4

DISTRO=${1:-oxygen}
RELEASE=${2:-1a}
DOWNLOAD_SERVER=ftp.halifax.rwth-aachen.de

BASE=$PWD
TARGET=${BASE}/target
DOWNLOAD=${TARGET}/download
BUILD=${TARGET}/build
DIST=${TARGET}/dist
DIRECTOR=${TARGET}/director/director

set -e
mkdir -p $DOWNLOAD $BUILD $DIST

echo "Preparing director..."
if [ ! -e ${DOWNLOAD}/director_latest.zip ]
then
	URL="http://${DOWNLOAD_SERVER}/eclipse/tools/buckminster/products/director_latest.zip"
	echo "Downloading $URL..."
	wget -q $URL -O ${DOWNLOAD}/director_latest.zip
fi

test -d ${TARGET}/director || unzip -q ${DOWNLOAD}/director_latest.zip -d ${TARGET}

function unpack
{
	rm -rf ${BUILD}/?clipse* ${BUILD}/?.*

	echo "Unpacking... $1"

	case "${1}" in
	*.zip)
		unzip -q ${1} -d $BUILD
		;;
	*.tar)
		tar xf ${1} -C $BUILD
		;;
	*.tar.gz)
		tar xfz ${1} -C $BUILD
		;;
	*.tar.bz2)
		tar xfj ${1} -C $BUILD
		;;
	*.dmg)
		7z e ${1} -o$BUILD && 7z x $BUILD/*.hfs -o$BUILD >/dev/null
		;;
	esac
}

function pack
{
	ARCHIVE=`echo ${1} | sed -e 's/.dmg$/.tar.bz2/g'`
	echo "Packing into... $ARCHIVE"

	cd $BUILD
	case "${1}" in
	*.zip)
		zip -r9 -q ${ARCHIVE} ?clipse*
		;;
	*.tar)
		tar cf ${ARCHIVE} ?clipse*
		;;
	*.tar.gz)
		tar cfz ${ARCHIVE} ?clipse*
		;;
	*.tar.bz2)
		tar cfj ${ARCHIVE} ?clipse*
		;;
	*.dmg)
		cd Eclipse
		tar cfj ${ARCHIVE} ?clipse*
		;;
	esac
	cd $BASE
}

function build
{
	ECLIPSE="eclipse-jee-${DISTRO}-${RELEASE}-$1"
	TARGET=${DIST}/eclipse-jee-generator-${DISTRO}-${RELEASE}-$1

	if [ ! -e ${TARGET} ]
	then
		if [ ! -e ${DOWNLOAD}/${ECLIPSE} ]
		then
			URL="http://${DOWNLOAD_SERVER}/eclipse/technology/epp/downloads/release/${DISTRO}/${RELEASE}/${ECLIPSE}"
			echo "Downloading $URL..."

			if ! wget -q $URL -O "${DOWNLOAD}/${ECLIPSE}"
			then
				echo "Cannot download ${1}!"
				rm "${DOWNLOAD}/${ECLIPSE}"
				return
			fi
		fi

		unpack ${DOWNLOAD}/${ECLIPSE}
		echo "Prepare Distro ${DISTRO} ${RELEASE}..."
		if [ -d ${BUILD}/Eclipse.app ]
		then
			echo "Mac"
			DEST=${BUILD}/Eclipse.app/Contents/Eclipse
		else
			if [ -d ${BUILD}/Eclipse/Eclipse.app ]
			then
				echo "Mac DMG"
				DEST=${BUILD}/Eclipse/Eclipse.app/Contents/Eclipse
			else
				echo "Other platform"
				DEST=${BUILD}/eclipse
			fi
		fi
		echo "Eclipse directory: $DEST"
		${DIRECTOR} -noSplash\
			-application org.eclipse.equinox.p2.director\
			-profileProperties org.eclipse.update.install.features=true\
			-installIU org.eclipse.egit.feature.group,org.eclipse.sdk.ide,org.jeegen.jee6.feature.feature.group,org.jeegen.jee7.feature.feature.group\
			-repository http://download.eclipse.org/releases/${DISTRO}/,http://www.jee-generator.org/updates/release/,http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/\
			-destination ${DEST}

		pack ${TARGET}
	else
		echo "${TARGET} already exists."
	fi
}

#			-installIU org.eclipse.egit.feature.group,org.eclipse.sdk.ide,org.jeegen.jee6.feature.feature.group,org.jeegen.jee7.feature.feature.group

build linux-gtk-x86_64.tar.gz
build linux-gtk.tar.gz
build macosx-cocoa-x86_64.tar.gz
build macosx-cocoa-x86_64.dmg
build win32-x86_64.zip
build win32.zip

rm -rf ${BUILD}
rm -rf ${TARGET}/director
rm -rf ${DOWNLOAD}
