#!/bin/sh

# 启动center服务
cd /data/gameServer/center && ./start.sh &
# 等待center服务启动完成（如果需要等待，可以添加适当的检查机制）

# 启动login服务
cd /data/gameServer/login && ./start.sh &
# 等待login服务启动完成（如果需要等待，可以添加适当的检查机制）

# 启动fight服务
cd /data/gameServer/fight && ./start.sh &
# 等待fight服务启动完成（如果需要等待，可以添加适当的检查机制）

# 启动game服务
cd /data/gameServer/game && ./start.sh &
# 等待game服务启动完成（如果需要等待，可以添加适当的检查机制）

# 启动web服务
cd /data/gameServer/web && ./start.sh &
# 等待web服务启动完成（如果需要等待，可以添加适当的检查机制）

# 让脚本继续运行，直到所有后台进程结束
wait