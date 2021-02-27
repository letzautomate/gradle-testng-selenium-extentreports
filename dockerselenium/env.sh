#!/bin/bash
cd $(dirname $0)

function startEnv() {
	local browsers=$1
	docker-compose -f docker-compose.yml up -d
	docker-compose scale chrome=$browsers
}

function stopEnv() {
	docker-compose down -v
}

function queryContainerForReady() {
	local attemptCount=0
	local containerName=$1
	local readyString=$2
	local maxAttempts=$3
	local sleepAmoutn=$4

	local success='false'
}

# check for the container existence
if [ $(docker ps -a | grep -c $containerName) == 0]; then
	echo "ERROR: No container found named $containerName"
	exit 1
fi

local containerId=$(docker ps -q -a --filter "name=${containerName}")
if [-z "$containerId"]; then
	echo "ERROR: could not find the container id"
	exit 1
fi

echo #newline
echo "******** pinging $containerName until it is ready or times out ******"

while (( $attemptCount < $maxAttempts)) && [$success == 'false']; do
	echo -n "Attempt $(( ++attemptCount )) ..."
	if [ $attemptCount = 20 ]; then
		echo "$containerName is not ready"
		exit 1
	fi
	if [ $(docker logs $containerId 2> /dev/null | grep -c "readyString") != 0 ]; then
		echo "$conatainerName is ready"
		success ='true'
	else
		echo "$containerName is not ready ... sleeping $sleepAmount seconds .."
		sleep $sleepAmount
	fi
done
if [$success == 'false']; then
	echo "ERROR: $containerName did not come up in the expected time frame"
	exit 	
fi

echo #newline

}

cmd=$1
browserCnt=$2
if [ "$cmd" = start ]; then
	startEnv $browserCnt
	queryContainerForReady "dockerselenium-hub-1" "Selenium Grid hub is up and runnning"  20 10
	queryContainerForReady "dockerselenium-chrome-1" "the node is registered to the hub and ready to use"  20 10
elif [ "$cmd" = stop ]; then
	stopEnv
else
	echo "ERROR! Invalid Option: [$cmd]"
	echo "Usage: $0 <start|stop>"
	exit 1
fi
	