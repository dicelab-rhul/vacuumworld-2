#!/bin/bash

PKG="ant"
PKG_OK=$(which "$PKG")
echo Checking for "$PKG"... $PKG_OK

if [ "" == "$PKG_OK" ]; then
  echo "$PKG is not installed. Aborting. Install $PKG and retry."
  exit -1
fi

PKG="python3"
PKG_OK=$(which "$PKG")
echo Checking for "$PKG"... $PKG_OK

if [ "" == "$PKG_OK" ]; then
  echo "$PKG is not installed. Aborting. Install $PKG and retry."
  exit -1
fi

echo ""

JAR_NAME="vw.jar"
ANT_PATH="$(pwd)/ant.xml"

echo "Looking for an old version of $ANT_PATH..."

if [ -f "$ANT_PATH" ]; then
  echo "Found existing $ANT_PATH"
  echo "Backing up $ANT_PATH to $ANT_PATH.vwbak..."
  mv $ANT_PATH "$ANT_PATH.vwbak"

  if [ -f "$ANT_PATH.vwbak" ]; then
    echo "Backup complete."
  else
    echo "Backup failed. Aborting script..."
    echo "BUILD FAILED, exiting..."
    echo "Bye!"

    exit -1
  fi
else
  echo "Did not found existing $ANT_PATH, no backup needed."
fi

echo "Generating ANT script: $ANT_PATH..."
./ant_generator.py -H $HOME -a $ANT_PATH -j $JAR_NAME

if [ -f "$ANT_PATH" ]; then
  echo "Succesfully created $ANT_PATH"
  echo "Removing backup, if any..."

  if [ -f "$ANT_PATH.vwbak" ]; then
    rm -rf "$ANT_PATH.vwbak"

    if [ -f "$ANT_PATH.vwbak" ]; then
      echo "Failed to remove backup."
    else
      echo "Backup removed."
    fi
  else
    echo "No backup to remove."
  fi

  echo "Done."
else
  echo "BUILD FAILED, restoring backup, if any..."

  if [ -f "$ANT_PATH.vwbak" ]; then
    mv "$ANT_PATH.vwbak" "$ANT_PATH"

    if [ -f "$ANT_PATH" ]; then
      echo "Backup restored."
    else
      echo "Failed to restore backup."
    fi
  else
    echo "No backup to restore."
  fi

  echo "Bye!"

  exit -1
fi

echo "Looking for an old version of $HOME/$JAR_NAME..."

if [ -f "$HOME/$JAR_NAME" ]; then
  echo "Found existing $HOME/$JAR_NAME"
  echo "Backing up $HOME/$JAR_NAME to $HOME/$JAR_NAME.vwbak..."
  mv $HOME/$JAR_NAME "$HOME/$JAR_NAME.vwbak"

  if [ -f "$HOME/$JAR_NAME.vwbak" ]; then
    echo "Backup complete."
  else
    echo "Backup failed. Aborting script..."
    echo "BUILD FAILED, exiting..."
    echo "Bye!"

    exit -1
  fi
else
  echo "Did not found existing $HOME/$JAR_NAME, no backup needed"
fi

echo "Generating $HOME/$JAR_NAME"
ant -f ant.xml

if [ -f "$HOME/$JAR_NAME" ]; then
  echo "Succesfully created $HOME/$JAR_NAME"
  echo "Removing backup, if any..."

  if [ -f "$HOME/$JAR_NAME.vwbak" ]; then
    rm -rf "$HOME/$JAR_NAME.vwbak"

    if [ -f "$HOME/$JAR_NAME.vwbak" ]; then
      echo "Failed to remove backup."
    else
      echo "Backup removed."
    fi
  else
    echo "No backup to remove."
  fi

  echo "Done."
else
  echo "BUILD FAILED, restoring backup, if any..."

  if [ -f "$HOME/$JAR_NAME.vwbak" ]; then
    mv "$HOME/$JAR_NAME.vwbak" "$HOME/$JAR_NAME"

    if [ -f "$HOME/$JAR_NAME" ]; then
      echo "Backup restored."
    else
      echo "Failed to restore backup."
    fi
  else
    echo "No backup to restore."
  fi

  echo "Bye!"

  exit -1
fi
