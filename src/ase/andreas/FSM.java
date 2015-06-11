package ase.andreas;
public class FSM {

	private State state;
	
	FSM() {
		this.state = State.START;
	}
	
	public void doSomething(int x) {
		this.state = this.state.changeState(x);
	}
	
	
	public static void main(String[] args) {
		// Golden scenario
		System.out.println("Now starting the golden scenario.");
		FSM f = new FSM();
		f.doSomething(11);
		f.doSomething(12);
		f.doSomething(13);
		f.doSomething(14);
		System.out.println("");
		
		// Abnormal, but valid state change
		System.out.println("Now starting the abnormal scenario.");
		f = new FSM();
		f.doSomething(11);
		f.doSomething(12);
		f.doSomething(-1);
		f.doSomething(14);
		System.out.println("");
		
		// Invalid state scenario
		System.out.println("Now starting the invalid scenario.");
		f = new FSM();
		f.doSomething(11);
		f.doSomething(12);
		f.doSomething(15);
		f.doSomething(14);
	}
	
}
