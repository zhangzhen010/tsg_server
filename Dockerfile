# 使用官方的Java 17镜像作为基础镜像
FROM openjdk:17-jdk-alpine

# 维护者信息
LABEL maintainer="272745078@qq.com"

#设置时区为北京时间
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

# 创建目录(中心服)
RUN mkdir -p /data/gameServer/center/logs
# 将Jenkins镜像中的文件复制到容器的工作目录中(中心服)
COPY GameCenterServer/src/main/resources /data/gameServer/center
COPY GameCenterServer/src/main/resources/docker/bxtest /data/gameServer/center
COPY GameCenterServer/target/lib/ /data/gameServer/center/lib
COPY GameCenterServer/target/GameCenterServer-1.jar /data/gameServer/center/GameCenterServer-1.jar

# 创建目录(登录服)
RUN mkdir -p /data/gameServer/login/logs
# 将Jenkins镜像中的文件复制到容器的工作目录中(中心服)
COPY GameLoginServer/src/main/resources /data/gameServer/login
COPY GameLoginServer/src/main/resources/docker/bxtest /data/gameServer/login
COPY GameLoginServer/target/lib/ /data/gameServer/login/lib
COPY GameLoginServer/target/GameLoginServer-1.jar /data/gameServer/login/GameLoginServer-1.jar

# 创建目录(战斗服)
RUN mkdir -p /data/gameServer/fight/logs
# 将Jenkins镜像中的文件复制到容器的工作目录中(中心服)
COPY GameFightServer/src/main/resources /data/gameServer/fight
COPY GameFightServer/src/main/resources/docker/bxtest /data/gameServer/fight
COPY GameFightServer/target/lib/ /data/gameServer/fight/lib
COPY GameFightServer/target/GameFightServer-1.jar /data/gameServer/fight/GameFightServer-1.jar

# 创建目录(逻辑服)
RUN mkdir -p /data/gameServer/game/logs
# 将Jenkins镜像中的文件复制到容器的工作目录中(中心服)
COPY GameServerServer/src/main/resources /data/gameServer/game
COPY GameServerServer/src/main/resources/docker/bxtest /data/gameServer/game
COPY GameServerServer/target/lib/ /data/gameServer/game/lib
COPY GameServerServer/target/GameServerServer-1.jar /data/gameServer/game/GameServerServer-1.jar

# 创建目录(web服)
RUN mkdir -p /data/gameServer/web/logs
# 将Jenkins镜像中的文件复制到容器的工作目录中(中心服)
COPY GameWebServer/src/main/resources /data/gameServer/web
COPY GameWebServer/src/main/resources/docker/bxtest /data/gameServer/web
COPY GameWebServer/target/lib/ /data/gameServer/web/lib
COPY GameWebServer/target/GameWebServer-1.jar /data/gameServer/web/GameWebServer-1.jar

# 复制主启动脚本并设置权限
COPY all_start.sh /data/gameServer/all_start.sh
RUN chmod +x /data/gameServer/all_start.sh

# 设置工作目录
WORKDIR /data/gameServer

# 暴露 9111（登录 http） 9161（登录 grpc） 18651（web http） 18671（web grpc） 18251（中心 grpc） 18851（战斗 grpc） 10000（逻辑端口） 20000（逻辑 grpc）
EXPOSE 9111 9161 18651 18671 18251 18851 10000 20000

# 设置脚本文件权限
RUN chmod +x /data/gameServer/center/start.sh
RUN chmod +x /data/gameServer/login/start.sh
RUN chmod +x /data/gameServer/fight/start.sh
RUN chmod +x /data/gameServer/game/start.sh
RUN chmod +x /data/gameServer/web/start.sh

# 启动
ENTRYPOINT ["./all_start.sh"]