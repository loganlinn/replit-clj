#!/bin/sh

if [  "$#" -lt 1  ]; then
  set -- build
fi

exec clojure -T:build "$@"