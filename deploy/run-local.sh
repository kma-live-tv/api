#!/usr/bin/env bash

LOCAL_FILE=./srs.local.conf
HOST_IP=$(ip route get 1 | sed -n 's/^.*src \([0-9.]*\) .*$/\1/p')

rm -f $LOCAL_FILE
cp ./srs.conf $LOCAL_FILE
sed -i "s,http://api,http://${HOST_IP},g" $LOCAL_FILE

docker run --name srs --rm -p 1935:1935 -p 1985:1985 -p 8080:8080\
 -v "$(pwd)/srs.local.conf":/usr/local/srs/conf/srs.conf -it ossrs/srs:4 ./objs/srs -c conf/srs.conf
