#!/bin/bash

# JEE versions:
# JEE6-Generator: 1.2.6
# JEE7-Generator: 1.2.6

DISTRO=${1:-2019-03}
RELEASE=${2:-R}
REFINEMENT=${3:-}
DOWNLOAD_SERVER=archive.eclipse.org

BASE=$PWD
TARGET_BASE=${BASE}/target
DOWNLOAD=${TARGET_BASE}/download
BUILD=${TARGET_BASE}/build
DIST=${TARGET_BASE}/dist
DIRECTOR_ZIP=eclipse-testing-kepler-SR2-linux-gtk-x86_64.tar.gz
DIRECTOR=${TARGET_BASE}/eclipse/eclipse
#DIRECTOR=/tmp/director/director

set -e
mkdir -p $DOWNLOAD $BUILD $DIST

echo "Preparing director..."
if [ ! -e ${DOWNLOAD}/${DIRECTOR_ZIP} ]
then
	URL="http://${DOWNLOAD_SERVER}/technology/epp/downloads/release/kepler/SR2/${DIRECTOR_ZIP}"
	echo "Downloading $URL..."
	wget -q $URL -O ${DOWNLOAD}/${DIRECTOR_ZIP}
fi

echo "Unpacking director..."
test -d ${TARGET_BASE}/director || tar xfz ${DOWNLOAD}/${DIRECTOR_ZIP} -C ${TARGET_BASE}/

function unpack
{
	ARCHIVE=${1}

	rm -rf ${BUILD}/?clipse* ${BUILD}/?.*
	echo "Unpacking... ${DISTRO}"

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
	echo "Packing into... $ARCHIVE"

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
	*.dmg)
		cd Eclipse
		tar cfj ${ARCHIVE} ?clipse*
		;;
	esac
	cd $BASE
}

function build
{
	PLATFORM=${1}
	ECLIPSE="eclipse-jee-${DISTRO}-${RELEASE}${REFINEMENT}-${PLATFORM}"
	TARGET=${DIST}/eclipse-jee-generator-${DISTRO}-${RELEASE}-${PLATFORM}

	if [ ! -e ${TARGET} ]
	then
		if [ ! -e ${DOWNLOAD}/${ECLIPSE} ]
		then
			URL="http://${DOWNLOAD_SERVER}/technology/epp/downloads/release/${DISTRO}/${RELEASE}/${ECLIPSE}"
			echo "Downloading $URL"

			if ! wget -q $URL -O "${DOWNLOAD}/${ECLIPSE}"
			then
				echo "Cannot download ${1}!"
				rm "${DOWNLOAD}/${ECLIPSE}"
				return
			fi
		fi

		unpack ${DOWNLOAD}/${ECLIPSE}
		echo "Preparing Distro ${DISTRO} ${RELEASE}"
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

		cp -a ${DEST}/configuration/config.ini .
		${DIRECTOR} -noSplash\
			-application org.eclipse.equinox.p2.director\
			-profileProperties org.eclipse.update.install.features=true\
			-installIU org.eclipse.egit.feature.group,org.eclipse.sdk.ide,org.jeegen.jee6.feature.feature.group,org.jeegen.jee7.feature.feature.group\
			-repository http://download.eclipse.org/releases/${DISTRO}/,http://www.jee-generator.org/updates/release/,http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/\
			-destination ${DEST}

		mv config.ini ${DEST}/configuration/
		pack ${TARGET}
	else
		echo "${TARGET} already exists."
	fi
}

build linux-gtk-x86_64.tar.gz
build macosx-cocoa-x86_64.dmg
build win32-x86_64.zip

rm -rf ${BUILD}
rm -rf ${TARGET_BASE}/director
rm -rf ${DOWNLOAD}
