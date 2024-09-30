#!/bin/sh
export LC_ALL="zh_CN.UTF-8"
export LANG="zh_CN.UTF-8"
start(){
	 trap "" SIGHUP
	 exec java -Xlog:gc:game_gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:MetaspaceSize=128m -XX:CompressedClassSpaceSize=128m -XX:AutoBoxCacheMax=1000000 -XX:+AlwaysPreTouch -XX:HeapDumpPath=${JAVA_HOME} -XX:ErrorFile=game_error%p.log -server -Xmx1g -Xms1g -Xmn512m -Xss228k -jar orangeGameWebGameServer-1.jar  -Dfile.encoding=UTF-8&
	 tail -f -n 0 logs/webgame.log
}
echo "start webgame:"
start
