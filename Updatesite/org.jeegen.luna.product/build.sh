#!/bin/bash

DISTRO=luna
RELEASE=SR2
DOWNLOAD_SERVER=ftp.halifax.rwth-aachen.de

BASE=$PWD
TARGET=${BASE}/target
DOWNLOAD=${TARGET}/download
BUILD=${TARGET}/build
DIST=${TARGET}/dist
DIRECTOR=${TARGET}/director/director

mkdir -p $DOWNLOAD $BUILD $DIST

test -e ${DOWNLOAD}/director_latest.zip || wget "http://${DOWNLOAD_SERVER}/eclipse/tools/buckminster/products/director_latest.zip" -O ${DOWNLOAD}/director_latest.zip
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

	test -e ${DOWNLOAD}/${ECLIPSE} || wget "http://${DOWNLOAD_SERVER}/eclipse/technology/epp/downloads/release/${DISTRO}/${RELEASE}/${ECLIPSE}" -O "${DOWNLOAD}/${ECLIPSE}"

	unpack ${DOWNLOAD}/${ECLIPSE}
	${DIRECTOR} -noSplash\
		-application org.eclipse.equinox.p2.director\
		-profileProperties org.eclipse.update.install.features=true\
		-installIU org.eclipse.egit.feature.group,org.eclipse.sdk.ide,org.jeegen.jee6.feature.feature.group,org.jeegen.jee7.feature.feature.group\
		-repository http://download.eclipse.org/releases/kepler/,http://download.itemis.com/updates/releases/,http://www.jee-generator.org/updates/release/\
		-destination ${BUILD}/eclipse

	pack ${DIST}/eclipse-jee-generator-${DISTRO}-${RELEASE}-$1
}

build linux-gtk-x86_64.tar.gz
build linux-gtk.tar.gz
build macosx-cocoa-x86_64.tar.gz
build win32-x86_64.zip
build win32.zip

rm -rf ${BUILD} ${DOWNLOAD} ${TARGET}/director
