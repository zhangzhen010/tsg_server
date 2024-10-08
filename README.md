# tsg_server
tsg server

## Introduction

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-17%2B-blue)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Maven Central](https://img.shields.io/maven-central/v/com.mmorrell/solanaj.svg)](https://search.maven.org/artifact/com.mmorrell/solanaj)
[![Solana](https://img.shields.io/badge/Solana-Compatible-blueviolet)](https://solana.com/)
[![Java](https://img.shields.io/badge/Pure-Java-orange)](https://www.java.com/)
[![Documentation](https://img.shields.io/badge/API-Documentation-lightgrey)](https://docs.solana.com/apps/jsonrpc-api)
[![Discord](https://img.shields.io/discord/889577356681945098?color=blueviolet)](https://discord.gg/solana)

Tokyo Stupid Games (TSG) is a pioneering RWA PRIZE Game-Fi platform that merges Web3 gaming with the dynamic world of Tokyo's cultural and IP ecosystem. By combining cuting-edge blockchain technology with authentic Tokyo brands and real-world asset (RWA) NFTs, TSG delivers an unparalleled experience where users can engage with exclusive digital and physical rewards through casual gaming. Our mission is to cultivate a new era of decentralized gaming and IP, providing players with exciting opportunities to earn,interact, and own a piece of Tokyo's iconic culture. TSG is more than just a project-it's a gateway to a world where innovation, creativity, and real value come together through simple, accessible gaming experiences.

tsg server, written in pure Java.

## 🛠️ Requirements

- Java 17+

## 🏗️ Setting

1. Before starting the project, you need to install the MongoDB and Redis databases locally.

2. Create a database named webgame_game_data in the MongoDB database, and then import the configuration from the mongo directory in the project into the webgame_game_data database.

3. Modify the application.yml file in the resource directory of the GameWebGameServer module, and change the database connection configuration to the local MongoDB connection address:

```sh
  #mongodb
  data:
    mongodb:
      #db
      uri: mongodb://orange:Orange_qnmw2018_3@192.168.110.111:27017/webgame_game?authSource=admin
      #data
      data:
        uri: mongodb://orange:Orange_qnmw2018_3@192.168.110.111:27017/webgame_game_data?authSource=admin
      logs:
        uri: mongodb://orange:Orange_qnmw2018_3@192.168.110.111:27017/webgame_game_log?authSource=admin
```

4. Modify the redis.xml file in the resource directory of the GameWebGameServer module, and change the ip, port, and auth settings to the local Redis connection information:

```sh
<ip>192.168.110.111</ip>
<port>6379</port>
<auth>Orange_qnmw2018_3</auth>
```

## 🚀 run
Run the WebGameMain.java file located at GameWebGameServer/src/main/java/com/game/WebGameMain.java
