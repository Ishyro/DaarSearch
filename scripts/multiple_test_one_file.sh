#! /bin/bash

file=$1
result_temp="${file//*\//}"

result=test/results/"${result_temp%.txt}".m_results
input="test/multiple_regexes.txt"

rm -f $result
touch $result

if [ $# -lt 1 ]
  then
    echo "need a file as input"
    exit 1
fi

while IFS= read -r line
do
  regexes=$regexes" "$line
done < "$input"

scripts/multiple_test.sh $file $regexes >> $result

exit 0
