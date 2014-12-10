
public class TestCase{
	
	boolean[] hitLinesF;
	boolean pass;

	public double getDistance(TestCase other, double[] weightMask) {
		// TODO Auto-generated method stub
		
		double crossNum = 0;
		double unionNum = 0;
		for(int i = 0; i < hitLinesF.length; i ++){
			if(hitLinesF[i] && other.hitLinesF[i])
				crossNum += weightMask[i];
			else if(hitLinesF[i] || other.hitLinesF[i])
				unionNum += weightMask[i];
		}
		//return (double)crossNum-(double)unionNum;
		return crossNum/(crossNum + unionNum);
	}

}
