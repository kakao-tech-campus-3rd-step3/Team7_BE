#!/bin/bash

# 현재 활성화된 포트 확인 (Nginx 설정 파일 조회)
CURRENT_PORT=$(grep -oP '(?<=:)\d+' /etc/nginx/proxy_pass.conf)

if [ "$CURRENT_PORT" == "8080" ]; then
  # 현재 Blue(8080)가 활성화 상태 -> Green(8081)으로 배포
  TARGET_PORT=8081
  TARGET_CONTAINER="kareer-fit-green"
  OLD_CONTAINER="kareer-fit-blue"
else
  # 현재 Green(8081)가 활성화 상태 -> Blue(8080)으로 배포
  TARGET_PORT=8080
  TARGET_CONTAINER="kareer-fit-blue"
  OLD_CONTAINER="kareer-fit-green"
fi

echo ">>> New Docker Image Pull"
docker pull $DOCKER_IMAGE_NAME:$IMAGE_TAG

echo ">>> Deploying to $TARGET_CONTAINER on port $TARGET_PORT"
# 환경변수 직접 주입하여 docker-compose로 새 컨테이너 실행
CONTAINER_NAME=$TARGET_CONTAINER PORT_MAPPING="$TARGET_PORT:8080" docker compose --env-file .env up -d --no-deps app

echo ">>> Health check for new container..."
# 10번 시도, 5초 간격으로 Health Check
for i in {1..10}; do
  # curl 응답에서 "UP"이라는 문자열이 있는지 확인하여 성공 여부를 결정 - Spring Acutator 사용 시 응답 형식에 맞춤
  HEALTH_CHECK_RESPONSE=$(curl -s http://127.0.0.1:$TARGET_PORT/health)
  if echo "$HEALTH_CHECK_RESPONSE" | grep -q '"status":"UP"'; then
    echo ">>> Health check successful!"
    
    echo ">>> Switching Nginx proxy to port $TARGET_PORT"
    # Nginx 프록시 설정 변경
    sudo sh -c "echo 'proxy_pass http://127.0.0.1:$TARGET_PORT;' > /etc/nginx/proxy_pass.conf"
    sudo nginx -s reload
    
    echo ">>> Stopping old container: $OLD_CONTAINER"
    # 이전 컨테이너 중지
    docker stop $OLD_CONTAINER
    docker rm $OLD_CONTAINER
    
    exit 0
  fi
  echo "Health check failed. Retrying in 5 seconds... ($i/10)"
  sleep 5
done

echo "!!! Deployment failed: Health check timed out."
echo ">>> Stopping and removing failed container: $TARGET_CONTAINER"
docker stop $TARGET_CONTAINER
docker rm $TARGET_CONTAINER
exit 1