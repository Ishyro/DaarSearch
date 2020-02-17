#! /bin/bash

if [ $# -lt 2 ]
  then
    echo "need a file and some regexes in input"
    exit 1
fi

file=$1
regexes=$2
index="${file%.txt}".index
title_temp="${file//*\//}"
title="${title_temp%.txt}"

if [ ! -r $file ];
  then
    echo "This file doesn't exist"
    exit 2
fi

start=`date +%s%3N`
java kmp.MultipleSearchesInText $@ > temp
end=`date +%s%3N`
total1=`expr $end - $start`

start=`date +%s%3N`
java index.MultipleSearchesInText $index $@  > temp
end=`date +%s%3N`
total2=`expr $end - $start`

rm -f temp

echo $title $total1 $total2

exit 0
