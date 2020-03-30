#! /bin/bash

file=$1
jaccard=$2
book_name_temp="${file//*\//}"
book_name=${book_name_temp%.txt}

grep $book_name $jaccard | awk -v name="$book_name" '{ if ($1 == name) printf ("%s\n", $2, $3); else printf ("%s\n", $1, $3) }' | sort -k 2 | awk '{ print $1 }'

exit 0
