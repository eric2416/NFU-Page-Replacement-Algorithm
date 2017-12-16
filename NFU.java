//Eric Liu

package jnachos.kern.mem;

import jnachos.kern.JNachos;
import jnachos.kern.NachosProcess;
import jnachos.machine.Machine;
import jnachos.machine.TranslationEntry;

public class NFU implements PageReplacementAlgorithm {
	
	static int[] counters = new int[Machine.NumPhysPages];//each index corresponds to a physical page, tracks increments after clock interrupts
	
	public NFU() {
		
	}
	@Override
	public int chooseVictimPage() {
		
		int min = counters[0];
		int indexOfMin = 0;
		
		for(int i = 0; i < Machine.NumPhysPages; i++) {//loop to find index of lowest incremented page
			
			if(counters[i] < min) {
				min = counters[i];
				indexOfMin = i;
			}
		}
		return indexOfMin;//returns index of page with lowest counter to evict
	}
	
	public static void update() {//update is called every clock interrupt
		
		for(int i = 0; i < Machine.NumPhysPages; i++) {
			
			if(translationEntryForPhysicalPage(i) != null){
				
				if(translationEntryForPhysicalPage(i).use) {//if the page exists and has been used, increment once and reset the R bit
					counters[i]++;
					translationEntryForPhysicalPage(i).use = false;
				}
			}
		}
	}
	
	//GIVEN FUNCTION
	public static TranslationEntry translationEntryForPhysicalPage(int physicalPage) {
	    int victimPid = JNachos.getPageFrameMap()[physicalPage];
	    if (victimPid < 0) {
	    	return null;
	    }
	    NachosProcess victim = NachosProcess.mProcessHash.get(victimPid);
	    if (victim == null) {
	    	return null;
	    }
	    TranslationEntry entry = null;
	    for (int i = 0; i < victim.getSpace().mPageTable.length; i++) {
	    	if (victim.getSpace().mPageTable[i].physicalPage == physicalPage) {
	    		entry = victim.getSpace().mPageTable[i];
	    		break;
	    	}
	    }
	    return entry;
	}
}