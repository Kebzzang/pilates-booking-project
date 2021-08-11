#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=pilas-springboot-webservice

echo ">Build 파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY

echo ">현재 실행 중인 앱 pid 확인"

CURRENT_PID=$(pgrep -fl pilas-springboot-webservice | grep jar | awk '{print $1}')

echo "현재 실행 중인 앱 pid : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 백그라운드 애플리케이션이 없습니다."
else
        echo ">kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo ">새 앱 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo ">JAR Name: $JAR_NAME"

echo ">$JAR_NAME 에 실행권한 부여"

chmod +x $JAR_NAME

echo ">$JAR_NAME 실행"

nohup java -jar  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &