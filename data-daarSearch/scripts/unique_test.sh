#! /bin/bash

if [ $# -lt 2 ]
  then
    echo "egrep need a regex and a file as input"
    exit 1
fi

regex=$1
file=$2
index="${file%.txt}".index
title_temp="${file//*\//}"
title="${title_temp%.txt}"
CLASSPATH=bin

if [ ! -r $file ];
  then
    echo "This file doesn't exist"
    exit 2
fi

start=`date +%s%3N`
java -classpath ${CLASSPATH} automata.SearchInText $regex $file > temp
end=`date +%s%3N`
total1=`expr $end - $start`

start=`date +%s%3N`
java -classpath ${CLASSPATH} kmp.SearchInText $regex $file > temp
end=`date +%s%3N`
total2=`expr $end - $start`

start=`date +%s%3N`
java -classpath ${CLASSPATH} index.SearchInText $regex $file $index > temp
end=`date +%s%3N`
total3=`expr $end - $start`

start=`date +%s%3N`
egrep $regex $file > temp
end=`date +%s%3N`
total4=`expr $end - $start`

rm -f temp

echo $title $total1 $total2 $total3 $total4

exit 0
