#! /bin/bash

if [ $# -lt 2 ]
  then
    echo "egrep need a regex and a file as input"
    exit 1
fi

regex=$1
file=$2
index="${file%.txt}".index

if [ ! -r $file ];
  then
    echo "This file doesn't exist"
    exit 2
fi

if [[ $regex == *"*"* ]] || [[ $regex == *"."* ]] || [[ $regex == *"("* ]] || [[ $regex == *"|"* ]];
  then
    echo "using automata method"
    java automata.SearchInText $regex $file
    exit 0
fi

if [[ $regex =~ ^[a-zA-Z]+$ ]];
  then
    if  [ -r $index ];
      then
        echo "egrep building radix-tree from an index file"
        java index.SearchInText $regex $file $index
      else
        echo "couldn't find" $index", using KMP method"
        java kmp.SearchInText $regex $file
    fi
    exit 0
fi

echo "using KMP method"
java kmp.SearchInText $regex $file
exit 0
