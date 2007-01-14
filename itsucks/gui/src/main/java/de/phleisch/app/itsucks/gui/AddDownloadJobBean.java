/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.gui;

import java.util.ArrayList;
import java.util.List;

import de.phleisch.app.itsucks.filter.JobFilterInterface;
import de.phleisch.app.itsucks.io.DownloadJob;

public class AddDownloadJobBean {
	
	private DownloadJob mDownload;
	private List<JobFilterInterface> mFilterList;
	
	public DownloadJob getDownload() {
		return mDownload;
	}
	public void setDownload(DownloadJob pDownload) {
		mDownload = pDownload;
	}
	public List<JobFilterInterface> getFilterList() {
		return mFilterList;
	}
	public void setFilterList(List<JobFilterInterface> pFilterList) {
		mFilterList = pFilterList;
	}
	public void addFilter(JobFilterInterface pAdvancedFilter) {
		
		List<JobFilterInterface> newList = new ArrayList<JobFilterInterface>(getFilterList());
		newList.add(pAdvancedFilter);
		
		setFilterList(newList);
	}
	
}
