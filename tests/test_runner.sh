#! /bin/bash
## Itera 1..N veces los test
set -e ## Exit if  exists  one exception
for value in {1..100}
do
./test.sh run
done