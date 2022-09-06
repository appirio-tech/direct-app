#!/bin/bash
set -eo pipefail
APP_NAME=$1
UPDATE_CACHE=""
docker build -f Dockerfile -t $APP_NAME:latest \
  --build-arg RMI_SERVER=$RMI_SERVER .