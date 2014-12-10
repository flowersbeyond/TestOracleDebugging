set term pdf size 5, 3
set size ratio 0.5
set output "D:\\Documents\\Research\\TEST_ORACLE\\paper\\ieeetran_major_revision\\plotscripts\\phi.pdf"
beta(sim) = (1 + sqrt ( 2 * sim - 1)) / 2
phi(r, sim) = beta(sim) + r - 2 * beta(sim) * r

set yrange [0.5: 1]
set ytics 0.1
set mytics 2
set ylabel "sim_i"

set xrange [0: 1]
set xtics 0.2
set mxtics 2
set xlabel "r"

set zlabel "phi"
set title "phi(r, sim_i)"

set view map
set contour base
set cntrparam levels incremental 0, 0.1, 1
unset surface

set lmargin 0
set bmargin 0
set tmargin 0
set rmargin 0

set key outside

splot phi (x,y)
set term windows
quit