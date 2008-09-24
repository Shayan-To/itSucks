//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.2-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.09.24 at 07:08:28 PM CEST 
//


package de.phleisch.app.itsucks.persistence.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *     &lt;extension base="{http://itsucks.sourceforge.net/ItSucksJobSchema}serializedJobFilter">
 *       &lt;sequence>
 *         &lt;element name="letUnfilteredJobsPass" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element ref="{http://itsucks.sourceforge.net/ItSucksJobSchema}serializedRegExpJobFilterRule" maxOccurs="unbounded" minOccurs="0"/>
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
    "letUnfilteredJobsPass",
    "serializedRegExpJobFilterRule"
})
@XmlRootElement(name = "serializedRegExpJobFilter")
public class SerializedRegExpJobFilter
    extends SerializedJobFilter
{

    protected boolean letUnfilteredJobsPass;
    protected List<SerializedRegExpJobFilterRule> serializedRegExpJobFilterRule;

    /**
     * Gets the value of the letUnfilteredJobsPass property.
     * 
     */
    public boolean isLetUnfilteredJobsPass() {
        return letUnfilteredJobsPass;
    }

    /**
     * Sets the value of the letUnfilteredJobsPass property.
     * 
     */
    public void setLetUnfilteredJobsPass(boolean value) {
        this.letUnfilteredJobsPass = value;
    }

    /**
     * Gets the value of the serializedRegExpJobFilterRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serializedRegExpJobFilterRule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSerializedRegExpJobFilterRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SerializedRegExpJobFilterRule }
     * 
     * 
     */
    public List<SerializedRegExpJobFilterRule> getSerializedRegExpJobFilterRule() {
        if (serializedRegExpJobFilterRule == null) {
            serializedRegExpJobFilterRule = new ArrayList<SerializedRegExpJobFilterRule>();
        }
        return this.serializedRegExpJobFilterRule;
    }

}
