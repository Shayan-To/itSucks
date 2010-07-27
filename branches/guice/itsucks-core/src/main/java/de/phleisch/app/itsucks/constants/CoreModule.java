/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 27.07.2010
 */

package de.phleisch.app.itsucks.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

import de.phleisch.app.itsucks.job.download.http.impl.HttpRetrieverFactory;
import de.phleisch.app.itsucks.job.download.impl.DataRetrieverFactory;
import de.phleisch.app.itsucks.job.download.impl.DownloadJobFactory;
import de.phleisch.app.itsucks.persistence.JobSerialization;
import de.phleisch.app.itsucks.persistence.impl.JAXBJobSerialization;
import de.phleisch.app.itsucks.persistence.jaxb.conversion.BeanConverter;
import de.phleisch.app.itsucks.persistence.jaxb.conversion.DispatcherConfigurationConverter;
import de.phleisch.app.itsucks.persistence.jaxb.conversion.DownloadJobConverter;
import de.phleisch.app.itsucks.persistence.jaxb.conversion.HttpRetrieverConfigurationConverter;
import de.phleisch.app.itsucks.persistence.jaxb.conversion.JobManagerConfigurationConverter;
import de.phleisch.app.itsucks.processing.DataProcessor;
import de.phleisch.app.itsucks.processing.download.http.impl.ContentParser;
import de.phleisch.app.itsucks.processing.download.http.impl.FilterFileSizeProcessor;
import de.phleisch.app.itsucks.processing.download.http.impl.HtmlParser;
import de.phleisch.app.itsucks.processing.download.http.impl.HttpRedirectorProcessor;
import de.phleisch.app.itsucks.processing.download.impl.PersistenceProcessor;

public class CoreModule extends AbstractModule {
	@Override 
	protected void configure() {
		
		//load default properties
		loadProperties(binder());

		MapBinder<Integer, DataProcessor> processorBinder
			= MapBinder.newMapBinder(binder(), Integer.class, DataProcessor.class);
		processorBinder.addBinding(10).to(FilterFileSizeProcessor.class);
		processorBinder.addBinding(20).to(ContentParser.class);
		processorBinder.addBinding(30).to(HttpRedirectorProcessor.class);
		processorBinder.addBinding(40).to(HtmlParser.class);
		processorBinder.addBinding(50).to(PersistenceProcessor.class);

		//bind(DataProcessorManager.class);
		
		MapBinder<String, DataRetrieverFactory> mapbinder
			= MapBinder.newMapBinder(binder(), String.class, DataRetrieverFactory.class);
		mapbinder.addBinding("http").to(HttpRetrieverFactory.class);
		mapbinder.addBinding("https").to(HttpRetrieverFactory.class);

		bind(DownloadJobFactory.class);
		
		
		
		Multibinder<BeanConverter> beanConverterBinder = 
			Multibinder.newSetBinder(binder(), BeanConverter.class);
		beanConverterBinder.addBinding().to(DownloadJobConverter.class);
		beanConverterBinder.addBinding().to(DispatcherConfigurationConverter.class);
		beanConverterBinder.addBinding().to(HttpRetrieverConfigurationConverter.class);
		beanConverterBinder.addBinding().to(JobManagerConfigurationConverter.class);
		
		bind(JobSerialization.class).to(JAXBJobSerialization.class);
/*
		
		<bean
			id="ApplicationConfiguration"
			class="de.phleisch.app.itsucks.configuration.impl.PropertyFileConfigurationImpl"
			singleton="true">
			
			<property name="comment">
				<value>ItSucks Configuration</value>
			</property>
			<property name="defaultConfigurationPath">
				<value>/defaultConfiguration.properties</value>
			</property>
			<property name="saveConfigurationPath">
				<value>$USER_HOME$/.itsucks/itsucks.properties</value>
			</property>
			
		</bean>
		*/
	}

	private void loadProperties(Binder binder) {
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("core_default_settings.properties");
		Properties appProperties = new Properties();
		try {
			appProperties.load(stream);
			Names.bindProperties(binder, appProperties);
		} catch (IOException e) {
			// This is the preferred way to tell Guice something went wrong
			binder.addError(e);
		}
	}

}