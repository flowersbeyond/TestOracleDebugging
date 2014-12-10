import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestCaseManager {
	
	public TestCase[] testcases;

	public int readFromFile(String dir){
		
		try {
			ArrayList<TestCase> testcaseBuffer = new ArrayList<TestCase>();
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(dir)));
			String s = reader.readLine();
			while(s != null){
				String[] hits = s.split(" ");
				boolean [] hitsF = new boolean[hits.length - 1];
				for(int i = 0; i < hits.length - 1; i ++){
					if(hits[i].equals("1")) hitsF[i] = true;
					else hitsF[i] = false;
				}
				
				TestCase testcase = new TestCase();
				testcase.hitLinesF = hitsF;
				if(hits[hits.length-1].equals("1"))
					testcase.pass = false;
				else
					testcase.pass = true;
				
				testcaseBuffer.add(testcase);
				
				s = reader.readLine();
			}
			
			reader.close();
			
			this.testcases = new TestCase[testcaseBuffer.size()];
			this.testcases = testcaseBuffer.toArray(this.testcases);
			return 0;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		
	}
	
		
	//default writer. reford this.testcases to file
	public int writeToFile(String dir){
		return writeToFile(this.testcases, dir);
	}
	
	//write mutated testcases to file
	public int writeToFile(TestCase[] testcases, String dir){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir)));
			for(int i = 0; i < testcases.length; i ++){
				StringBuffer sb = new StringBuffer("");
				for(int j = 0; j < testcases[i].hitLinesF.length; j ++){
					if(testcases[i].hitLinesF[j])
						sb.append("1 ");
					else
						sb.append("0 ");
				}
				if(testcases[i].pass)
					sb.append("1");
				else
					sb.append("0");
				writer.write(sb.toString());
				writer.newLine();
			}
			
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public void recordSuspicion(double[] suspicion){
		
	}
	
	public void showSuspicion(){
		
	}
	public TestCase[] mutate(double ratio){
		TestCase[] mutation = new TestCase[testcases.length];
		for(int i = 0; i < mutation.length; i ++){
			mutation[i] = new TestCase();
			mutation[i].hitLinesF = testcases[i].hitLinesF;
			double p = Math.random();
			if(p < ratio)
				mutation[i].pass = ! testcases[i].pass;
			else
				mutation[i].pass = testcases[i].pass;
		}
		
		return mutation;
	}
	
	// if we totally trust the voting debugger, we change every test case that we think is suspicious.
	public TestCase[] autoDebug(TestCase[] mutate, double threshold, double[] suspicion){
		TestCase[] debugResult = new TestCase[mutate.length];
		for(int i = 0; i < mutate.length; i ++){
			debugResult[i] = new TestCase();
			debugResult[i].hitLinesF = mutate[i].hitLinesF;
			if(suspicion[i] > threshold)
				debugResult[i].pass = !mutate[i].pass;
			else debugResult[i].pass = mutate[i].pass;
		}
		
		return debugResult;
	}
	
	// we don't fully trust the voting debugger. we verify the suspicious test cases manually.
	// but the "unsuspicious" test cases are ignored.
	public TestCase[] humanDebug(TestCase[] mutate, double threshold, double[] suspicion){
		TestCase[] debugResult = new TestCase[mutate.length];
		for(int i = 0; i < mutate.length; i ++){
			debugResult[i] = new TestCase();
			debugResult[i].hitLinesF = mutate[i].hitLinesF;
			debugResult[i].pass = mutate[i].pass;
			if(suspicion[i] > threshold)
				debugResult[i].pass = testcases[i].pass;
			
		}
		
		return debugResult;
	}
	
	public int [] getStatistics(TestCase[] result, double threshold, double[] suspicion){
		
		//[0]:correct
		//[1]:false positives
		//[2]:miss
		int correct = 0;
		int fp = 0;
		int miss = 0;
		
		for(int i = 0; i < result.length; i ++){
			if(result[i].pass == testcases[i].pass){
				if(suspicion[i] > threshold)
					correct ++;
			}
			else{
				if(suspicion[i] > threshold)
					fp ++;
				else
					miss ++;
			}				
		}
		
		int[] statistics = new int[3];
		statistics[0] = correct;
		statistics[1] = fp;
		statistics[2] = miss;
		
		return statistics;
	}
}
