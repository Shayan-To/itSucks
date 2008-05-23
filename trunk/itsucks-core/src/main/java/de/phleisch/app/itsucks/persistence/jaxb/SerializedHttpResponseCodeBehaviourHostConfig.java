//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.05.22 at 10:37:28 PM GMT 
//


package de.phleisch.app.itsucks.persistence.jaxb;

import java.util.ArrayList;
import java.util.List;

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
 *         &lt;element name="hostname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://itsucks.sourceforge.net/ItSucksJobSchema}serializedHttpRetrieverResponseCodeBehaviour" maxOccurs="unbounded"/>
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
    "hostname",
    "serializedHttpRetrieverResponseCodeBehaviour"
})
@XmlRootElement(name = "serializedHttpResponseCodeBehaviourHostConfig")
public class SerializedHttpResponseCodeBehaviourHostConfig {

    @XmlElement(required = true)
    protected String hostname;
    @XmlElement(required = true)
    protected List<SerializedHttpRetrieverResponseCodeBehaviour> serializedHttpRetrieverResponseCodeBehaviour;

    /**
     * Gets the value of the hostname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Sets the value of the hostname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostname(String value) {
        this.hostname = value;
    }

    /**
     * Gets the value of the serializedHttpRetrieverResponseCodeBehaviour property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serializedHttpRetrieverResponseCodeBehaviour property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSerializedHttpRetrieverResponseCodeBehaviour().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SerializedHttpRetrieverResponseCodeBehaviour }
     * 
     * 
     */
    public List<SerializedHttpRetrieverResponseCodeBehaviour> getSerializedHttpRetrieverResponseCodeBehaviour() {
        if (serializedHttpRetrieverResponseCodeBehaviour == null) {
            serializedHttpRetrieverResponseCodeBehaviour = new ArrayList<SerializedHttpRetrieverResponseCodeBehaviour>();
        }
        return this.serializedHttpRetrieverResponseCodeBehaviour;
    }

}
