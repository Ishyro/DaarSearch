#! /bin/bash

values=test/graphs/u_graph.values
graph=test/graphs/u_graph.jpg

rm -f $graph $values
touch $values

for file in test/results/*.u_results;
do
  awk '{ title = $1; total2 += $2; total3 += $3; total4 += $4; total5 += $5; count++; \
  square1 += $1^2; square2 += $2^2; square3 += $3^2; square4 += $4^2; square5 += $5^2;} END \
  { print title" "total2/count" "total3/count" "total4/count" "total5/count" " \
    sqrt((square2-total2^2/count)/count)" "sqrt((square3-total3^2/count)/count)" " \
    sqrt((square4-total4^2/count)/count)" "sqrt((square5-total5^2/count)/count) }' \
  "${file}" >> $values
done

gnuplot  << EOF
set terminal jpeg
set output "${graph}"
set yrange [0:]
set style data histogram
set style histogram cluster
set style histogram errorbars gap 2 lw 1
set style fill solid 1.0 border rgb 'grey30'
set xtics rotate by -45
set xlabel "Books"
set ylabel "Millis"
plot "${values}" u 2:6:xtic(1) t 'Automata', "${values}" u 3:7:xtic(1) t 'KMP', "${values}" u 4:8:xtic(1) t 'Index', "${values}" u 5:9:xtic(1) t 'egrep'
EOF

rm -f $values

exit 0
