import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;




public class Evaluator {
	
	int radius;
	
	public Evaluator(int radius){
		this.radius = radius;
	}

	public double[] evaluate(TestCase [] cases, ArrayList<DistRecord[]> record){
		
		
		double [] suspicion = new double [cases.length];
		for(int i = 0; i < cases.length; i ++){
			suspicion[i] = vote(cases, record.get(i), i);
		}
		
		return suspicion;
	}
	
	//There could be several vote strategies.
	//This is the simplest one. Everyone votes either 1 or 0.
	private double vote(TestCase[] cases, DistRecord[] record, int index){
		double passVote = 0; 
		double failVote = 0;
		
		for(int i = 0; i < this.radius; i ++){
			if(cases[record[i].testNum].pass) passVote += record[i].distance;
			else failVote += record[i].distance;
		}
		
		if(cases[index].pass)
			return (double)failVote/(double)(passVote + failVote);
		else
			return (double) passVote/(double) (passVote + failVote);
	}
}
