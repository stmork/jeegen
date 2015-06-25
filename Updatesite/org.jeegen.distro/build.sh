#!/bin/bash

DISTRO=${1:-luna}
RELEASE=${2:-SR2}
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
	rm -rf ${BUILD}/eclipse

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
	esac
}

function pack
{
	echo "Packing into... $1"

	cd $BUILD
	case "${1}" in
	*.zip)
		zip -r9 -q ${1} eclipse
		;;
	*.tar)
		tar cf ${1} eclipse
		;;
	*.tar.gz)
		tar cfz ${1} eclipse
		;;
	*.tar.bz2)
		tar cfj ${1} eclipse
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
			wget -q $URL -O "${DOWNLOAD}/${ECLIPSE}"
		fi

		unpack ${DOWNLOAD}/${ECLIPSE}
		echo "Prepare Distro ${DISTRO} ${RELEASE}..."
		${DIRECTOR} -noSplash\
			-application org.eclipse.equinox.p2.director\
			-profileProperties org.eclipse.update.install.features=true\
			-installIU org.eclipse.egit.feature.group,org.eclipse.sdk.ide,org.jeegen.jee6.feature.feature.group,org.jeegen.jee7.feature.feature.group\
			-repository http://download.eclipse.org/releases/${DISTRO}/,http://download.itemis.com/updates/releases/,http://www.jee-generator.org/updates/release/\
			-destination ${BUILD}/eclipse

		pack ${TARGET}
	else
		echo "${TARGET} already exists."
	fi
}

build linux-gtk-x86_64.tar.gz
build linux-gtk.tar.gz
build macosx-cocoa-x86_64.tar.gz
build win32-x86_64.zip
build win32.zip

rm -rf ${BUILD} ${DOWNLOAD} ${TARGET}/director
