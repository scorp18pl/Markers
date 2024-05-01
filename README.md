# Waypoints

## Dependencies

The plugin relies on the
[btc-ascii-table](https://code.google.com/archive/p/java-ascii-table/)
java library.

## Installation


### Dependency setup
After setting up the maven project make sure to install the dependencies
```bash
    cd <project-dir>
    mkdir lib && cd lib
    curl -O https://storage.googleapis.com/google-code-archive-downloads/v2/code.google.com/java-ascii-table/btc-ascii-table-1.0.jar
    cd ..
    mvn install:install-file -Dfile=lib/btc-ascii-table-1.0.jar -DgroupId=com.bethecoder -DartifactId=ascii-table -Dversion=1.0 -Dpackaging=jar
```
