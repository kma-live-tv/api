#!/usr/bin/env bash

docker run --name srs -p 1935:1935 -p 1985:1985 -p 8080:8080 -v "$(pwd)/srs.local.conf":/usr/local/srs/conf/srs.conf -d -it ossrs/srs:4 ./objs/srs -c conf/srs.conf