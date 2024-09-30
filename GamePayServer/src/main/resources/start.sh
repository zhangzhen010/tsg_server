#!/bin/sh
export LC_ALL="zh_CN.UTF-8"
export LANG="zh_CN.UTF-8"
start(){
	 trap "" SIGHUP #该句的作用是屏蔽SIGHUP信号，trap可以屏蔽很多信号
	 exec java -Xlog:gc:game_gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:MetaspaceSize=64m -XX:HeapDumpPath=${JAVA_HOME} -XX:ErrorFile=game_error%p.log -server -Xmx1g -Xms1g -Xmn125m -Xss228k -jar orangeGamePayServer-1.jar  -Dfile.encoding=UTF-8&
	 tail -f logs/pay.log
}
echo "start pay:"
start
