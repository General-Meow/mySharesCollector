#!/bin/bash
docker-machine env mine
eval $(docker-machine env mine)
docker build -t generalmeow/mysharescollector:1.1 .
