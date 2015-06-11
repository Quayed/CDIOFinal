package ase.andreas;

import java.util.Scanner;

public enum State {
	START {
		@Override
		State changeState(int x) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter UserID:");
			String in = scanner.nextLine();
			try {
				userID = Integer.parseInt(in);
			} catch (NumberFormatException e) {
				System.out.println("Not a number");
				return START;
			}
			return CONFIRM;
		}
	},
	CONFIRM {
		@Override
		State changeState(int x) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Andreas lorentzen?");
			String in = scanner.nextLine();
			
			if(in.equals("0")){
				return START;
			}
			else{
				return product;
			}
			
		}
	},
	product {
		@Override
		State changeState(int x) {
			return START;
			
		}
	},
	STEP1 {

		@Override
		State changeState(int x) {
			switch (x) {
			case 12:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return STEP2;
			case -1:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return START;
			case -2:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return STOP;
			default:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return INVALIDSTATE;
			}
		}
	},
	STEP2 {

		@Override
		State changeState(int x) {
			switch (x) {
			case 13:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return STEP3;
			case -1:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return START;
			case -2:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return STOP;
			default:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return INVALIDSTATE;
			}
		}
	},
	STEP3 {

		@Override
		State changeState(int x) {
			switch (x) {
			case 14:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return STOP;
			case -1:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return START;
			case -2:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return STOP;
			default:
				// TODO: do something
				System.out.println("I am now changing from state " + this + " with int argument x = " + x);
				return INVALIDSTATE;
			}
		}
	},
	INVALIDSTATE {

		@Override
		State changeState(int x) {
			// TODO: do something
			System.out.println("I am now changing from state " + this + " with int argument x = " + x);
			return STOP;
		}
	},
	STOP {

		@Override
		State changeState(int x) {
			// TODO: do something
			System.out.println("I am now changing from state " + this + " with int argument x = " + x);
			return STOP;
		}
	};
	abstract State changeState(int x);
	private static int userID;
}