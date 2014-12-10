import java.util.ArrayList;


public class FaultLocalizer {

	public static final int TARANTULA = 0;
	public static final int NAISH1 = 1;
	public static final int NAISH2 = 2;
	public static final int BINARY = 3;
	public static final int RUSSEL_RAO = 4;
	public static final int WONG1 = 5;
	public static final int OCHIAI = 6;
	
	public static final int N = 0;
	public static final int E = 1;
	public static final int P = 0;
	public static final int F = 1;
	
	public int mode;
	
	public FaultLocalizer(int mode){
		this.mode = mode;
	}
	
	public int[] localize(TestCase [] testcases){
		switch (mode){
		
		case TARANTULA:
			return this.tarantula(testcases);
		case NAISH1:
			return this.naish1(testcases);
		case NAISH2:
			return this.naish2(testcases);
		case BINARY:
			return this.binary(testcases);
		case RUSSEL_RAO:
			return this.russel_rao(testcases);
		case WONG1:
			return this.russel_rao(testcases);
		case OCHIAI:
			return this.ochiai(testcases);
			default:
				return null;			
		}
	}
	
	public int[][][] getAllCounts(TestCase[] testcases){
		int[][][] allCounts = new int[testcases[0].hitLinesF.length][2][2];
		for(int i = 0; i < testcases.length; i ++){
			for(int j = 0; j < testcases[i].hitLinesF.length; j ++){
				if(testcases[i].hitLinesF[j]){
					if(testcases[i].pass)
						allCounts[j][E][P] ++;
					else
						allCounts[j][E][F] ++;
				}
				else{
					if(testcases[i].pass)
						allCounts[j][N][P] ++;
					else
						allCounts[j][N][F] ++;
				}
			}
		}
		return allCounts;
	}
	
	public int[] getRank(int[] score){
		int[] leastRank = new int[score.length];
		for(int i = 0; i < score.length; i ++){
			for(int j = 0; j < score.length; j ++){
				if(score[i] < score[j])
					leastRank[i] ++;
			}
		}
		int[] mostRank = new int[score.length];
		for(int i = 0; i < score.length; i ++){
			for(int j = 0; j < score.length; j ++){
				if(score[i] <= score[j])
					mostRank[i] ++;
			}
		}
		int[] rank = new int[score.length];
		for(int i = 0; i < score.length; i ++){
			rank[i] = (leastRank[i] + mostRank[i])/2;
		}
		return rank;
	}
	
	public int[] getRank(double[] score){
		int[] rank = new int[score.length];
		for(int i = 0; i < score.length; i ++){
			for(int j = 0; j < score.length; j ++){
				if(score[i] < score[j])
					rank[i] ++;
			}
		}
		return rank;
	}
	public int[] tarantula(TestCase[] testcases){
		double[] result = new double[testcases[0].hitLinesF.length];
		int pass_all = 0;
		int fail_all = 0;
		for(int i = 0; i < testcases.length; i ++){
			if(testcases[i].pass) pass_all ++;
			else fail_all++;
		}
		for(int i = 0; i < result.length; i ++){
			int pass_i = 0;
			int fail_i = 0;
			for(int j = 0; j < testcases.length; j ++){
				if(!testcases[j].hitLinesF[i]) continue;
				if(testcases[j].pass) pass_i ++;
				else fail_i ++;
			}
			if(fail_i == 0 && pass_i == 0)
				result[i] = 0;
			else
				result[i] = ((double)fail_i/(double)fail_all)
				/((double)fail_i/(double)fail_all + (double)pass_i/(double)pass_all);
		}
		
		return getRank(result);
	}
	
	public int[] naish1(TestCase[] testcases){
		int[][][] allCounts = getAllCounts(testcases);
		int[] score = new int[allCounts.length];
		for(int i = 0; i < allCounts.length; i ++){
			if(allCounts[i][N][F] > 0)
				score[i] = -1;
			else
				score[i] = allCounts[i][N][P];
		}
		
		return getRank(score);
	}
	
	public int[] naish2(TestCase[] testcases){
		int[][][] allCounts = getAllCounts(testcases);
		double[] score = new double[allCounts.length];
		for(int i = 0; i < allCounts.length; i ++){
			score[i] = allCounts[i][E][F]
					-((double)allCounts[i][E][P])/((double)(allCounts[i][E][P] + allCounts[i][N][P] + 1));
		}
		
		return getRank(score);
	}
	
	public int[] russel_rao(TestCase[] testcases){
		int[][][]allCounts = getAllCounts(testcases);
		double[] score = new double[allCounts.length];
		for(int i = 0; i < allCounts.length; i ++){
			int[][] count = allCounts[i];
			score[i] = (double)count[E][F]/(double)(count[E][F] + count[N][F] + count[E][P] + count[N][P]);
		}
		return getRank(score);
	}
	
	public int[] binary(TestCase[] testcases){
		int[][][]allCounts = getAllCounts(testcases);
		int[] score = new int[allCounts.length];
		for(int i = 0; i < allCounts.length; i ++){
			int[][] count = allCounts[i];
			if(count[N][F] > 0)
				score[i] = 0;
			else
				score[i] = 1;
		}
		return getRank(score);
	}
	
	public int[] wong1(TestCase[] testcases){
		int[][][]allCounts = getAllCounts(testcases);
		double[] score = new double[allCounts.length];
		for(int i = 0; i < allCounts.length; i ++){
			int[][] count = allCounts[i];
			score[i] = count[E][F];
		}
		return getRank(score);
	}
	
	public int[] ochiai(TestCase[] testcases){
		int [][][]allCounts = getAllCounts(testcases);
		double[] score = new double[allCounts.length];
		for(int i = 0; i < allCounts.length; i ++){
			int [][] count = allCounts[i];
			score[i] = (double)count[E][F]/(Math.sqrt((count[N][F] + count[E][F])*(count[E][F] + count[E][P])));
		}
		
		return getRank(score);
	}
	
	public double getExamScore(int[] rank, int[] flAnswer){
		
		int best = rank.length;
		for(int i = 0;i < flAnswer.length; i ++){
			if(rank[flAnswer[i] - 1] < best)
				best = rank[flAnswer[i] - 1];
		}
		return (double)best/(double)(rank.length);
	}
}
