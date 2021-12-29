#!/usr/bin/env bash
set -e

./generate-ws-doc.sh

docker build -t ghcr.io/kma-live-tv/api:latest .
docker push ghcr.io/kma-live-tv/api:latest
