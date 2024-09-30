#!/bin/sh
export LC_ALL="zh_CN.UTF-8"
export LANG="zh_CN.UTF-8"
export JAVA_HOME="/usr/java/jdk-17/"
stop()
{
	        javaps=`$JAVA_HOME/bin/jps -l | grep orangeGamePayServer-1.jar`
			pid=`echo $javaps | awk '{print $1}'`
		    echo -n "$pid"
			kill -15  "$pid"
			tail -f logs/pay.log
}
stop
