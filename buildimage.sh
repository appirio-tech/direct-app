#!/bin/bash
set -eo pipefail
APP_NAME=$1
UPDATE_CACHE=""
docker build -f ECSDockerfile -t $APP_NAME:latest .