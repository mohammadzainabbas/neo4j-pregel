#!/bin/bash

log() {
    echo "[$(date)] $1"
}

# Clean the project
./gradlew clean

# Build the project using gradle
./gradlew shadowJar

# Check
PRE_DIR="$HOME/Library/Application\ Support/Neo4j\ Desktop/Application/relate-data/dbmss"
NEO4J_DIR="$PRE_DIR/dbms-259a33e6-ef51-40da-ab00-fadcd3341a7a"
PLUGINS_DIR="$NEO4J_DIR/plugins/"

# Copy the plugin to the plugins directory

cp app/build/libs/*.jar "$PLUGINS_DIR"




