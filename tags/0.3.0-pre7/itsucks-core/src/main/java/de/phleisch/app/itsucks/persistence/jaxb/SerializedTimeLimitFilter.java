//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.05.22 at 10:37:28 PM GMT 
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
 *     &lt;extension base="{http://itsucks.sourceforge.net/ItSucksJobSchema}serializedJobFilter">
 *       &lt;sequence>
 *         &lt;element name="timeLimit" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "timeLimit"
})
@XmlRootElement(name = "serializedTimeLimitFilter")
public class SerializedTimeLimitFilter
    extends SerializedJobFilter
{

    @XmlElement(required = true)
    protected String timeLimit;

    /**
     * Gets the value of the timeLimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeLimit() {
        return timeLimit;
    }

    /**
     * Sets the value of the timeLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeLimit(String value) {
        this.timeLimit = value;
    }

}
