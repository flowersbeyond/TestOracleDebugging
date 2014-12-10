import java.util.ArrayList;


public abstract class Measureable {

	public abstract double getDistance (Measureable theOther);
	
	public static Measureable getCentre (ArrayList<Measureable> items){ return null;}
}
