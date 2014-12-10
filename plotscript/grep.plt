reset
set terminal pdf size 8.00in, 2.50in fontscale 0.4
set output "E://plotdata//grep.pdf"

set multiplot



set zrange [-0.02:0.2]
set ztics 0.05
set yrange [0:5]
set ytics 1
set xrange [0:0.1]
set xtics 0.02

set view 70,60

set xlabel "mutation" offset -1,-1 rotate by 150
set ylabel "version" offset 1,-1 #rotate parallel
set zlabel "accuracy" rotate parallel



set key noautotitles



set size 0.30,1

set origin 0,0
set title "(a) TARANTULA" offset 0,-2.5
unset colorbox
unset key
#unset surface
splot "E:\\plotdata\\TARANTULA\\grep_v1.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\TARANTULA\\grep_v1.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\TARANTULA\\grep_v2.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\TARANTULA\\grep_v2.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\TARANTULA\\grep_v3.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\TARANTULA\\grep_v3.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\TARANTULA\\grep_v4.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\TARANTULA\\grep_v4.txt" using 1:2:4 with lines linetype 2


set origin 0.245,0
set title "(b) OCHIAI" offset 0,-2.5
unset key
#unset surface

set zrange [0:0.2]
set ztics 0.05

unset zlabel

splot "E:\\plotdata\\OCHIAI\\grep_v1.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\OCHIAI\\grep_v1.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\OCHIAI\\grep_v2.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\OCHIAI\\grep_v2.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\OCHIAI\\grep_v3.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\OCHIAI\\grep_v3.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\OCHIAI\\grep_v4.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\OCHIAI\\grep_v4.txt" using 1:2:4 with lines linetype 2

set origin 0.49,0
set title "(c) RUSSEL_RAO" offset 0,-2.5
unset key
#unset surface
unset zlabel

splot "E:\\plotdata\\RUSSEL_RAO\\grep_v1.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\RUSSEL_RAO\\grep_v1.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\RUSSEL_RAO\\grep_v2.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\RUSSEL_RAO\\grep_v2.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\RUSSEL_RAO\\grep_v3.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\RUSSEL_RAO\\grep_v3.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\RUSSEL_RAO\\grep_v4.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\RUSSEL_RAO\\grep_v4.txt" using 1:2:4 with lines linetype 2

set origin 0.735,0
set title "(d) NAISH2" offset 0,-2.5
#set colorbox user origin 0.95, 0.2 size 0.01, 0.6
set key at screen 0.95,0.75
set key box

unset zlabel
#unset surface

set zrange [-0.02:1]
set ztics 0.25

splot "E:\\plotdata\\NAISH2\\grep_v1.txt" using 1:2:3 with lines linetype 1 title "mutated", \
 "E:\\plotdata\\NAISH2\\grep_v1.txt" using 1:2:4 with lines linetype 2 title "debugged", \
 "E:\\plotdata\\NAISH2\\grep_v2.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\NAISH2\\grep_v2.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\NAISH2\\grep_v3.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\NAISH2\\grep_v3.txt" using 1:2:4 with lines linetype 2, \
 "E:\\plotdata\\NAISH2\\grep_v4.txt" using 1:2:3 with lines linetype 1, \
 "E:\\plotdata\\NAISH2\\grep_v4.txt" using 1:2:4 with lines linetype 2

unset multiplot
unset terminal;
set terminal windows