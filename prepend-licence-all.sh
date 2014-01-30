#!/bin/sh

find androidsrc -iname \*java | xargs -n 1 ./prepend-licence.sh
find iossrc -iname \*\.m | xargs -n 1 ./prepend-licence.sh
find iossrc -iname \*\.h | xargs -n 1 ./prepend-licence.sh
