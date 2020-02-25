#! /bin/bash

index="src/main/resources/index"
temp="jaccard.temp"
CLASSPATH=bin

for index1 in $index/*.index; do
  for index2 in $index/*.index; do
    if [ $index1 != $index2 ]; then
      echo "$index1" "$index2"
    fi
  done
done | awk '($1,$2) in seen || ($2,$1) in seen {next} {seen[$1,$2]; print}' > $temp

while IFS= read -r line
do
  java -classpath ${CLASSPATH} index.Jaccard $line
done < $temp | awk {'printf ("%-24s\t%-24s\t%-24s\n", $1, $2, $3)'}

rm -f $temp

exit 0
