import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Main {

	public static String dataLocation = "/home/evelyn/workspace/TestCaseReliability/input/";//this should be adjusted accordingly
	public static void main(String args[]){
		String[] flnames = {"TARANTULA", "NAISH1", "NAISH2", "BINARY", "RUSSEL_RAO", "WONG1","OCHIAI"};
		for(int i = 0; i < flnames.length; i ++){
			genDataFile(flnames[i]);
		}
	}
	
	public static void genDataFile(String flName){
		String datadir = dataLocation + flName + "/";
		int [][] mut_compr = new int[10][100];
		int [][] mut_recall = new int [10][100];
		int [][] mut_prec = new int[10][100];
		int [][] mut_f1measure = new int[10][100];
		int [][] mut_mutScore = new int[10][100];
		int [][] mut_autoScore = new int [10][100];
		int [][] mut_absImp = new int[10][100];
		int [][] mut_relImp = new int[10][100];
		int [][] rec_absImp = new int[100][100];
		int [][] rec_relImp = new int[100][100];
		
		int [] accuracy = new int[1000];
		
		//data for grep is handled manually
		for(int i = 1; i <= 7; i ++){
			String programDataDir = datadir + i + "/";
			File programDataCollection = new File(programDataDir);
			for(File f : programDataCollection.listFiles()){
				//if(!f.getName().endsWith("trim"))
					//continue;
				try {
					BufferedReader reader = new BufferedReader(new FileReader(f));
					String precS = reader.readLine();
					if(precS == null)
						continue;
					double originalPrec = Double.parseDouble(precS.split(" ")[1]);
					accuracy[(int)(originalPrec/0.001)] ++;
					
					String s = reader.readLine();
					while(s != null && !s.equals("")){
						String[] mutDataS = s.split("[\t| ]+");
						//mutateRatio, radir, debugThreshold, precision, recall, mutateScore, loss_of_accuracy, autoScore, absoluteImprovement, relativeImprovement, humanScore, humanPrior));
						double mutateRatio = Double.parseDouble(mutDataS[0]);
						double precision = Double.parseDouble(mutDataS[3]);
						double recall = Double.parseDouble(mutDataS[4]);
						double f1measure = Double.parseDouble(mutDataS[5]);
						double mutateScore = Double.parseDouble(mutDataS[6]);
						double loss_of_accuracy = Double.parseDouble(mutDataS[7]);
						double autoScore = Double.parseDouble(mutDataS[8]);
						double absImp = Double.parseDouble(mutDataS[9]);
						
						double relImp;
						if(mutDataS[9].equals("NaN")|| mutDataS[9].equals("Infinity"))
							relImp = -1;
						else
							relImp = Double.parseDouble(mutDataS[9]);
						
						int mutIndex = (int)((mutateRatio + 0.002)/0.01) - 1;
						
						int comprIndex = (int)(loss_of_accuracy/0.01) - 1;
						if(comprIndex < 0) comprIndex = 0;
						
						int recallIndex = (int)(recall/0.01) - 1;
						if( recallIndex < 0) recallIndex = 0;
						
						int precIndex = (int)(precision/0.01) - 1;
						if(precIndex < 0) precIndex = 0;
						
						int f1measureIndex = (int)(f1measure/0.01) - 1;
						if(f1measureIndex < 0) f1measureIndex = 0;
						
						int mutScoreIndex = (int)(mutateScore/0.01) - 1;
						if(mutScoreIndex < 0) mutScoreIndex = 0;
						
						int autoScoreIndex = (int)(autoScore/0.01) - 1;
						if(autoScoreIndex < 0) autoScoreIndex = 0;
						
						int absImpIndex = (int)(absImp/0.01) - 1;
						if(absImpIndex < 0) absImpIndex = 0;
						
						int relImpIndex = (int)(relImp/0.01) - 1;
						if(relImpIndex < 0 || relImpIndex >= 100) relImpIndex = 0;
						
						mut_compr [mutIndex][comprIndex] ++;
						mut_recall [mutIndex][recallIndex] ++;
						mut_prec [mutIndex][precIndex] ++;
						mut_f1measure[mutIndex][precIndex] ++;
						mut_mutScore[mutIndex][mutScoreIndex] ++;
						mut_autoScore[mutIndex][autoScoreIndex] ++;
						mut_absImp [mutIndex][absImpIndex] ++;
						mut_relImp [mutIndex][relImpIndex] ++;
						rec_absImp [recallIndex][absImpIndex] ++;
						rec_relImp [recallIndex][relImpIndex] ++;
						
						s = reader.readLine();
					}
					reader.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		writeProportionFile(mut_compr, 0.01, 0.01, 0.0, 0.01, datadir + "mut_compr_" + flName, true, false);
		writeProportionFile(mut_recall, 0.01, 0.01, 0.0, 0.01, datadir + "mut_recall_" + flName, false, false);
		writeProportionFile(mut_prec, 0.01, 0.01, 0.0, 0.01, datadir + "mut_prec_" + flName, false, false);
		writeProportionFile(mut_f1measure, 0.01, 0.01, 0.0, 0.01, datadir + "mut_f1measure" + flName, false, false);
		writeProportionFile(mut_mutScore, 0.01, 0.01, 0.0, 0.01, datadir + "mut_mutScore_" + flName, true, false);
		writeProportionFile(mut_autoScore, 0.01, 0.01, 0.0, 0.01, datadir + "mut_autoScore_" + flName, true, false);
		writeProportionFile(mut_absImp, 0.01, 0.01, 0.0, 0.01, datadir + "mut_absImp_" + flName, false, false);
		writeProportionFile(mut_relImp, 0.01, 0.01, 0.0, 0.01, datadir + "mut_relImp_" + flName, false, false);
		writeProportionFile(rec_absImp, 0.0, 0.01, 0.0, 0.01, datadir + "rec_absImp_" + flName, false, true);
		writeProportionFile(rec_relImp, 0.0, 0.01, 0.0, 0.01, datadir + "rec_relImp_" + flName, false, true);
		writeProportionFile(accuracy, 0.001, 0.001, datadir + "original_accuracy_" + flName, true);
		
		
	}
	
	public static void writeProportionFile(int[][] rawData, double x_start, double x_inter, double y_start, double y_inter, String fileName, boolean inteFromSmallY, boolean separateX){
		int x_count = rawData.length;
		int y_count = rawData[0].length;
		double[][] proportion = new double[x_count][y_count];
		
		if(separateX){
			int[] allCountRecord = new int[x_count];
			for(int i = 0; i < x_count; i ++){
				for(int j = 0; j < y_count; j ++){
					allCountRecord[i] += rawData[i][j];
				}
			}
			for(int i = 0; i < x_count; i ++){
				for(int j = 0; j < y_count; j ++){
					if(allCountRecord[i] == 0)
						proportion[i][j] = 0;
					else
						proportion[i][j] = (double)rawData[i][j] / (double)allCountRecord[i];
				}
			}
		}		
		else{
			int allCount = 0;
			for(int j = 0; j < y_count; j ++)
				allCount += rawData[0][j];
			
			for(int i = 0; i < x_count; i ++){
				for(int j = 0; j < y_count; j ++){
					proportion[i][j] = (double)rawData[i][j] / (double)allCount;
				}
			}
		}
		for(int i = 0; i < x_count; i ++){
			double tempRecord[] = new double[y_count];
			for(int j = 0; j < y_count; j ++){
				tempRecord[j] = proportion[i][j];
			}
			
			if(inteFromSmallY){
				for(int j = 0; j < y_count; j ++){
					for(int k = j + 1; k < y_count; k ++){
						proportion[i][k] += tempRecord[j];
					}
				}
			}
			else{
				for(int j = y_count - 1; j >= 0; j --){
					for(int k = j - 1; k >= 0; k --){
						proportion[i][k] += tempRecord[j];
					}
				}
			}
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
			for(int i = 0; i < x_count; i ++){
				for(int j = 0; j < y_count; j ++){
					writer.write(String.format("%5.4f %5.4f %5.4f", x_start + i * x_inter, y_start + j * y_inter, proportion[i][j]));
					writer.newLine();
				}
				writer.newLine();
			}
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeProportionFile(int[] rawData, double x_start, double x_inter, String fileName, boolean inteFromSmallX){
		int x_count = rawData.length;
		int allCount = 0;
		for(int i = 0; i < x_count; i ++){
			allCount += rawData[i];
		}
		double[] proportion = new double[x_count];
		for(int i = 0; i < x_count; i ++){
			proportion[i] = (double)rawData[i]/(double)allCount;
		}
		
		double[] tempRecord = new double[x_count];
		for(int i = 0; i < x_count; i ++){
			tempRecord[i] = proportion[i];
		}
		//by default inteFromSmallX = true
		for(int i = 0; i < x_count; i ++){
			for(int j = i + 1; j < x_count; j++){
				proportion[j] += tempRecord[i];
			}
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
			for(int i = 0; i < x_count; i ++){
				writer.write(String.format("%5.4f %5.4f", x_start + i * x_inter, proportion[i]));
				writer.newLine();
			}
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
