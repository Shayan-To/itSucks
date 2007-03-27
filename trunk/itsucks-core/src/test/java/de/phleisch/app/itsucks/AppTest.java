/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks;

import java.io.File;
import java.net.URL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.phleisch.app.itsucks.filter.DownloadJobFilter;
import de.phleisch.app.itsucks.io.DownloadJob;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void testApp() throws Exception {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ApplicationConstants.CORE_SPRING_CONFIG_FILE);
		
		Dispatcher dispatcher = (Dispatcher) context.getBean("Dispatcher");
		assertNotNull(dispatcher);

		DownloadJobFilter filter = (DownloadJobFilter) context.getBean("DownloadJobFilter");
		//filter.setBaseURL(new URL("http://..."));
		//filter.setAllowOnlyRelativeReferences(true);
		filter.setAllowedHostNames(new String[] {".*"});
		filter.setMaxRecursionDepth(1);
		filter.setSaveToFileFilter(new String[] {".*[Jj][Pp][Gg]", ".*[Pp][Nn][Gg]", ".*[Gg][Ii][Ff]"});
		dispatcher.addJobFilter(filter);		
		
		DownloadJob job = (DownloadJob) context.getBean("DownloadJob");
		job.setUrl(new URL("http://itsucks.sourceforge.net/test/test.html"));
		job.setSavePath(new File("/tmp/crawl"));
		
		job.setIgnoreFilter(true);
		dispatcher.addJob(job);
		
		dispatcher.processJobs();
	}
}
