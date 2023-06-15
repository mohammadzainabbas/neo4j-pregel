#!/bin/bash

# Clean the project
./gradlew clean

# Build the project using gradle
./gradlew build

# Run the project
./gradlew run

# Check
PRE_DIR="$HOME/Library/Application\ Support/Neo4j\ Desktop/Application/relate-data/dbmss"
NEO4J_DIR="$PRE_DIR/dbms-259a33e6-ef51-40da-ab00-fadcd3341a7a"
PLUGINS_DIR="$NEO4J_DIR/plugins/"

# Copy the plugin to the plugins directory
cp build/libs/neo4j-plugin-1.0-SNAPSHOT.jar $PLUGINS_DIR


