#! /bin/bash

threshold=$1
indexes="src/main/resources/indexes"
temp="jaccard.temp"
CLASSPATH=bin

for index1 in $indexes/*.index; do
  for index2 in $indexes/*.index; do
    if [ $index1 != $index2 ]; then
      echo "$index1" "$index2"
    fi
  done
done | awk '($1,$2) in seen || ($2,$1) in seen {next} {seen[$1,$2]; print}' > $temp

while IFS= read -r line
do
  java -classpath ${CLASSPATH} index.Jaccard $line
done < $temp | awk -v threshhold="$threshold" '{ if ($3 <= threshhold) printf ("%-24s\t%-24s\t%-24s\n", $1, $2, $3) }'

rm -f $temp

exit 0
