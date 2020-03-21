#! /bin/bash

indexes="src/main/resources/indexes"

for index in $indexes/*.index; do
    echo "$index"
done

exit 0
