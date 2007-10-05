/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 07.03.2007
 */

package de.phleisch.app.itsucks.persistence;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import de.phleisch.app.itsucks.Job;
import de.phleisch.app.itsucks.filter.JobFilter;
import de.phleisch.app.itsucks.persistence.jaxb.ObjectFactory;
import de.phleisch.app.itsucks.persistence.jaxb.SerializedContextParameter;
import de.phleisch.app.itsucks.persistence.jaxb.SerializedJob;
import de.phleisch.app.itsucks.persistence.jaxb.SerializedJobFilter;
import de.phleisch.app.itsucks.persistence.jaxb.SerializedJobs;
import de.phleisch.app.itsucks.persistence.jaxb.conversion.BeanConverter;
import de.phleisch.app.itsucks.persistence.jaxb.conversion.BeanConverterManager;

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
	
	private BeanConverterManager mBeanConverterManager;
	
	public JAXBJobSerialization() {
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.persistence.JobSerialization#serialize(de.phleisch.app.itsucks.persistence.SerializableJobList, java.io.OutputStream)
	 */
	public void serialize(SerializableJobList pJobList, OutputStream pOutputStream) throws Exception {
		
		JAXBContext jc = createJAXBContext();
		
		Marshaller marshaller = jc.createMarshaller();
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				   new Boolean(true));

		ObjectFactory beanFactory = new ObjectFactory();
		
		SerializedJobs jobs = beanFactory.createSerializedJobs();
		jobs.setVersion("1.0");
		
		//convert jobs included in the pJobList
		for (Job job : pJobList.getJobs()) {
			BeanConverter beanConverter = 
				mBeanConverterManager.getClassConverter(job.getClass());
			jobs.getAny().add(beanConverter.convertClassToBean(job));
		}
		
		//convert filter included in the pJobList
		for (JobFilter jobFilter : pJobList.getFilters()) {
			BeanConverter beanConverter = 
				mBeanConverterManager.getClassConverter(jobFilter.getClass());
			jobs.getAny().add(beanConverter.convertClassToBean(jobFilter));
		}
		
		//convert context parameter
		if(pJobList.getContextParameter() != null) {
			
			Set<Entry<String, Object>> entrySet = 
				pJobList.getContextParameter().entrySet();

			for (Entry<String, Object> entry : entrySet) {
				
				BeanConverter beanConverter = mBeanConverterManager.getClassConverter(
						entry.getValue().getClass());
				
				SerializedContextParameter serializedContextParameter = 
					beanFactory.createSerializedContextParameter();
				
				serializedContextParameter.setName(entry.getKey());
				serializedContextParameter.getAny().add(
						beanConverter.convertClassToBean(entry.getValue()));

				jobs.getAny().add(serializedContextParameter);
			}
			
		}
		
		//convert configuration included in the pJobList
		if(pJobList.getDispatcherConfiguration() != null) {
			BeanConverter beanConverter = mBeanConverterManager.getClassConverter(
					pJobList.getDispatcherConfiguration().getClass());
			jobs.getAny().add(beanConverter.convertClassToBean(
					pJobList.getDispatcherConfiguration()));
		}
		

		marshaller.marshal(jobs, pOutputStream);
		
		pOutputStream.close();
	}

	private JAXBContext createJAXBContext() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("de.phleisch.app.itsucks.persistence.jaxb");
		return jc;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.persistence.JobSerialization#deserialize(java.io.InputStream)
	 */
	public SerializableJobList deserialize(InputStream pInputStream) throws Exception, ClassNotFoundException {	
		
		SerializableJobList deserializedJobList = new SerializableJobList();
		
		JAXBContext jc = createJAXBContext();
		
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		SerializedJobs jobs = (SerializedJobs) unmarshaller.unmarshal(pInputStream);
		
		for (Object serializedEntry : jobs.getAny()) {
			
			BeanConverter beanConverter = 
				mBeanConverterManager.getBeanConverter(serializedEntry.getClass());
			if(beanConverter == null) {
				throw new IllegalStateException("Cannot find bean converter for class: " + serializedEntry.getClass());
			}
			
			if(serializedEntry instanceof SerializedJob) {
				Job job = (Job) beanConverter.convertBeanToClass(serializedEntry);
				deserializedJobList.addJob(job);
			} else if(serializedEntry instanceof SerializedJobFilter) {
				
				JobFilter jobFilter = (JobFilter) beanConverter.convertBeanToClass(serializedEntry);
				deserializedJobList.addFilter(jobFilter);
			} else {
				throw new IllegalStateException("Unknown type found during unmarshalling: " + serializedEntry);
			}
			
		}
		
		return deserializedJobList;
	}
	
	public void setApplicationContext(ApplicationContext pContext) {
		mContext = pContext;
	}

	public BeanConverterManager getBeanConverterManager() {
		return mBeanConverterManager;
	}

	public void setBeanConverterManager(BeanConverterManager pBeanConverterManager) {
		mBeanConverterManager = pBeanConverterManager;
	}

}
