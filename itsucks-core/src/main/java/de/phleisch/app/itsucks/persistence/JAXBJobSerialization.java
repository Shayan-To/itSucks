/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 07.03.2007
 */

package de.phleisch.app.itsucks.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import de.phleisch.app.itsucks.Job;
import de.phleisch.app.itsucks.io.DownloadJob;
import de.phleisch.app.itsucks.persistence.jaxb.ObjectFactory;
import de.phleisch.app.itsucks.persistence.jaxb.SerializedDownloadJob;
import de.phleisch.app.itsucks.persistence.jaxb.SerializedDownloadJobs;
import de.phleisch.app.itsucks.persistence.jaxb.conversion.BeanConverter;
import de.phleisch.app.itsucks.persistence.jaxb.conversion.BeanConverterManager;
import de.phleisch.app.itsucks.persistence.jaxb.conversion.DownloadJobConverter;

/**
 * This class implements the JobSerialization interface using the 
 * default object.serialize feature.
 * 
 * @author olli
 *
 */
public class JAXBJobSerialization
		extends AbstractJobSerialization
		implements ApplicationContextAware, JobSerialization {

	@SuppressWarnings("unused")
	private ApplicationContext mContext;
	
	public JAXBJobSerialization() {
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.persistence.JobSerialization#serialize(de.phleisch.app.itsucks.persistence.SerializableJobList, java.io.OutputStream)
	 */
	public void serialize(SerializableJobList pJobList, OutputStream pOutputStream) throws Exception {
		
		JAXBContext jc = JAXBContext.newInstance("de.phleisch.app.itsucks.persistence.jaxb");
		
		Marshaller marshaller = jc.createMarshaller();
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				   new Boolean(true));

		ObjectFactory beanFactory = new ObjectFactory();
		
		//TODO move to spring
		BeanConverterManager manager = new BeanConverterManager();
		DownloadJobConverter converter = new DownloadJobConverter();
		converter.setBeanFactory(beanFactory);
		manager.registerClassConverter(DownloadJob.class, converter);
		
		
		SerializedDownloadJobs jobs = beanFactory.createSerializedDownloadJobs();
		jobs.setVersion("1.0");
		
		for (Job job : pJobList.getJobs()) {
			
			BeanConverter beanConverter = manager.getClassConverter(job.getClass());
			
			//TODO this is not really good, fix this
			jobs.getSerializedDownloadJob().add((SerializedDownloadJob) 
					beanConverter.convertClassToBean(job));
			
		}
		
		marshaller.marshal(jobs, pOutputStream);
		
		pOutputStream.close();
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.persistence.JobSerialization#deserialize(java.io.InputStream)
	 */
	public SerializableJobList deserialize(InputStream pInputStream) throws IOException, ClassNotFoundException {	
		
		ObjectInputStream objectInput = new ObjectInputStream(pInputStream);
		SerializableJobList jobList = (SerializableJobList) objectInput.readObject();
		objectInput.close();
		
		for (Job job : jobList.getJobs()) {
			mJobFactory.injectDependencies(job);
		}
		
		
		return jobList;
	}
	
	public void setApplicationContext(ApplicationContext pContext) {
		mContext = pContext;
	}

}
