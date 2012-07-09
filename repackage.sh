#!/bin/bash

rm -rf pkg
mkdir -p pkg/plugins
cp examples/plummer-sample*/target/*jar pkg/plugins
cp examples/webapp/target/*war pkg
cd pkg
../repackage.sh *war
mv *repackaged.war ..
cd ..
rm -rf pkg

