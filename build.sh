#!/bin/bash

echo "[MANDATORY SOFTWARE SECTION]"
echo ""

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
echo "[MAVEN DEPENDENCIES SECTION]"
echo ""

MY_HOME=$(eval echo ~$(whoami))

MAVEN_DEP="junit-jupiter-api-5.0.0"
echo Checking for "$MAVEN_DEP"...
if [ ! -f "$MY_HOME/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.0.0/junit-jupiter-api-5.0.0.jar" ]; then
  echo "$MAVEN_DEP is not installed. Installing it now..."
  curl -L http://central.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.0.0/junit-jupiter-api-5.0.0.jar -o junit-jupiter-api-5.0.0.jar
  mvn install:install-file -Dfile=junit-jupiter-api-5.0.0.jar -DgroupId=org.junit.jupiter -DartifactId=junit-jupiter-api -Dversion=5.0.0 -Dpackaging=jar
  rm -rf junit-jupiter-api-5.0.0.jar
  echo "$MAVEN_DEP has been installed."
else
  echo "$MAVEN_DEP is already installed."
fi

echo ""

MAVEN_DEP="apiguardian-api-1.0.0"
echo Checking for "$MAVEN_DEP"...
if [ ! -f "$MY_HOME/.m2/repository/org/apiguardian/apiguardian-api/1.0.0/apiguardian-api-1.0.0.jar" ]; then
  echo "$MAVEN_DEP is not installed. Installing it now..."
  curl -L http://central.maven.org/maven2/org/apiguardian/apiguardian-api/1.0.0/apiguardian-api-1.0.0.jar -o apiguardian-api-1.0.0.jar
  mvn install:install-file -Dfile=apiguardian-api-1.0.0.jar -DgroupId=org.apiguardian -DartifactId=apiguardian-api -Dversion=1.0.0 -Dpackaging=jar
  rm -rf apiguardian-api-1.0.0.jar
  echo "$MAVEN_DEP has been installed."
else
  echo "$MAVEN_DEP is already installed."
fi

echo ""

MAVEN_DEP="opentest4j-1.0.0"
echo Checking for "$MAVEN_DEP"...
if [ ! -f "$MY_HOME/.m2/repository/org/opentest4j/opentest4j/1.0.0/opentest4j-1.0.0.jar" ]; then
  echo "$MAVEN_DEP is not installed. Installing it now..."
  curl -L http://central.maven.org/maven2/org/opentest4j/opentest4j/1.0.0/opentest4j-1.0.0.jar -o opentest4j-1.0.0.jar
  mvn install:install-file -Dfile=opentest4j-1.0.0.jar -DgroupId=org.opentest4j -DartifactId=opentest4j -Dversion=1.0.0 -Dpackaging=jar
  rm -rf opentest4j-1.0.0.jar
  echo "$MAVEN_DEP has been installed."
else
  echo "$MAVEN_DEP is already installed."
fi

echo ""

MAVEN_DEP="junit-platform-commons-1.0.0"
echo Checking for "$MAVEN_DEP"...
if [ ! -f "$MY_HOME/.m2/repository/org/junit/platform/junit-platform-commons/1.0.0/junit-platform-commons-1.0.0.jar" ]; then
  echo "$MAVEN_DEP is not installed. Installing it now..."
  curl -L http://central.maven.org/maven2/org/junit/platform/junit-platform-commons/1.0.0/junit-platform-commons-1.0.0.jar -o junit-platform-commons-1.0.0.jar
  mvn install:install-file -Dfile=junit-platform-commons-1.0.0.jar -DgroupId=org.junit.platform -DartifactId=junit-platform-commons -Dversion=1.0.0 -Dpackaging=jar
  rm -rf junit-platform-commons-1.0.0.jar
  echo "$MAVEN_DEP has been installed."
else
  echo "$MAVEN_DEP is already installed."
fi

echo ""

MAVEN_DEP="junit-platform-engine-1.0.0-M5"
echo Checking for "$MAVEN_DEP"...
if [ ! -f "$MY_HOME/.m2/repository/org/junit/platform/junit-platform-engine/1.0.0-M5/junit-platform-engine-1.0.0-M5.jar" ]; then
  echo "$MAVEN_DEP is not installed. Installing it now..."
  curl -L http://central.maven.org/maven2/org/junit/platform/junit-platform-engine/1.0.0-M5/junit-platform-engine-1.0.0-M5.jar -o junit-platform-engine-1.0.0-M5.jar
  mvn install:install-file -Dfile=junit-platform-engine-1.0.0-M5.jar -DgroupId=org.junit.platform -DartifactId=junit-platform-engine -Dversion=1.0.0-M5 -Dpackaging=jar
  rm -rf junit-platform-engine-1.0.0-M5.jar
  echo "$MAVEN_DEP has been installed."
else
  echo "$MAVEN_DEP is already installed."
fi

echo ""

MAVEN_DEP="junit-vintage-engine-4.12.0"
echo Checking for "$MAVEN_DEP"...
if [ ! -f "$MY_HOME/.m2/repository/org/junit/vintage/junit-vintage-engine/4.12.0/junit-vintage-engine-4.12.0.jar" ]; then
  echo "$MAVEN_DEP is not installed. Installing it now..."
  curl -L http://central.maven.org/maven2/org/junit/vintage/junit-vintage-engine/4.12.0/junit-vintage-engine-4.12.0.jar -o junit-vintage-engine-4.12.0.jar
  mvn install:install-file -Dfile=junit-vintage-engine-4.12.0.jar -DgroupId=org.junit.vintage -DartifactId=junit-vintage-engine -Dversion=4.12.0 -Dpackaging=jar
  rm -rf junit-vintage-engine-4.12.0.jar
  echo "$MAVEN_DEP has been installed."
else
  echo "$MAVEN_DEP is already installed."
fi

echo ""

MAVEN_DEP="junit-4.12"
echo Checking for "$MAVEN_DEP"...
if [ ! -f "$MY_HOME/.m2/repository/junit/junit/4.12/junit-4.12.jar" ]; then
  echo "$MAVEN_DEP is not installed. Installing it now..."
  curl -L http://central.maven.org/maven2/junit/junit/4.12/junit-4.12.jar -o junit-4.12.jar
  mvn install:install-file -Dfile=junit-4.12.jar -DgroupId=junit -DartifactId=junit -Dversion=4.12 -Dpackaging=jar
  rm -rf junit-4.12.jar
  echo "$MAVEN_DEP has been installed."
else
  echo "$MAVEN_DEP is already installed."
fi

echo ""

MAVEN_DEP="hamcrest-core-1.3"
echo Checking for "$MAVEN_DEP"...
if [ ! -f "$MY_HOME/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar" ]; then
  echo "$MAVEN_DEP is not installed. Installing it now..."
  curl -L http://central.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar -o hamcrest-core-1.3.jar
  mvn install:install-file -Dfile=hamcrest-core-1.3.jar -DgroupId=org.hamcrest -DartifactId=hamcrest-core -Dversion=1.3 -Dpackaging=jar
  rm -rf hamcrest-core-1.3.jar
  echo "$MAVEN_DEP has been installed."
else
  echo "$MAVEN_DEP is already installed."
fi

echo ""
echo "[ANT SCRIPT GENERATION SECTION]"
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
  echo "Successfully created $ANT_PATH"
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

echo ""
echo "[BUILD SECTION]"
echo ""
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
  echo "Successfully created $HOME/$JAR_NAME"
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
