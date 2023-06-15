#!/bin/bash
#----------------------------------------------------------------------------------------
# This script is used to build the project using Gradle, and then copy the output jar 
# (a Neo4j plugin) into the Neo4j's plugin directory. This allows the plugin to be loaded 
# by Neo4j when it starts up.
#----------------------------------------------------------------------------------------

# Function to output log messages with a timestamp
log() {
    printf "\033[34m[%s]\033[32m %s\033[0m\n" "$(date +"%a %d %b, %Y - %I:%M:%S %p")" "$1"
}

error() {
    printf "\033[34m[%s]\033[31m %s\033[0m\n" "$(date +"%a %d %b, %Y - %I:%M:%S %p")" "$1"
}

log "Starting 'Build & Copy' script ..."

# Define directories
PRE_DIR="$HOME/Library/Application Support/Neo4j Desktop/Application/relate-data/dbmss"
NEO4J_DIR="$PRE_DIR/dbms-259a33e6-ef51-40da-ab00-fadcd3341a7a"
PLUGINS_DIR="$NEO4J_DIR/plugins/"

# Clean the project
log "Cleaning the project ..."
if ./gradlew clean; then
    log "Project cleaned successfully."
else
    error "Failed to clean the project. Exiting ..."
    exit 1
fi

# Build the project using Gradle
log "Building the project ..."
if ./gradlew shadowJar; then
    log "Project built successfully."
else
    error "Failed to build the project. Exiting ..."
    exit 1
fi

# Copy the plugin to the plugins directory
log "Copying the plugin to the Neo4j plugins directory ..."
if cp app/build/libs/*.jar "$PLUGINS_DIR"; then
    log "Plugin copied successfully."
else
    error "Failed to copy the plugin. Exiting ..."
    exit 1
fi

log "'Build & Copy' script completed."