#!/bin/bash
export APACHE_DIR=$CATALINA_HOME
echo $APACHE_DIR

cd $APACHE_DIR

cd bin

./shutdown.sh

#ps auwwx | grep catalina.startup.Bootstrap \

./startup.sh