#! /bin/bash

values=test/graphs/m_graph_no_automata.values
graph=test/graphs/m_graph_no_automata.jpg

rm -f $graph $values
touch $values

for file in test/results/*.m_na_results;
do
  cat $file >> $values
done

gnuplot  << EOF
set terminal jpeg
set output "${graph}"
set yrange [0:]
set style data histogram
set style histogram cluster
set style fill solid 1.0 border rgb 'grey30'
set xtics rotate by -45
set xlabel "Books"
set ylabel "Millis"
plot "${values}" u 2:xtic(1) t 'KMP', "${values}" u 3:xtic(1) t 'Radix Tree'
EOF

rm -f $values

exit 0
