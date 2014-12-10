import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class Main {
	
	public static void main(String args[]){
		
		//control variables:
		//directory containing coverage matrix:
		String input_dir = "/home/evelyn/workspace/Fault_Localization/input/";
		//directory storing the debugging results:
		String output_dir = "/home/evelyn/workspace/TestCaseReliability/input/";
		
		String[] flnames = {"TARANTULA", "NAISH1", "NAISH2", "BINARY", "RUSSEL_RAO", "WONG1", "OCHIAI"};
		
		for(int flmode = FaultLocalizer.TARANTULA; flmode <= FaultLocalizer.OCHIAI; flmode ++){
			for(int i = 1; i <= 8; i ++){

				try {
					BufferedReader reader = new BufferedReader(new FileReader(new File(input_dir + i + "/flanswer")));
					ArrayList<String> flanswerList = new ArrayList<String>();
					String s = reader.readLine();
					while(s!= null && !s.isEmpty()){
						flanswerList.add(s);
						s = reader.readLine();
					}
					reader.close();
					
					String[] flanswerArray = new String[flanswerList.size()];
					flanswerArray = (flanswerList.toArray(flanswerArray));
					for(int j = 0; j < flanswerArray.length; j ++){
						String [] flanswerStringItem = flanswerArray[j].split(" ");
						
						//there is no fault in this version
						if(flanswerStringItem.length <= 1)
							continue;
						
						//flanswer starts from the second number
						int[] flanswer = new int[flanswerStringItem.length - 1];
						for(int k = 0; k < flanswer.length; k ++)
							flanswer[k] = Integer.parseInt(flanswerStringItem[k + 1]);
						
						String matrix_dir = input_dir + i + "/v" + (j + 1);
						String profile_dir = output_dir + flnames[flmode] + "/" + i + "/v" + (j + 1) + "_profile";
						//profiler:
						BufferedWriter profiler = new BufferedWriter(new FileWriter(new File(profile_dir)));
						
						//generate profile for single versions
						TestCaseManager manager = new TestCaseManager();
						manager.readFromFile(matrix_dir);
						//check if there is any "failed" tests for this version
						boolean flag = false;
						for(int k = 0; k < manager.testcases.length; k ++){
							if(!manager.testcases[k].pass){
								flag = true;
								break;
							}
						}
						if(!flag){
							profiler.close();
							continue;
						}
						
						NeighbourFinder finder = new NeighbourFinder();
						ArrayList<DistRecord[]> record = finder.find(manager.testcases, 10);
						if(flmode == FaultLocalizer.TARANTULA)
							finder.getStatistics(record, output_dir, i);						
						
						FaultLocalizer fl = new FaultLocalizer(flmode);
						int[] rank = fl.localize(manager.testcases);
						double score = fl.getExamScore(rank, flanswer);
						profiler.write(String.format("precision: %5.4f", score));
						profiler.newLine();
						for(double mutateRatio = 0.01; mutateRatio < 0.1; mutateRatio += 0.01){
							TestCase[] mutate = manager.mutate(mutateRatio);
							int radir = 4;
							double debugThreshold = 0.58;
							testFLPerformance(manager, mutateRatio, mutate, radir, debugThreshold, record, flmode, flanswer, profiler, score);
							
						}
						profiler.close();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}			

	}

	
	public static void testFLPerformance(TestCaseManager manager, double mutateRatio, TestCase[] mutate,
			int radir, double debugThreshold, ArrayList<DistRecord[]> record, int flmode, int[] flAnswer, BufferedWriter profiler, double idealScore){
		
		double[] suspicion = new Evaluator(radir).evaluate(mutate, record);
		
		TestCase[] autoDebug = manager.autoDebug(mutate, debugThreshold, suspicion);
		int[] autoStatistics = manager.getStatistics(autoDebug, debugThreshold, suspicion);
		TestCase[] humanDebug = manager.humanDebug(mutate, debugThreshold, suspicion);
		int[] humanStatistics = manager.getStatistics(humanDebug, debugThreshold, suspicion);
		
		FaultLocalizer fl = new FaultLocalizer(flmode);
		int[] mutateRank = fl.localize(mutate);
		double mutateScore = fl.getExamScore(mutateRank, flAnswer);
		double loss_of_accuracy = mutateScore - idealScore;
		
		int[] autoRank = fl.localize(autoDebug);
		double autoScore = fl.getExamScore(autoRank, flAnswer);
		double absoluteImprovement = mutateScore - autoScore;
		double relativeImprovement = (mutateScore - autoScore)/(mutateScore - idealScore);
		
		int[] humanRank = fl.localize(humanDebug);
		double humanScore = fl.getExamScore(humanRank, flAnswer);
		double humanPrior = autoScore - humanScore;
		
		double precision = (double)autoStatistics[0]/(double)(autoStatistics[0] + autoStatistics[1]);
		double recall = (double) autoStatistics[0] / (double)(autoStatistics[0] + autoStatistics[2]);
		double f1measure = 2 * precision * recall / (precision + recall);
		
		double fprate = (double)autoStatistics[1]/(double)mutate.length;
		double fnrate = (double)autoStatistics[2]/(double)mutate.length;
		
		try {
			profiler.write(String.format("%6.4f\t%d\t%6.4f\t%6.4f\t%6.4f\t%6.4f\t%6.4f\t%6.4f\t%6.4f\t%6.4f\t%6.4f\t%6.4f\t%6.4f",
					mutateRatio, radir, debugThreshold, precision, recall, f1measure, mutateScore, loss_of_accuracy, autoScore, absoluteImprovement, relativeImprovement, humanScore, humanPrior));
			profiler.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
