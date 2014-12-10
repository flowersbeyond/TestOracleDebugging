reset

set terminal pdf size 4.00in, 1.95in fontscale 0.4
set output "E://plotdata//origin_mut0.05_score.pdf"

set multiplot

set yrange [0:1]
set ytics 0.1
set xrange [0:0.5]
set xtics 0.05

set key noautotitles


set size 0.46,1

set origin 0,0
set title "(a) ORIGIN"
set xlabel "score"
set ylabel "proportion" rotate parallel offset 1,0
unset key
#set size square
plot "E://plotdata//original_accuracy_OCHIAI" with lines, "E://plotdata//original_accuracy_NAISH2" with lines, "E://plotdata//original_accuracy_RUSSEL_RAO" with lines, "E://plotdata//original_accuracy_TARANTULA" with lines

set origin 0.41,0
set title "(b) MUTATE 0.05"
set xlabel "score"
unset ylabel
set key at screen 1, 0.8
set key samplen 2
#set key maxrows 1
show key

#set size square
plot "E://plotdata//mut_mutScore_OCHIAI_0.05" using 2:3 with lines title "OCHIAI", "E://plotdata//mut_mutScore_NAISH2_0.05" using 2:3 with lines title "NAISH2", "E://plotdata//mut_mutScore_RUSSEL_RAO_0.05" using 2:3 with lines title "RUSSEL_RAO", "E://plotdata//mut_mutScore_TARANTULA_0.05" using 2:3 with lines title "TARANTULA"

unset multiplot
unset terminal;
set terminal windows