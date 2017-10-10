#!/bin/bash

PKG="ant"
PKG_OK=$(which "$PKG")
echo Checking for "$PKG"... $PKG_OK

if [ "" == "$PKG_OK" ]; then
  echo "$PKG is not installed. Aborting. Install $PKG and retry."
  exit -1
fi

JAR_NAME="vw.jar"
echo "Generating ANT script..."
./ant_generator.py -H $HOME -j $JAR_NAME
echo "Done"

echo "Generating $HOME/$JAR_NAME"
ant -f ant.xml
echo "Done."
