#!/bin/bash

# JEE versions:
# JEE6-Generator: 1.2.7
# JEE7-Generator: 1.2.7

DISTRO=${1:-2025-03}
RELEASE=${2:-R}
DOWNLOAD_SERVER=ftp.halifax.rwth-aachen.de
#DOWNLOAD_SERVER=archive.eclipse.org
DOWNLOAD_URI=/eclipse
#DOWNLOAD_URI="/downloads/download.php?file="

LEVEL=release
#LEVEL=snapshot

BASE=$PWD
TARGET_BASE=${BASE}/target
DOWNLOAD=${TARGET_BASE}/download
BUILD=${TARGET_BASE}/build
DIST=${TARGET_BASE}/dist
DIRECTOR_ZIP=eclipse-java-${DISTRO}-${RELEASE}-linux-gtk-x86_64.tar.gz
DIRECTOR=${TARGET_BASE}/eclipse/eclipse
LOG_CONFIG_XML=`realpath logback.xml`

set -e
mkdir -p $DOWNLOAD $BUILD $DIST

echo "Preparing director..."
if [ ! -e ${DOWNLOAD}/${DIRECTOR_ZIP} ]
then
	URL="http://${DOWNLOAD_SERVER}${DOWNLOAD_URI}/technology/epp/downloads/release/${DISTRO}/${RELEASE}/${DIRECTOR_ZIP}"
    echo "Downloading $URL"
    wget -q $URL -O ${DOWNLOAD}/${DIRECTOR_ZIP}
fi

echo "Unpacking director..."
test -d ${TARGET_BASE}/director || tar xfz ${DOWNLOAD}/${DIRECTOR_ZIP} -C ${TARGET_BASE}

function unpack
{
	ARCHIVE=${1}

	rm -rf ${BUILD}/?clipse* ${BUILD}/?.*

	echo "Unpacking... ${ARCHIVE}"

	case "${ARCHIVE}" in
	*.zip)
		unzip -q ${ARCHIVE} -d $BUILD
		;;
	*.tar)
		tar xf ${ARCHIVE} -C $BUILD
		;;
	*.tar.gz)
		tar xfz ${ARCHIVE} -C $BUILD
		;;
	*.tar.bz2)
		tar xfj ${ARCHIVE} -C $BUILD
		;;
	*.dmg)
		dmg2img -s ${ARCHIVE} -o ${DOWNLOAD}/eclipse-mac.img
		7z x ${DOWNLOAD}/eclipse-mac.img -o${BUILD}
		;;
	esac
}

function pack
{
	ARCHIVE=`echo ${1} | sed -e 's/.dmg$/.tar.bz2/g'`

	echo "Packing into... ${ARCHIVE}"

	cd $BUILD
	case "${ARCHIVE}" in
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
			URL="http://${DOWNLOAD_SERVER}${DOWNLOAD_URI}/technology/epp/downloads/release/${DISTRO}/${RELEASE}/${ECLIPSE}"
			echo "Downloading $URL"
			wget -q $URL -O "${DOWNLOAD}/${ECLIPSE}"
		fi

		unpack ${DOWNLOAD}/${ECLIPSE}
		echo "Prepare Distro ${DISTRO} ${RELEASE}..."
		if [ -d ${BUILD}/Eclipse.app ]
		then
			DEST=${BUILD}/Eclipse.app
			CONFIG=${DEST}/Contents/Eclipse/configuration
		else
			DEST=${BUILD}/eclipse
			CONFIG=${DEST}/configuration
		fi

		echo "################### Installing JEE generators..."
		cp -a ${CONFIG}/config.ini .
		${DIRECTOR} -noSplash\
			-application org.eclipse.equinox.p2.director\
			-profileProperties org.eclipse.update.install.features=true\
			-repository http://www.jee-generator.org/updates/${LEVEL}/,http://download.eclipse.org/releases/${DISTRO}/,http://download.eclipse.org/releases/2021-12/\
			-installIU org.eclipse.emf.sdk.feature.group,org.eclipse.xpand.sdk.feature.group,org.eclipse.xtend.sdk.feature.group,org.eclipse.xtext.sdk.feature.group,org.jeegen.jee6.feature.feature.group,org.jeegen.jee7.feature.feature.group\
			-destination ${DEST}\
			-vmargs -Dlogback.configurationFile=${LOG_CONFIG_XML} -Dp2.httpRule=allow

		mv config.ini ${CONFIG}/
		pack ${TARGET}
	else
		echo "${TARGET} already exists."
	fi
}

build linux-gtk-x86_64.tar.gz
build macosx-cocoa-x86_64.tar.gz
build win32-x86_64.zip

rm -rf ${BUILD}
rm -rf ${TARGET_BASE}/director
#rm -rf ${DOWNLOAD}
#rm -rf ${TARGET}
