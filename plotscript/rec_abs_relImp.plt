reset

set terminal pdf size 4.00in, 2.40in fontscale 0.4
set output "E://plotdata//rec_abs_relImp.pdf"

set multiplot

set pm3d
set cbrange[0:1]
set cbtics 0.1

set xlabel "recall" offset -1,-1 rotate parallel
set zlabel "proportion" rotate parallel

set zrange [0:1]
set ztics 0.1

set xtics 0.2

set view 70,40

set key noautotitles


set contour both
set cntrparam levels incremental 0.5,0.1,1

set size 0.47,1

set origin 0.05,0
set title "(a) Absolute Improvement" offset 0,-2
set ylabel "absolute improvement" offset 1,-1 rotate parallel
unset colorbox
unset key
unset surface
set yrange [0.4:0]
set ytics 0.08
set pm3d interpolate 0,0
splot "E:\\plotdata\\rec_absImp_RUSSEL_RAO" with lines

set origin 0.44,0
set title "(b) Relative Improvement" offset 0,-2
set ylabel "relative improvement" offset 1,-1 rotate parallel
set key at screen 0.88,0.8
set key samplen 2
show key
set colorbox user origin 0.92, 0.2 size 0.02,0.6
unset surface
unset zlabel
set yrange [1:0]
set ytics 0.2
splot "E:\\plotdata\\rec_relImp_RUSSEL_RAO" with lines

unset multiplot
unset terminal;
set terminal windows