/*
 * EditDownloadJobGroupPanel.java
 *
 * Created on __DATE__, __TIME__
 */

package de.phleisch.app.itsucks.gui.job.panel;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import de.phleisch.app.itsucks.SpringContextSingelton;
import de.phleisch.app.itsucks.filter.JobFilter;
import de.phleisch.app.itsucks.filter.download.http.impl.ChangeHttpResponseCodeBehaviourFilter;
import de.phleisch.app.itsucks.filter.download.http.impl.ChangeHttpResponseCodeBehaviourFilter.HttpResponseCodeBehaviourHostConfig;
import de.phleisch.app.itsucks.filter.download.impl.ContentFilter;
import de.phleisch.app.itsucks.filter.download.impl.DownloadJobFilter;
import de.phleisch.app.itsucks.filter.download.impl.FileSizeFilter;
import de.phleisch.app.itsucks.filter.download.impl.MaxLinksToFollowFilter;
import de.phleisch.app.itsucks.filter.download.impl.RegExpJobFilter;
import de.phleisch.app.itsucks.filter.download.impl.TimeLimitFilter;
import de.phleisch.app.itsucks.filter.download.impl.ContentFilter.ContentFilterConfig;
import de.phleisch.app.itsucks.filter.download.impl.RegExpJobFilter.RegExpFilterRule;
import de.phleisch.app.itsucks.gui.job.panel.DownloadJobSpecialRulesPanel.HttpStatusCodeBehaviourListElement;
import de.phleisch.app.itsucks.gui.util.ExtendedListModel;
import de.phleisch.app.itsucks.io.http.impl.HttpRetrieverConfiguration;
import de.phleisch.app.itsucks.io.http.impl.HttpRetrieverResponseCodeBehaviour;
import de.phleisch.app.itsucks.io.http.impl.HttpRetrieverResponseCodeBehaviour.ResponseCodeRange;
import de.phleisch.app.itsucks.job.Job;
import de.phleisch.app.itsucks.job.download.impl.DownloadJobFactory;
import de.phleisch.app.itsucks.job.download.impl.UrlDownloadJob;
import de.phleisch.app.itsucks.persistence.SerializableDispatcherConfiguration;
import de.phleisch.app.itsucks.persistence.SerializableJobPackage;

/**
 *
 * @author  __USER__
 */
