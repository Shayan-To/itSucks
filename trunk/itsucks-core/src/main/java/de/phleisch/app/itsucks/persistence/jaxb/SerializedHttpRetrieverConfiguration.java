//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.10.05 at 10:57:04 AM CEST 
//


package de.phleisch.app.itsucks.persistence.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maxConnectionsPerServer" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="proxyEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="proxyServer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="proxyPort" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="proxyAuthenticationEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="proxyUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="proxyPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="proxyRealm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "maxConnectionsPerServer",
    "proxyEnabled",
    "proxyServer",
    "proxyPort",
    "proxyAuthenticationEnabled",
    "proxyUser",
    "proxyPassword",
    "proxyRealm"
})
@XmlRootElement(name = "serializedHttpRetrieverConfiguration")
public class SerializedHttpRetrieverConfiguration {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer maxConnectionsPerServer;
    protected boolean proxyEnabled;
    @XmlElement(required = true, nillable = true)
    protected String proxyServer;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer proxyPort;
    protected boolean proxyAuthenticationEnabled;
    @XmlElement(required = true, nillable = true)
    protected String proxyUser;
    @XmlElement(required = true, nillable = true)
    protected String proxyPassword;
    @XmlElement(required = true, nillable = true)
    protected String proxyRealm;

    /**
     * Gets the value of the maxConnectionsPerServer property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxConnectionsPerServer() {
        return maxConnectionsPerServer;
    }

    /**
     * Sets the value of the maxConnectionsPerServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxConnectionsPerServer(Integer value) {
        this.maxConnectionsPerServer = value;
    }

    /**
     * Gets the value of the proxyEnabled property.
     * 
     */
    public boolean isProxyEnabled() {
        return proxyEnabled;
    }

    /**
     * Sets the value of the proxyEnabled property.
     * 
     */
    public void setProxyEnabled(boolean value) {
        this.proxyEnabled = value;
    }

    /**
     * Gets the value of the proxyServer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxyServer() {
        return proxyServer;
    }

    /**
     * Sets the value of the proxyServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxyServer(String value) {
        this.proxyServer = value;
    }

    /**
     * Gets the value of the proxyPort property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProxyPort() {
        return proxyPort;
    }

    /**
     * Sets the value of the proxyPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProxyPort(Integer value) {
        this.proxyPort = value;
    }

    /**
     * Gets the value of the proxyAuthenticationEnabled property.
     * 
     */
    public boolean isProxyAuthenticationEnabled() {
        return proxyAuthenticationEnabled;
    }

    /**
     * Sets the value of the proxyAuthenticationEnabled property.
     * 
     */
    public void setProxyAuthenticationEnabled(boolean value) {
        this.proxyAuthenticationEnabled = value;
    }

    /**
     * Gets the value of the proxyUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxyUser() {
        return proxyUser;
    }

    /**
     * Sets the value of the proxyUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxyUser(String value) {
        this.proxyUser = value;
    }

    /**
     * Gets the value of the proxyPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxyPassword() {
        return proxyPassword;
    }

    /**
     * Sets the value of the proxyPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxyPassword(String value) {
        this.proxyPassword = value;
    }

    /**
     * Gets the value of the proxyRealm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxyRealm() {
        return proxyRealm;
    }

    /**
     * Sets the value of the proxyRealm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxyRealm(String value) {
        this.proxyRealm = value;
    }

}
