#! /bin/bash

file=$1
jaccard=$2
book_name_temp="${file//*\//}"
book_name=${book_name_temp%.txt}

grep $book_name $jaccard | awk -v name="$book_name" '{ if ($1 == name) printf ("%-29s %-29s %s\n", $1, $2, $3); else printf ("%-24s\t%-24s\t%-24s\n", $2, $1, $3) }' | sort -k 3

exit 0
