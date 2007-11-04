//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.11.04 at 07:14:48 PM GMT 
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
 *     &lt;extension base="{http://itsucks.sourceforge.net/ItSucksJobSchema}serializedJob">
 *       &lt;sequence>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parentJobId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="depth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="maxRetryCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="minTimeBetweenRetry" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="saveToDisk" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="savePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "url",
    "parentJobId",
    "depth",
    "maxRetryCount",
    "minTimeBetweenRetry",
    "saveToDisk",
    "savePath"
})
@XmlRootElement(name = "serializedDownloadJob")
public class SerializedDownloadJob
    extends SerializedJob
{

    @XmlElement(required = true)
    protected String url;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long parentJobId;
    protected int depth;
    protected int maxRetryCount;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long minTimeBetweenRetry;
    protected boolean saveToDisk;
    @XmlElement(required = true, nillable = true)
    protected String savePath;

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the parentJobId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getParentJobId() {
        return parentJobId;
    }

    /**
     * Sets the value of the parentJobId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setParentJobId(Long value) {
        this.parentJobId = value;
    }

    /**
     * Gets the value of the depth property.
     * 
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Sets the value of the depth property.
     * 
     */
    public void setDepth(int value) {
        this.depth = value;
    }

    /**
     * Gets the value of the maxRetryCount property.
     * 
     */
    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    /**
     * Sets the value of the maxRetryCount property.
     * 
     */
    public void setMaxRetryCount(int value) {
        this.maxRetryCount = value;
    }

    /**
     * Gets the value of the minTimeBetweenRetry property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMinTimeBetweenRetry() {
        return minTimeBetweenRetry;
    }

    /**
     * Sets the value of the minTimeBetweenRetry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMinTimeBetweenRetry(Long value) {
        this.minTimeBetweenRetry = value;
    }

    /**
     * Gets the value of the saveToDisk property.
     * 
     */
    public boolean isSaveToDisk() {
        return saveToDisk;
    }

    /**
     * Sets the value of the saveToDisk property.
     * 
     */
    public void setSaveToDisk(boolean value) {
        this.saveToDisk = value;
    }

    /**
     * Gets the value of the savePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSavePath() {
        return savePath;
    }

    /**
     * Sets the value of the savePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSavePath(String value) {
        this.savePath = value;
    }

}
