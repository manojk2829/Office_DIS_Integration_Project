#!/bin/bash
echo "Loading the properties file"
DIR=$(dirname $(readlink -f "$0"))

source $DIR/../config/options.properties

echo "Test type: $TYPE"

cd $DIR/..

if [ "$TYPE" == "FULL" ]
    then
        echo "Full tests"
        java -cp $DIR/../seleniumDIS-trunk.jar:$DIR/../lib/* org.testng.TestNG $DIR/../config/testng.xml
        exit
fi

if [ "$TYPE" == "NO-GO" ]
    then
        echo "Basic tests"
        java -cp $DIR/../seleniumDIS-trunk.jar:$DIR/../lib/* org.testng.TestNG $DIR/../config/testng.xml -testname NO-GO
    exit
fi

echo "Please select FULL/NO-GO tests type on config/options.properties"

