#!/usr/bin/env bash

ag -o ./src/main/resources/static/ws-doc --force-write ./ws-doc.yml @asyncapi/html-template
