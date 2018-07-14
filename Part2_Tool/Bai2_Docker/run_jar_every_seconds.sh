#!/bin/bash
# Script to demo tool exercise 2
# Run file jar (path is arg1) every interval (arg2) seconds
# QuanChu 7/7/2018

DEFAULT_INTERVAL=5

if [ $# != 2 ]
then
	echo "You just supply" $# "arguments"
	echo "You must supply exactly two arguments!"
	echo "Arg1 : jar file path - Arg2 : interval time by seconds"
	exit
fi
jar_path=$1
every_seconds=$2

#echo $jar_path $every_seconds
#exit

if [ "$every_seconds" -le "0" ]
then
	every_seconds=$DEFAULT_INTERVAL
fi

while true
do
	java -jar $jar_path
	sleep $every_seconds
done
