import java.util.ArrayList;
public class RandomizeTest {

	public static void main(String[] args){
		RandomizeTest grouper = new RandomizeTest();
		grouper.groupify(new String[]{"1CMS","2CFS","3CFS","4CFS","5CMS","6CMS","7CFS","8CFG","9CMS","ACMS","BCMS","CCMS","DCMS","ECMS","FCMS","GCFS",
				"1BMS","2BFS","3BFS","4BFS","5BMS","6BMS","7BMS","8BMS","1DMS","2DFH","3DMG","4DMG","5DMG","6DMH","7DMH","8DMH","9DMS","ADMS"}
				,0);
	}
	
	public void groupify(String[] test2, int PRIORITIZE) {
		//final int PRIORITIZE = 1;
		System.out.println(test2.length);
		final int ATTEMPTS = 12000;
		int extras = test2.length % 4;
		int numGroups = test2.length / 4;
		if (extras == 3){
			numGroups++;
		}
		// TODO Auto-generated method stub
		//String[][] test = {{"1AM","2AM","3AM","4AM","5AM","6AM","7AF","8AF"},{"1BM","2BF","3BF","4BM","5BM","6BF","7BM","8BM"},
		//		{"1CM","2CM","3CM","4CM","5CM","6CM","7CM","8CF"},{"1DF","2DF","3DM","4DM","5DM","6DM","7DM","8DM"}}; //if person doesn't exist use ""
		int numTotalGirls = 0;
		boolean alreadyResetF = false;
		for (String x: test2){
			if (x.charAt(2) == 'F') numTotalGirls++;
		}
		boolean oddGirls = (numTotalGirls % 2 == 1);
		
		int numTotalNotSMCS = 0;
		//boolean alreadyResetS = false;
		for (String x: test2){
			if (x.charAt(3) != 'S') numTotalNotSMCS++;
		}
		boolean oddNotSMCS = (numTotalNotSMCS % 2 == 1);
		boolean restrictHouses = true;
		
		ArrayList<String[]> stuPairs = new ArrayList<String[]>();
		ArrayList<String[]> stuFPairs = new ArrayList<String[]>();
		//ArrayList<String[]> stuSPairs = new ArrayList<String[]>();
		int c=0;
		for (int n=0; n<5; n++){
			if (numTotalGirls % 2 == 1) oddGirls = true; //The boolean variable to set to true if we need to make one group with one girl only
			ArrayList<String[]> tempPairs = new ArrayList<String[]>(stuPairs); //Copy the current "all existing pairs"
			ArrayList<String[]> tempFPairs = new ArrayList<String[]>(stuFPairs);
			//ArrayList<String[]> tempSPairs = new ArrayList<String[]>(stuSPairs);
			ArrayList<String> stuUsed = new ArrayList<String>();
			ArrayList<String> results = new ArrayList<String>();
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
					count++;
					c++;
					flag = false;
					//for (int i=0; i<test.length; i++){ //Choose the random people
					//	int num = (int)(Math.random()*test[i].length);
					//	group[i] = test[i][num];
					//}
					/*
					for (int i=0; i<test2.length; i++){ //Choose the random people
						int num = (int)(Math.random()*test2[i].length);
						group[i] = test2[i][num];
					}
					group[3] = group[1];
					group[2] = group[0];
					while (group[3].equals(group[1])){
						int num = (int)(Math.random()*test2[1].length);
						group[3] = test2[1][num];
					}
					while (group[2].equals(group[0])){
						int num = (int)(Math.random()*test2[0].length);
						group[2] = test2[0][num];
					}
					*/
					
					int h = 0;
					while (h<group.length){
						int num = (int)(Math.random()*test2.length);
						group[h] = test2[num];
						for (int i=0; i<h; i++){
							if (group[i].equals(group[h])) h--;
						}
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
					
					for (int i=0; i<group.length; i++){ //Makes sure no people are reused (not efficient)
						if (stuUsed.indexOf(group[i]) > -1) flag = true;
					}
					int numgirls = 0; //Requires 2 or 0 girls in each group
					for (int i=0; i<group.length; i++){
						if (group[i].charAt(2) == 'F') numgirls++;
					}
					if (numgirls == 3 && oddGirls) {oddGirls = false;}
					else if (!(numgirls == 2 || numgirls == 0)) {flag = true;}
					
					/////////////////This one mixes humanities and global together
					if (restrictHouses){
						int numnotsmcs = 0;
						for (int i=0; i<group.length; i++){
							if (group[i].charAt(3) != 'S') numnotsmcs++;
						}
						if (!(numnotsmcs == 2 || numnotsmcs == 0)) flag = true;
						if (numnotsmcs == 3 && oddNotSMCS) {oddNotSMCS = false;}
						else if (!(numnotsmcs == 2 || numnotsmcs == 0)) {flag = true;}
					}
					/////////////////End Section////////////
					
					/*//////////////These two count humanities and global separately
					int numhum = 0;
					int numglo = 0;
					for (int i=0; i<group.length; i++){
						if (group[i].charAt(3) == 'H') numhum++;
						if (group[i].charAt(3) == 'G') numglo++;
					}
					if (!(numhum == 2 || numhum == 0)) flag = true;
					if (!(numglo == 2 || numglo == 0)) flag = true;
					///////////////End Section//////*/
					
					//for (int x=0; x<group.length; x++){
					//	System.out.print(group[x]+" ");
					//}
					//System.out.println();
				}
				if (count >= ATTEMPTS) break;
				for (int i=0; i<group.length-1; i++){
					for (int j=i+1; j<group.length; j++){		
						if (group[i].charAt(2) == 'F' || group[j].charAt(2) == 'F') //Considering letting girls repeat partners, since there are probably less girls
							tempFPairs.add(new String[]{group[i],group[j]});
						else {tempPairs.add(new String[]{group[i],group[j]});}
					}
				}
				
				a += (n+1)+": ";
				for (int x=0; x<group.length; x++){
					a += group[x]+" ";
				}
				for (int i=0; i<group.length; i++){
					stuUsed.add(group[i]);
				}
			}
			if (count < ATTEMPTS){
				results.add(a);
				System.out.println(a+"\t"+c);
				stuPairs = new ArrayList<String[]>(tempPairs);
				stuFPairs = new ArrayList<String[]>(tempFPairs);
				//stuSPairs = new ArrayList<String[]>(tempSPairs);
			} else {
				if (c > ((PRIORITIZE == 1) ? 5000000 : 2500000) && !alreadyResetF){
					alreadyResetF = true;
					stuFPairs = new ArrayList<String[]>();
					System.out.println("RESET girl pairs");
				}
				
				if (c > ((PRIORITIZE == 0) ? 5000000 : 2500000) && restrictHouses){
					restrictHouses = false;
				//	alreadyResetS = true;
				//	stuSPairs = new ArrayList<String[]>();
					System.out.println("REMOVED house restrictions");
				}

				if (c > 12000000){
					c = 0;
					stuPairs = new ArrayList<String[]>();
					stuFPairs = new ArrayList<String[]>();
					//stuSPairs = new ArrayList<String[]>();
					System.out.println("RESET ALL PAIRS");
				}
				n--;
			}
		}
		System.out.println(stuPairs.size());
		for (int i=0; i<stuPairs.size()-1; i++){
			for (int j=i+1; j<stuPairs.size(); j++){
				if ((stuPairs.get(i)[0].equals(stuPairs.get(j)[0]) && stuPairs.get(i)[1].equals(stuPairs.get(j)[1])) || (stuPairs.get(i)[1].equals(stuPairs.get(j)[0]) && stuPairs.get(i)[0].equals(stuPairs.get(j)[1]))) System.out.println(i+""+j+"Duplicates!!!!");
			}
		}
	}

}
