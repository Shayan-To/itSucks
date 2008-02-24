//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.02.24 at 08:54:22 PM GMT 
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
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="matchAction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="noMatchAction" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "name",
    "description",
    "pattern",
    "matchAction",
    "noMatchAction"
})
@XmlRootElement(name = "serializedContentFilterConfig")
public class SerializedContentFilterConfig
    extends SerializedJobFilter
{

    @XmlElement(required = true, nillable = true)
    protected String name;
    @XmlElement(required = true, nillable = true)
    protected String description;
    @XmlElement(required = true)
    protected String pattern;
    @XmlElement(required = true)
    protected String matchAction;
    @XmlElement(required = true)
    protected String noMatchAction;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the pattern property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the value of the pattern property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPattern(String value) {
        this.pattern = value;
    }

    /**
     * Gets the value of the matchAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatchAction() {
        return matchAction;
    }

    /**
     * Sets the value of the matchAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatchAction(String value) {
        this.matchAction = value;
    }

    /**
     * Gets the value of the noMatchAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoMatchAction() {
        return noMatchAction;
    }

    /**
     * Sets the value of the noMatchAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoMatchAction(String value) {
        this.noMatchAction = value;
    }

}
