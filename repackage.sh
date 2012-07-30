#!/bin/bash

DIST=$1
if [ "$DIST" == "" ] ; then
    echo "You must specify the distribution .war"
    exit 1
fi
BASE=`echo $DIST | sed -e 's/\.war//'`

rm -rf work
mkdir work
cd work

jar xf ../$DIST
cp ../plugins/*jar WEB-INF/lib
#rm WEB-INF/lib/plummer-kernel* WEB-INF/lib/plummer-api*

jar cf ../$BASE-repackaged.war *

cd ..
rm -rf work
