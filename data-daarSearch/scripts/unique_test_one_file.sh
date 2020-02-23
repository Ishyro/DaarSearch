#! /bin/bash

file=$1
result_temp="${file//*\//}"

result=test/results/"${result_temp%.txt}".u_results
input="test/unique_regexes.txt"

rm -f $result
touch $result

if [ $# -lt 1 ]
  then
    echo "need a file as input"
    exit 1
fi

while IFS= read -r line
do
  scripts/unique_test.sh $line $file >> $result
done < "$input"

exit 0