public class EditDownloadJobGroupPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 8250877774474103209L;

	/** Creates new form EditDownloadJobGroupPanel */
	public EditDownloadJobGroupPanel() {
		initComponents();
	}

	public void loadJob(SerializableJobPackage pJobList) {

		UrlDownloadJob pJob = (UrlDownloadJob) pJobList.getJobs().get(0);
		List<URL> urlList = new ArrayList<URL>();
		for (Job job : pJobList.getJobs()) {
			urlList.add(((UrlDownloadJob) job).getUrl());
		}
		List<JobFilter> pFilters = pJobList.getFilters();

		SerializableDispatcherConfiguration dispatcherConfiguration = pJobList
				.getDispatcherConfiguration();

		HttpRetrieverConfiguration httpRetrieverConfiguration = (HttpRetrieverConfiguration) pJobList
				.getContextParameter(HttpRetrieverConfiguration.CONTEXT_PARAMETER_HTTP_RETRIEVER_CONFIGURATION);

		DownloadJobFilter downloadJobFilter = null;
		MaxLinksToFollowFilter maxLinksToFollowFilter = null;
		RegExpJobFilter regExpJobFilter = null;
		FileSizeFilter fileSizeFilter = null;
		ChangeHttpResponseCodeBehaviourFilter httpResponseCodeFilter = null;
		TimeLimitFilter timeLimitFilter = null;
		ContentFilter contentFilter = null;

		for (JobFilter jobFilter : pFilters) {
			if (jobFilter instanceof DownloadJobFilter) {
				downloadJobFilter = (DownloadJobFilter) jobFilter;
				continue;
			}
			if (jobFilter instanceof MaxLinksToFollowFilter) {
				maxLinksToFollowFilter = (MaxLinksToFollowFilter) jobFilter;
				continue;
			}
			if (jobFilter instanceof RegExpJobFilter) {
				regExpJobFilter = (RegExpJobFilter) jobFilter;
				continue;
			}
			if (jobFilter instanceof FileSizeFilter) {
				fileSizeFilter = (FileSizeFilter) jobFilter;
				continue;
			}
			if (jobFilter instanceof ChangeHttpResponseCodeBehaviourFilter) {
				httpResponseCodeFilter = (ChangeHttpResponseCodeBehaviourFilter) jobFilter;
				continue;
			}			
			if (jobFilter instanceof TimeLimitFilter) {
				timeLimitFilter = (TimeLimitFilter) jobFilter;
				continue;
			}
			if (jobFilter instanceof ContentFilter) {
				contentFilter = (ContentFilter) jobFilter;
			}
		}

		//load basic panel
		this.downloadJobBasicPanel.nameTextField.setText(pJob.getName());
		this.downloadJobBasicPanel.setUrlList(urlList);
		this.downloadJobBasicPanel.savePathTextField.setText(pJob.getSavePath()
				.getAbsolutePath());
		this.downloadJobBasicPanel.maxRetriesTextField.setText(String
				.valueOf(pJob.getMaxRetryCount()));

		if (dispatcherConfiguration != null) {
			if (dispatcherConfiguration.getWorkerThreads() != null) {
				this.downloadJobBasicPanel.workingThreadsTextField
						.setText(String.valueOf(dispatcherConfiguration
								.getWorkerThreads()));
			}
		}

		if (httpRetrieverConfiguration != null) {

			if (httpRetrieverConfiguration.getMaxConnectionsPerServer() != null) {
				this.downloadJobBasicPanel.maxConnectionsTextField
						.setText(String.valueOf(httpRetrieverConfiguration
								.getMaxConnectionsPerServer()));
			}
			
			if (httpRetrieverConfiguration.getBandwidthLimit() != null) {
				
				final int kbytes = 1024;
				final int mbytes = 1024 * 1024;
				
				int limit = httpRetrieverConfiguration.getBandwidthLimit();
				int index = 0;
				
				if((limit % mbytes) == 0) {
					limit /= mbytes;
					index = 2;
				} else if((limit % kbytes) == 0) {
					limit /= kbytes;
					index = 1;
				} 
				
				this.downloadJobBasicPanel.enableBandwidthLimitCheckBox.setSelected(true);
				this.downloadJobBasicPanel.bandwidthLimitComboBox.setSelectedIndex(index);
				this.downloadJobBasicPanel.bandwidthLimitTextField.setText(String.valueOf(limit));
			}

			if (httpRetrieverConfiguration.isProxyEnabled()) {
				this.downloadJobBasicPanel.enableProxyCheckBox
						.setSelected(true);
				this.downloadJobBasicPanel.proxyServerTextField
						.setText(httpRetrieverConfiguration.getProxyServer());
				this.downloadJobBasicPanel.proxyPortTextField.setText(String
						.valueOf(httpRetrieverConfiguration.getProxyPort()));

				if (httpRetrieverConfiguration.isProxyAuthenticationEnabled()) {

					this.downloadJobBasicPanel.enableAuthenticationCheckBox
							.setSelected(true);
					this.downloadJobBasicPanel.authenticationUserTextField
							.setText(httpRetrieverConfiguration.getProxyUser());
					this.downloadJobBasicPanel.authenticationPasswordTextField
							.setText(httpRetrieverConfiguration
									.getProxyPassword());
				}
			}
			
			if(httpRetrieverConfiguration.getUserAgent() != null) {
				this.downloadJobBasicPanel.userAgentCheckBox
					.setSelected(true);
				this.downloadJobBasicPanel.userAgentTextField
					.setText(httpRetrieverConfiguration.getUserAgent());
			}
		}

		//load simple rules
		if (maxLinksToFollowFilter != null) {
			this.downloadJobSimpleRulesPanel.linksToFollowTextField
					.setText(String.valueOf(maxLinksToFollowFilter
							.getMaxLinksToFollow()));
		} else {
			this.downloadJobSimpleRulesPanel.linksToFollowTextField
					.setText("-1");
		}

		if (timeLimitFilter != null) {
			this.downloadJobSimpleRulesPanel.timeLimitTextField
					.setText(timeLimitFilter.getTimeLimitAsText());
		} else {
			this.downloadJobSimpleRulesPanel.timeLimitTextField.setText("-1");
		}

		if (downloadJobFilter != null) {

			this.downloadJobSimpleRulesPanel.recursionDepthTextField
					.setText(String.valueOf(downloadJobFilter
							.getMaxRecursionDepth()));

			if (downloadJobFilter.getURLPrefix() != null) {
				this.downloadJobSimpleRulesPanel.urlPrefixCheckBox
						.setSelected(true);
				this.downloadJobSimpleRulesPanel.urlPrefixTextField
						.setText(downloadJobFilter.getURLPrefix()
								.toExternalForm());
			} else {
				this.downloadJobSimpleRulesPanel.urlPrefixCheckBox
						.setSelected(false);
			}

			this.downloadJobSimpleRulesPanel.hostnameFilterTableModel
					.setRowCount(0);
			String[] allowedHostNames = downloadJobFilter.getAllowedHostNames();
			for (String string : allowedHostNames) {
				this.downloadJobSimpleRulesPanel.hostnameFilterTableModel
						.addRow(new Object[] { string });
			}

			this.downloadJobSimpleRulesPanel.saveToDiskFilterTabelModel
					.setRowCount(0);
			String[] saveToDiskFilter = downloadJobFilter.getSaveToDisk();
			for (String string : saveToDiskFilter) {
				this.downloadJobSimpleRulesPanel.saveToDiskFilterTabelModel
						.addRow(new Object[] { string });
			}

		}

		//load special rules
		if (fileSizeFilter != null) {

			this.downloadJobSpecialRulesPanel.fileSizeEnableCheckBox
					.setSelected(true);

			this.downloadJobSpecialRulesPanel.fileSizeMinField
					.setText(fileSizeFilter.getMinSizeAsText());
			this.downloadJobSpecialRulesPanel.fileSizeMaxField
					.setText(fileSizeFilter.getMaxSizeAsText());
			this.downloadJobSpecialRulesPanel.fileSizeNotKnownComboBox
					.setSelectedIndex(fileSizeFilter.isAcceptWhenLengthNotSet() ? 0
							: 1);
		}
		
		if(httpResponseCodeFilter != null) {
			
			this.downloadJobSpecialRulesPanel.httpStatusCodeBehaviourCheckBox
				.setSelected(true);
			
			ExtendedListModel model = 
				this.downloadJobSpecialRulesPanel.httpStatusCodeBehaviourEditListPanel.getListModel();
			
			for (HttpResponseCodeBehaviourHostConfig hostConfig : 
				httpResponseCodeFilter.getConfigList()) {
				
				HttpRetrieverResponseCodeBehaviour responseCodeBehaviour = 
					hostConfig.getResponseCodeBehaviour();
				
				for (ResponseCodeRange responseCodeRange : 
					responseCodeBehaviour.getConfigurationList()) {
					
					HttpStatusCodeBehaviourListElement element = 
						this.downloadJobSpecialRulesPanel.new HttpStatusCodeBehaviourListElement();
					
					element.setHostnameRegexp(hostConfig.getHostnameRegexp());
					element.setResponseCodeFrom(Integer.toString(responseCodeRange.getResponseCodeFrom()));
					element.setResponseCodeTo(Integer.toString(responseCodeRange.getResponseCodeTo()));
					element.setAction(
							this.downloadJobSpecialRulesPanel.findIndexForHttpRetrieverResponseCodeBehaviour(
									responseCodeRange.getAction()));
					if(responseCodeRange.getTimeToWaitBetweenRetry() != null) {
						element.setTimeToWaitBetweenRetry(Long.toString(responseCodeRange.getTimeToWaitBetweenRetry()));
					}

					model.addElement(element);
				}
			}
		}

		//load advanced rules
		if (regExpJobFilter != null) {
			ExtendedListModel model = this.downloadJobRegExpRulesPanel.regExpFilterListModel;
//			this.downloadJobRegExpRulesPanel.regExpFilterList.setModel(model);
			for (RegExpFilterRule jobFilterRule : regExpJobFilter.getFilterRules()) {
				model
						.addElement(this.downloadJobRegExpRulesPanel.new RegExpFilterRuleListElement(
								jobFilterRule));
			}
		}
		
		//load content filter
		if (contentFilter != null) {
			ExtendedListModel model = this.downloadJobContentFilterPanel.contentFilterListModel;
			
			for (ContentFilterConfig jobFilterRule : contentFilter.getContentFilterConfigList()) {
				model.addElement(this.downloadJobContentFilterPanel.new ContentFilterRuleListElement(
								jobFilterRule));
			}
			
		}

	}

	public SerializableJobPackage buildJob() {

		if (!validatePanels())
			return null;

		DownloadJobFactory jobFactory = (DownloadJobFactory) SpringContextSingelton
				.getApplicationContext().getBean("JobFactory");
		UrlDownloadJob basicJob = jobFactory.createDownloadJob();
		List<URL> urls = new ArrayList<URL>();
		List<JobFilter> jobFilterList = new ArrayList<JobFilter>();
		SerializableDispatcherConfiguration dispatcherConfiguration = new SerializableDispatcherConfiguration();
		HttpRetrieverConfiguration retrieverConfiguration = new HttpRetrieverConfiguration();

		//build download job
		basicJob.setIgnoreFilter(true);
		basicJob.setState(UrlDownloadJob.STATE_OPEN);

		//basic panel
		basicJob.setName(this.downloadJobBasicPanel.nameTextField.getText());

		try {
			urls.addAll(this.downloadJobBasicPanel.getUrlList());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		basicJob.setSavePath(new File(
				this.downloadJobBasicPanel.savePathTextField.getText()));
		basicJob.setMaxRetryCount(Integer
				.parseInt(this.downloadJobBasicPanel.maxRetriesTextField
						.getText()));

		dispatcherConfiguration.setWorkerThreads(Integer
				.parseInt(this.downloadJobBasicPanel.workingThreadsTextField
						.getText()));

		retrieverConfiguration.setMaxConnectionsPerServer(Integer
				.parseInt(this.downloadJobBasicPanel.maxConnectionsTextField
						.getText()));

		//proxy configuration
		if (this.downloadJobBasicPanel.enableProxyCheckBox.isSelected()) {
			retrieverConfiguration.setProxyEnabled(true);

			retrieverConfiguration
					.setProxyServer(this.downloadJobBasicPanel.proxyServerTextField
							.getText());

			retrieverConfiguration.setProxyPort(Integer
					.parseInt(this.downloadJobBasicPanel.proxyPortTextField
							.getText()));
		} else {
			retrieverConfiguration.setProxyEnabled(false);
		}

		if (this.downloadJobBasicPanel.enableAuthenticationCheckBox
				.isSelected()) {
			retrieverConfiguration.setProxyAuthenticationEnabled(true);

			retrieverConfiguration
					.setProxyUser(this.downloadJobBasicPanel.authenticationUserTextField
							.getText());

			retrieverConfiguration
					.setProxyPassword(this.downloadJobBasicPanel.authenticationPasswordTextField
							.getText());
		} else {
			retrieverConfiguration.setProxyAuthenticationEnabled(false);
		}
		
		//user agent
		if (this.downloadJobBasicPanel.userAgentCheckBox.isSelected()) {
			retrieverConfiguration
					.setUserAgent(this.downloadJobBasicPanel.userAgentTextField
							.getText());
		}
		
		//bandwidth limit
		if (this.downloadJobBasicPanel.enableBandwidthLimitCheckBox.isSelected()) {
			int bandwidthLimit = Integer.parseInt(this.downloadJobBasicPanel.bandwidthLimitTextField
					.getText());
			int multiplier = (int) Math.pow(1024, this.downloadJobBasicPanel.bandwidthLimitComboBox.getSelectedIndex());
			bandwidthLimit *= multiplier;
			
			retrieverConfiguration.setBandwidthLimit(bandwidthLimit);
		}

		//simple rules panel
		DownloadJobFilter downloadJobFilter = new DownloadJobFilter();
		jobFilterList.add(downloadJobFilter);

		String maxRecursionDepth = this.downloadJobSimpleRulesPanel.recursionDepthTextField
				.getText();
		if (maxRecursionDepth != null && maxRecursionDepth.length() > 0) {
			downloadJobFilter.setMaxRecursionDepth(Integer
					.parseInt(maxRecursionDepth));
		}

		String maxLinksToFollow = this.downloadJobSimpleRulesPanel.linksToFollowTextField
				.getText();
		if (maxLinksToFollow != null && maxLinksToFollow.length() > 0) {
			MaxLinksToFollowFilter maxLinksToFollowFilter = new MaxLinksToFollowFilter();
			maxLinksToFollowFilter.setMaxLinksToFollow(Integer
					.parseInt(maxLinksToFollow));

			if (maxLinksToFollowFilter.getMaxLinksToFollow() > -1) {
				jobFilterList.add(maxLinksToFollowFilter);
			}
		}

		String timeLimit = this.downloadJobSimpleRulesPanel.timeLimitTextField
				.getText();
		if (timeLimit != null && timeLimit.length() > 0) {
			TimeLimitFilter timeLimitFilter = new TimeLimitFilter();
			timeLimitFilter.setTimeLimitAsText(timeLimit);
			if (timeLimitFilter.getTimeLimit() > -1) {
				jobFilterList.add(timeLimitFilter);
			}
		}

		if (this.downloadJobSimpleRulesPanel.urlPrefixCheckBox.isSelected()) {
			try {
				downloadJobFilter.setURLPrefix(new URL(
						this.downloadJobSimpleRulesPanel.urlPrefixTextField
								.getText()));
			} catch (MalformedURLException e) {
				throw new RuntimeException("Bad URL: "
						+ this.downloadJobSimpleRulesPanel.urlPrefixTextField
								.getText(), e);
			}
		} else {
			downloadJobFilter.setURLPrefix(null);
		}

		int hostnameFilterRowCount = this.downloadJobSimpleRulesPanel.hostnameFilterTableModel
				.getRowCount();
		List<String> allowedHostnames = new ArrayList<String>();
		for (int i = 0; i < hostnameFilterRowCount; i++) {
			allowedHostnames
					.add((String) this.downloadJobSimpleRulesPanel.hostnameFilterTableModel
							.getValueAt(i, 0));
		}
		downloadJobFilter.setAllowedHostNames(allowedHostnames
				.toArray(new String[allowedHostnames.size()]));

		int saveToDiskFilterRowCount = this.downloadJobSimpleRulesPanel.saveToDiskFilterTabelModel
				.getRowCount();
		List<String> saveToDiskFilters = new ArrayList<String>();
		for (int i = 0; i < saveToDiskFilterRowCount; i++) {
			saveToDiskFilters
					.add((String) this.downloadJobSimpleRulesPanel.saveToDiskFilterTabelModel
							.getValueAt(i, 0));
		}
		downloadJobFilter.setSaveToDisk(saveToDiskFilters
				.toArray(new String[saveToDiskFilters.size()]));

		//file size filter
		if (this.downloadJobSpecialRulesPanel.fileSizeEnableCheckBox
				.isSelected()) {

			FileSizeFilter fileSizeFilter = new FileSizeFilter();

			fileSizeFilter
					.setMinSizeAsText(this.downloadJobSpecialRulesPanel.fileSizeMinField
							.getText().trim());
			fileSizeFilter
					.setMaxSizeAsText(this.downloadJobSpecialRulesPanel.fileSizeMaxField
							.getText().trim());

			fileSizeFilter
					.setAcceptWhenLengthNotSet(this.downloadJobSpecialRulesPanel.fileSizeNotKnownComboBox
							.getSelectedIndex() > 0 ? false : true);

			jobFilterList.add(fileSizeFilter);
		}
		
		//http status code filter
		if (this.downloadJobSpecialRulesPanel.httpStatusCodeBehaviourCheckBox
				.isSelected()) {
			
			ChangeHttpResponseCodeBehaviourFilter httpResponseCodeFilter = 
				new ChangeHttpResponseCodeBehaviourFilter();
			
			ExtendedListModel listModel = 
				this.downloadJobSpecialRulesPanel.httpStatusCodeBehaviourEditListPanel.getListModel();
			
			Object[] elements = listModel.toArray();
			for (int i = 0; i < elements.length; i++) {
				HttpStatusCodeBehaviourListElement element = 
					(HttpStatusCodeBehaviourListElement) elements[i];
				
				HttpRetrieverResponseCodeBehaviour.Action action = 
					this.downloadJobSpecialRulesPanel.mHttpResponseCodeFilterActions.get(element.getAction()).getValue();
				
				ResponseCodeRange responseCodeRange = 
					new ResponseCodeRange(
							Integer.parseInt(element.getResponseCodeFrom()),
							Integer.parseInt(element.getResponseCodeTo()),
							action
					);
				
				if(action.equals(HttpRetrieverResponseCodeBehaviour.Action.FAILED_BUT_RETRYABLE)) {
					responseCodeRange.setTimeToWaitBetweenRetry(
							Long.parseLong(element.getTimeToWaitBetweenRetry()));
				}
				
				HttpRetrieverResponseCodeBehaviour responseCodeBehaviour =
					new HttpRetrieverResponseCodeBehaviour();
				responseCodeBehaviour.add(responseCodeRange);
				
				HttpResponseCodeBehaviourHostConfig hostConfig = 
					new HttpResponseCodeBehaviourHostConfig(element.getHostnameRegexp(), 
							responseCodeBehaviour);
				
				httpResponseCodeFilter.addConfig(hostConfig);
			}
			
			jobFilterList.add(httpResponseCodeFilter);
		}
		
		//advanced rules
		int advancedFilterCount = this.downloadJobRegExpRulesPanel.regExpFilterListModel
				.getSize();
		if (advancedFilterCount > 0) {
			RegExpJobFilter regExpFilter = new RegExpJobFilter();

			for (int i = 0; i < advancedFilterCount; i++) {
				RegExpFilterRule rule = ((DownloadJobRegExpRulesPanel.RegExpFilterRuleListElement) this.downloadJobRegExpRulesPanel.regExpFilterListModel
						.get(i)).getRule();
				regExpFilter.addFilterRule(rule);
			}

			jobFilterList.add(regExpFilter);
		}

		//content filter
		int contentFilterCount = this.downloadJobContentFilterPanel.contentFilterListModel
				.getSize();
		if (contentFilterCount > 0) {
			ContentFilter contentFilter = new ContentFilter();

			for (int i = 0; i < contentFilterCount; i++) {
				ContentFilterConfig rule = ((DownloadJobContentFilterPanel.ContentFilterRuleListElement) this.downloadJobContentFilterPanel.contentFilterListModel
						.get(i)).getRule();
				contentFilter.addContentFilterConfig(rule);
			}

			jobFilterList.add(contentFilter);
		}

		//build result
		SerializableJobPackage result = new SerializableJobPackage();

		for (URL url : urls) {
			UrlDownloadJob job = jobFactory.createDownloadJob();

			job.setUrl(url);
			job.setIgnoreFilter(basicJob.isIgnoreFilter());
			job.setState(basicJob.getState());
			job.setName(basicJob.getName());
			job.setSavePath(basicJob.getSavePath());
			job.setMaxRetryCount(basicJob.getMaxRetryCount());

			result.addJob(job);
		}

		result.setFilters(jobFilterList);
		result.setDispatcherConfiguration(dispatcherConfiguration);
		result
				.putContextParameter(
						HttpRetrieverConfiguration.CONTEXT_PARAMETER_HTTP_RETRIEVER_CONFIGURATION,
						retrieverConfiguration);

		return result;
	}

	private boolean validatePanels() {

		boolean result = true;

		List<String> errorsBasicPanel = downloadJobBasicPanel.validateFields();
		List<String> errorsSimplePanel = downloadJobSimpleRulesPanel
				.validateFields();
		List<String> errorsSpecialPanel = downloadJobSpecialRulesPanel
				.validateFields();

		if (errorsBasicPanel.size() > 0) {
			result = false;
			tabbedPane.setSelectedComponent(downloadJobBasicPanel);
			displayErrors(errorsBasicPanel);
		} else if (errorsSimplePanel.size() > 0) {
			result = false;
			tabbedPane.setSelectedComponent(downloadJobSimpleRulesPanel);
			displayErrors(errorsSimplePanel);
		} else if (errorsSpecialPanel.size() > 0) {
			result = false;
			tabbedPane.setSelectedComponent(downloadJobSpecialRulesPanel);
			displayErrors(errorsSpecialPanel);
		}

		return result;
	}

	private void displayErrors(List<String> errorsBasicPanel) {
		StringBuffer buffer = new StringBuffer();
		for (String string : errorsBasicPanel) {
			buffer.append(string + '\n');
		}

		JOptionPane.showMessageDialog(this, buffer.toString(),
				"Validation errors", JOptionPane.ERROR_MESSAGE);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">
	private void initComponents() {
		tabbedPane = new javax.swing.JTabbedPane();
		downloadJobBasicPanel = new de.phleisch.app.itsucks.gui.job.panel.DownloadJobBasicPanel();
		downloadJobSimpleRulesPanel = new de.phleisch.app.itsucks.gui.job.panel.DownloadJobSimpleRulesPanel();
		downloadJobSpecialRulesPanel = new de.phleisch.app.itsucks.gui.job.panel.DownloadJobSpecialRulesPanel();
		downloadJobRegExpRulesPanel = new de.phleisch.app.itsucks.gui.job.panel.DownloadJobRegExpRulesPanel();
		downloadJobContentFilterPanel = new de.phleisch.app.itsucks.gui.job.panel.DownloadJobContentFilterPanel();

		tabbedPane.addTab("Basic Parameters", downloadJobBasicPanel);

		tabbedPane.addTab("Simple Rules", downloadJobSimpleRulesPanel);

		tabbedPane.addTab("Special Rules", downloadJobSpecialRulesPanel);

		tabbedPane.addTab("Advanced RegExp Rules", downloadJobRegExpRulesPanel);

		tabbedPane.addTab("Content Filter", downloadJobContentFilterPanel);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(tabbedPane,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 619,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(tabbedPane,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 598,
				Short.MAX_VALUE));
	}// </editor-fold>//GEN-END:initComponents

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private de.phleisch.app.itsucks.gui.job.panel.DownloadJobBasicPanel downloadJobBasicPanel;
	private de.phleisch.app.itsucks.gui.job.panel.DownloadJobContentFilterPanel downloadJobContentFilterPanel;
	private de.phleisch.app.itsucks.gui.job.panel.DownloadJobRegExpRulesPanel downloadJobRegExpRulesPanel;
	private de.phleisch.app.itsucks.gui.job.panel.DownloadJobSimpleRulesPanel downloadJobSimpleRulesPanel;
	private de.phleisch.app.itsucks.gui.job.panel.DownloadJobSpecialRulesPanel downloadJobSpecialRulesPanel;
	private javax.swing.JTabbedPane tabbedPane;
	// End of variables declaration//GEN-END:variables

}