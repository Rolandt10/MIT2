/** Generated by YAKINDU Statechart Tools code generator. */
package hu.bme.mit.yakindu.analysis.example;

import hu.bme.mit.yakindu.analysis.IStatemachine;
import hu.bme.mit.yakindu.analysis.ITimerCallback;

public interface IExampleStatemachine extends ITimerCallback,IStatemachine {
	public interface SCInterface {
	
		public void raiseStart();
		
		public void raiseBlue();
		
		public void raiseBlack();
		
		public long getBlueTime();
		
		public void setBlueTime(long value);
		
		public long getBlackTime();
		
		public void setBlackTime(long value);
		
	}
	
	public SCInterface getSCInterface();
	
}
