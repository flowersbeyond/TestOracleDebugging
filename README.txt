This folder contains all experiment data and source code for paper "first, debug the test oracle". The main program is in folder TestCaseReliability/src. It takes as input the code coverage information of Siemens Test Suites and grep in folder Fault_Localization/input. The experimental outputs are in TestCaseReliability/input, which contains the raw data of the experimental results. Then the OracleDebugPlotter extracts related data into separate files. These separate files are used as input to gnuplot to generate the plots in the paper. Scripts for plotting are in folder plotscript.


Detailed description of this folder is as follows:

Fault_localization
`-input
  `-1,2,...,7: coverage matrix for all Siemens Test Suite program variants.
  `-8        : coverage matrix for grep variants.
  `-mapping  : mapping relation from numbers to different Siemens programs.


TestCaseReliability
`-src
  `-Evaluator.java      : the voting process
  `-FaultLocalizer.java : implementation of all fault localizers
  `-Main.java           : the main experiment program
  `-Measureable.java    : an interface describing anything that can be defined a distance between two elements of its kind.
  `-NeighbourFinder.java: find the closest n neighbours for a test case
  `-TestCase.java       : Class for a test case
  `-TestCaseManager.java: a manager for all test cases.
`-input
  `-BINARY, NAISH1, ..., WONG1: experimental results for different fault localization techniques
    `-1, 2, ..., 8            : experimental results for Siemens Test Suite programs (1-7) and grep(8)
      `-vX_profile            : raw data for each variant. It records the fault localization accuracy using the correct test oracle, using the mutated test oracle under different mutate rates, using the debugged oracle, the relative and absolute improvement, recall and precise value, and so on.
      `-aaa_bbb(_ccc)_XXX     : data containing only item aaa, bbb (and ccc) using SFL XXX. This file is used as input to gnuplot to generate the plots in the paper.

OracleDebugPlotter
`-src
  `-Main.java : the main data processor that extract related data item into separate files.

plotscript
`-grep.plt                : script for figure 6.
`-mut_absImp.plt          : script for figure 5(c)
`-mut_autoScore.plt       : script for figure 5(b)
`-mut_compr.plt           : script for figure 2
`-mut_mutScore.plt        : script for figure 5(a)
`-mut_rec_prec_f1measure.plt: script for figure 4
`-mut_relImp.plt            : script for figure 5(d)
`-origin_0.05_score.plt     : script for figure 1
`-phi.plt                   : script for figure 3
`-rec_abs_relImp.plt        : script for figure 7

NOTE:
1. If you want to run the program yourself, you need to modify some of the directories hard coded in the source files. Please refer to notations and adjust it properly.
2. The coverage matrix is provided while the matrix generator is not. This is because the default folder structure of the Siemens Test Suite provided by SIR needs to be adjusted carefully for running our matrix generator. Since acquiring this matrix should not be a hard problem we believe that it is unnecessary to burden you with this structure adjustment trouble.
3. Raw data for grep is processed manually since only two items for four variants needed to be extracted.
4. Directories in the plot scripts should also be modified accordingly if you want to generate the plots on your own.
