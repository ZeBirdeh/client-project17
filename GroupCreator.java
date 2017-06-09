import java.util.ArrayList;
public class GroupCreator {
	private DataParse datap;
	
	public GroupCreator(){
		datap = null;
	}
	
	public GroupCreator(DataParse dp){
		datap = dp;
	}
	
	public ArrayList<String> groupify(ArrayList<String> test, int PRIORITIZE) {
		//final int PRIORITIZE = 1;
		//System.out.println(test2.length);
		final int ATTEMPTS = 12000;
		int extras = test.size() % 4;
		int numGroups = test.size() / 4;
		if (extras == 3){
			numGroups++;
		}
		// TODO Auto-generated method stub
		//String[][] test = {{"1AM","2AM","3AM","4AM","5AM","6AM","7AF","8AF"},{"1BM","2BF","3BF","4BM","5BM","6BF","7BM","8BM"},
		//		{"1CM","2CM","3CM","4CM","5CM","6CM","7CM","8CF"},{"1DF","2DF","3DM","4DM","5DM","6DM","7DM","8DM"}}; //if person doesn't exist use ""
		int numTotalGirls = 0;
		boolean alreadyResetF = false;
		for (String x: test){
			if (x.charAt(2) == 'F') numTotalGirls++;
		}
		boolean oddGirls = (numTotalGirls % 2 == 1);
		boolean restrictG = true;
		if (numTotalGirls < test.size() / 3){
			
		}

		int numClemente = 0;
		boolean alreadyResetC = false;
		for (String x: test){
			if (x.charAt(4) == 'C') numClemente++;
		}
		int maxclemente = numClemente / numGroups + 1;
		boolean restrictC = true;
		
		int numTotalNotSMCS = 0;
		//boolean alreadyResetS = false;
		for (String x: test){
			if (x.charAt(3) != 'S') numTotalNotSMCS++;
		}
		boolean oddNotSMCS = (numTotalNotSMCS % 2 == 1);
		boolean restrictHouses = true;
		
		ArrayList<String[]> stuPairs = new ArrayList<String[]>();
		ArrayList<String[]> stuFPairs = new ArrayList<String[]>();
		//ArrayList<String[]> stuSPairs = new ArrayList<String[]>();

		ArrayList<String> test3 = new ArrayList<String>(test);
		
		int c=0;
		ArrayList<String> results = new ArrayList<String>();
		for (int n=0; n<5; n++){
			ArrayList<String> test4 = new ArrayList<String>(test);
			if (numTotalGirls % 2 == 1) oddGirls = true; //The boolean variable to set to true if we need to make one group with one girl only
			ArrayList<String[]> tempPairs = new ArrayList<String[]>(stuPairs); //Copy the current "all existing pairs"
			ArrayList<String[]> tempFPairs = new ArrayList<String[]>(stuFPairs);
			//ArrayList<String[]> tempSPairs = new ArrayList<String[]>(stuSPairs);
			//ArrayList<String> stuUsed = new ArrayList<String>();
			int count = 0;
			String a = "";
			String[] group = new String[4];
			for (int m=0; m<numGroups; m++){
				if ((m>numGroups - 3 && extras == 2) || (m>numGroups - 2 && extras == 1)){
					group = new String[5];
				}
				if (m>numGroups - 2 && extras == 3){
					group = new String[3];
				}
				boolean flag = true;
				count = 0;
				while (flag && count<ATTEMPTS){
					test3 = new ArrayList<String>(test4);
					count++;
					c++;
					flag = false;
					
					int h = 0;
					while (h<group.length){ //Generate the group
						int num = (int)(Math.random()*test3.size());
						group[h] = test3.get(num);
						test3.remove(num);
						h++;
					}
					
					for (int i=0; i<tempPairs.size(); i++){	//Not very optimized way to check every single pair of people used so far
						boolean has1 = false, has2 = false;
						for (int j=0; j<group.length; j++){
							if (group[j]==tempPairs.get(i)[0]) has1 = true;
							if (group[j]==tempPairs.get(i)[1]) has2 = true;
						}
						if (has1 && has2) flag = true;
					}
					for (int i=0; i<tempFPairs.size(); i++){
						boolean has1 = false, has2 = false;
						for (int j=0; j<group.length; j++){
							if (group[j]==tempFPairs.get(i)[0]) has1 = true;
							if (group[j]==tempFPairs.get(i)[1]) has2 = true;
						}
						if (has1 && has2) flag = true;
					}
					
					if (restrictG){
						int numgirls = 0; //Requires 2 or 0 girls in each group
						for (int i=0; i<group.length; i++){
							if (group[i].charAt(2) == 'F') numgirls++;
						}
						if (numgirls == 3 && oddGirls) {oddGirls = false;}
						else if (!(numgirls == 2 || numgirls == 0)) {flag = true;}
					}
					if (restrictC){
						int numc = 0; //Requires 2 or 0 clemente kids in each group
						for (int i=0; i<group.length; i++){
							if (group[i].charAt(4) == 'C') numc++;
						}
						if (numc > maxclemente) {flag = true;}
					}
					/////////////////This one mixes humanities and global together
					if (restrictHouses){
						int numnotsmcs = 0;
						for (int i=0; i<group.length; i++){
							if (group[i].charAt(3) != 'S') numnotsmcs++;
						}
						if (numnotsmcs == 3 && oddNotSMCS) {oddNotSMCS = false;}
						else if (!(numnotsmcs == 2 || numnotsmcs == 0)) {flag = true;}
					}
					/////////////////End Section////////////
				}
				if (count >= ATTEMPTS) break;
				for (int i=0; i<group.length-1; i++){
					for (int j=i+1; j<group.length; j++){		
						if (group[i].charAt(2) == 'F' && group[j].charAt(2) == 'F') 
							tempFPairs.add(new String[]{group[i],group[j]});
						else {tempPairs.add(new String[]{group[i],group[j]});}
					}
				}
				if (datap != null){
					datap.incrementProgress(c/(3100*test.size()));
				}
				
				a += ", ";
				for (int x=0; x<group.length; x++){
					a += group[x]+" ";
				}
				test4 = new ArrayList<String>(test3);
			}
			if (count < ATTEMPTS){
				results.add(a);
				//System.out.println(results);
				System.out.println(a+"\t"+c);
				stuPairs = new ArrayList<String[]>(tempPairs);
				stuFPairs = new ArrayList<String[]>(tempFPairs);
				//stuSPairs = new ArrayList<String[]>(tempSPairs);
			} else {
				if (c > ((PRIORITIZE == 1) ? 400000*numTotalGirls : 300000*numTotalGirls) && !alreadyResetF){
					alreadyResetF = true;
					//stuFPairs = new ArrayList<String[]>();
					restrictG = false;
					System.out.println("RESET girl pairs");
				}
				
				if (c > ((PRIORITIZE == 0) ? 400000*numTotalNotSMCS : 30000*numTotalNotSMCS) && restrictHouses){
					restrictHouses = false;
				//	alreadyResetS = true;
				//	stuSPairs = new ArrayList<String[]>();
					System.out.println("REMOVED house restrictions");
				}

				if (c > 300000*test.size()){
					c = 0;
					stuPairs = new ArrayList<String[]>();
					stuFPairs = new ArrayList<String[]>();
					//stuSPairs = new ArrayList<String[]>();
					restrictG = true;
					restrictHouses = true;
					System.out.println("RESET ALL PAIRS");
				}
				n--;
			}

		}
		//System.out.println(stuPairs.size());
		datap.incrementProgress(100);
		return results;
	}

}
