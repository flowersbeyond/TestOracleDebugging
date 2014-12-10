reset

set terminal pdf size 7.50in, 2.50in fontscale 0.4
set output "E://plotdata//mut_rec_prec.pdf"

set multiplot

set pm3d
set cbrange[0.5:1]
set cbtics 0.05

set xlabel "mutation" offset -1,-1 rotate parallel
set zlabel "proportion" rotate parallel

set zrange [0:1]
set ztics 0.1
set yrange [1:0]
set ytics 0.1
set xtics 0.02

set view 70,60

set key noautotitles


set contour both
set cntrparam levels incremental 0.5,0.1,1

set size 0.28,1

set origin 0.02,0
set title "(a) RECALL" offset 0,-2
set ylabel "recall" offset 1,-1 rotate parallel
unset colorbox
unset key
unset surface
splot "E:\\plotdata\\mut_recall_TARANTULA" with lines

set origin 0.29,0
set title "(b) PRECISION" offset 0,-2
set ylabel "precision" offset 1,-1 rotate parallel
unset colorbox
unset key
unset surface
splot "E:\\plotdata\\mut_prec_NAISH1" with lines

set origin 0.56,0
set title "(c) F1 MEASURE" offset 0,-2
set ylabel "f1 measure" offset 1,-1 rotate parallel
set key at screen 0.83,0.8
show key
set colorbox user origin 0.86, 0.2 size 0.01,0.6
unset surface
splot "E:\\plotdata\\mut_f1measure_OCHIAI" with lines

unset multiplot
unset terminal;
set terminal windows