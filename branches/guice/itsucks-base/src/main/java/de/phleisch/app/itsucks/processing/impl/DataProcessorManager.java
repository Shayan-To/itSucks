/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.processing.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Provider;

import de.phleisch.app.itsucks.job.Job;
import de.phleisch.app.itsucks.processing.DataProcessor;
import de.phleisch.app.itsucks.processing.DataProcessorChain;

public class DataProcessorManager {

	private Collection<Provider<DataProcessor>> mProcessors; 
	
	public DataProcessorManager() {
		super();
	}
	
	@Inject
	public void setProcessors(Map<Integer, Provider<DataProcessor>> pList) {
		//TODO, use priority
		mProcessors = new ArrayList<Provider<DataProcessor>>(pList.values());
	}
	
	//@Inject
	public void setProcessors2(Set<DataProcessor> pList) {
		System.out.println("Test");
	}

	private List<DataProcessor> getProcessorsForJob(Job pJob) {
		
		ArrayList<DataProcessor> result = new ArrayList<DataProcessor>();
		
		for (Provider<DataProcessor> provider : mProcessors) {
			DataProcessor processor = provider.get();
			if(processor.supports(pJob)) {
				result.add(processor);
			}
		}
		
		return result;
	}

	public DataProcessorChain getProcessorChainForJob(Job pJob) {
		List<DataProcessor> processorsForJob = getProcessorsForJob(pJob);
		
		DataProcessorChain chain = new DataProcessorChainImpl(processorsForJob);
		chain.setJob(pJob);
		
		return chain;
	}

}
