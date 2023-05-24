public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

	public static final int SIMULATION_DURATION = 24 * 3600;


	public static int getOptimalNumberOfSpots(int hourlyRate) {
		//throw new UnsupportedOperationException("This method has not been implemented yet!");
		int n = 1;
		boolean repeat = true;


		while (repeat){


			System.out.println("\n" + "==== Setting lot capacity to: " + n + "====");

			double resultAverage= 0;

			for(int i = 1; i <= NUM_RUNS; i++){
				//Simulation run 1 (25ms); Queue length at the end of simulation run: 56
				ParkingLot lot = new ParkingLot(n);
				Simulator sim = new Simulator(lot,hourlyRate,SIMULATION_DURATION);
				long start = System.currentTimeMillis();
				sim.simulate();
				long end = System.currentTimeMillis();
				System.out.println("Simulation run " + i + "(" + (end-start) + "ms);" +
						" Queue length at the end of simulation run: " + sim.getIncomingQueueSize());
				resultAverage = resultAverage + sim.getIncomingQueueSize();
			}
			resultAverage = resultAverage / NUM_RUNS;

			if (resultAverage <= THRESHOLD){
				repeat = false;
			}
			else{
				n = n +1;
			}
		}
		return n;
	}

	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");
	}
}