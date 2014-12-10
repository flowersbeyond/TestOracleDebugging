import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class NeighbourFinder {
	
	public ArrayList<DistRecord[]> find(TestCase[] testcases, int k){
		
		double[] weightMask = new double[testcases[0].hitLinesF.length];
		for(int i = 0; i < testcases.length; i ++){
			TestCase testcase = testcases[i];
			for(int j = 0; j < testcase.hitLinesF.length; j ++){
				if(!testcase.hitLinesF[j])
					weightMask [j] += 1;
			}
		}
		double unifier = 0;
		for(int j = 0; j < weightMask.length; j ++){
			if(weightMask[j] > unifier)
				unifier = weightMask[j];
		}
		for(int j = 0; j < weightMask.length; j ++){
			//weightMask[j] /= unifier;
			weightMask[j] = Math.log10(unifier/(weightMask[j] + 1));
		}
		
		ArrayList<DistRecord[]> record  = new ArrayList<DistRecord[]>();
		double distanceRecord [][] = new double [testcases.length][testcases.length];
		for(int i = 0; i < testcases.length; i ++){
			for(int j = 0; j < testcases.length; j ++){
				distanceRecord[i][j] = testcases[i].getDistance(testcases[j], weightMask);
			}
		}
		
		for(int i = 0; i < testcases.length; i ++){
			int[] record_i = new int[k];			
			//the first k elements
			for(int j = 0; j < k; j ++){
				int insert = 0;
				while(insert < j && distanceRecord[i][j] <= distanceRecord[i][record_i[insert]])
					insert ++;
				for(int m = j - 1; m >= insert; m --)
					record_i[m + 1] = record_i[m];
				record_i[insert] = j;				
			}
			
			//the left elements			
			for(int j = k; j < testcases.length; j ++){
				int insert = 0;
				while (insert < k && distanceRecord[i][j]<= distanceRecord[i][record_i[insert]])
					insert ++;
				if(insert == k) continue;
				
				for(int m = k - 2; m >= insert; m --)
					record_i[m + 1] = record_i[m];
				record_i[insert] = j;
			}
			
			DistRecord[] recordMap_i = new DistRecord[k];
			for(int j = 0; j < k; j ++){
				recordMap_i[j] = new DistRecord();
				recordMap_i[j].testNum = record_i[j];
				recordMap_i[j].distance = distanceRecord[i][record_i[j]];
			}
			record.add(recordMap_i);
		}
		
		return record;
	}
	
	public void getStatistics(ArrayList<DistRecord[]> record, String output_dir, int programNum){
		int[] bucket = new int[11];
		for(DistRecord[] record_i : record){
			for(int j = 0; j < record_i.length; j ++){
				if(record_i[j].distance < 0.9)
					bucket [0] ++;
				else {
					int bucketNum = (int)((record_i[j].distance - 0.9)/0.01) + 1;
					bucket[bucketNum] ++;
				}
			}
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(output_dir + "neighbourCount" + programNum)));
			writer.write(programNum + "");
			for(int i = 0; i < bucket.length; i ++){
				writer.write("\t" + bucket[i]);
			}
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class DistRecord{
	int testNum;
	double distance;
}
