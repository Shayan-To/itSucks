//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.07.07 at 12:13:31 PM CEST 
//


package de.phleisch.app.itsucks.persistence.jaxb;

import javax.annotation.Generated;
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
 *         &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="matchAction" type="{http://itsucks.sourceforge.net/ItSucksJobSchema}serializedRegExpJobFilterAction"/>
 *         &lt;element name="noMatchAction" type="{http://itsucks.sourceforge.net/ItSucksJobSchema}serializedRegExpJobFilterAction"/>
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
    "pattern",
    "matchAction",
    "noMatchAction"
})
@XmlRootElement(name = "serializedRegExpJobFilterRule")
@Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
public class SerializedRegExpJobFilterRule
    extends SerializedJobFilter
{

    @XmlElement(required = true, nillable = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    protected String name;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    protected String pattern;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    protected SerializedRegExpJobFilterAction matchAction;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    protected SerializedRegExpJobFilterAction noMatchAction;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
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
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the pattern property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
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
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    public void setPattern(String value) {
        this.pattern = value;
    }

    /**
     * Gets the value of the matchAction property.
     * 
     * @return
     *     possible object is
     *     {@link SerializedRegExpJobFilterAction }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    public SerializedRegExpJobFilterAction getMatchAction() {
        return matchAction;
    }

    /**
     * Sets the value of the matchAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link SerializedRegExpJobFilterAction }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    public void setMatchAction(SerializedRegExpJobFilterAction value) {
        this.matchAction = value;
    }

    /**
     * Gets the value of the noMatchAction property.
     * 
     * @return
     *     possible object is
     *     {@link SerializedRegExpJobFilterAction }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    public SerializedRegExpJobFilterAction getNoMatchAction() {
        return noMatchAction;
    }

    /**
     * Sets the value of the noMatchAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link SerializedRegExpJobFilterAction }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-07T12:13:31+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    public void setNoMatchAction(SerializedRegExpJobFilterAction value) {
        this.noMatchAction = value;
    }

}