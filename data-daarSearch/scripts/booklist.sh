#! /bin/bash

indexes="src/main/resources/indexes"
betweenness=$1
booklist=$2
temp="booklist.temp"

for index in $indexes/*.index; do
    echo "$index"
done > $temp

cat $betweenness | awk '{ printf ("src/main/resources/indexes/%s.index\n", $1)  }' > $booklist

grep -v -f $booklist $temp | cat >> $booklist

rm -f $temp

exit 0
