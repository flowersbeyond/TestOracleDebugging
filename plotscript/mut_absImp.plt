reset
set terminal pdf size 8.00in, 2.40in fontscale 0.4
set output "E://plotdata//mut_absImp.pdf"

set multiplot

set pm3d
set cbrange[0:1]
set cbtics 0.1



set zrange [0:1]
set ztics 0.1
set yrange [0.4:0]
set ytics 0.1
set xtics 0.02

set xlabel "mutation" offset -1,-1 rotate by 150
set ylabel "absolute improvement" offset 1,-1 rotate parallel
set zlabel "proportion" rotate parallel

set view 70,50

set key noautotitles


set contour both
set cntrparam levels incremental 0,0.1,1

set size 0.30,1

set origin 0,0.05
set title "(a) TARANTULA" offset 0,-1
unset colorbox
unset key
unset surface
splot "E:\\plotdata\\mut_absImp_TARANTULA" with lines

set origin 0.245,0.05
set title "(b) OCHIAI" offset 0,-1
unset key
unset surface
unset zlabel
splot "E:\\plotdata\\mut_absImp_OCHIAI" with lines

set origin 0.49,0.05
set title "(c) RUSSEL_RAO" offset 0,-1
unset key
unset surface
unset zlabel
splot "E:\\plotdata\\mut_absImp_RUSSEL_RAO" with lines

set origin 0.733,0.05
set title "(d) NAISH2" offset 0,-1
set colorbox user origin 0.68, 0.06 size 0.3, 0.03 horizontal

set key at screen 0.05,0.09 horizontal
set key box
set key maxrows 1
show key

unset surface
unset zlabel
splot "E:\\plotdata\\mut_absImp_NAISH2" with lines

unset multiplot
unset terminal;
set terminal windows