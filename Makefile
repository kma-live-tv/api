.PHONY: all build push

all: build push

build:
	DOCKER_BUILDKIT=1 docker build -t ghcr.io/kma-live-tv/api:latest .

push:
	docker push ghcr.io/kma-live-tv/api:latest
