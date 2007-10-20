//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.10.20 at 11:28:38 PM CEST 
//


package de.phleisch.app.itsucks.persistence.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serializedRegExpJobFilterAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serializedRegExpJobFilterAction">
 *   &lt;complexContent>
 *     &lt;extension base="{http://itsucks.sourceforge.net/ItSucksJobSchema}serializedJobFilter">
 *       &lt;sequence>
 *         &lt;element name="priorityChange" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="accept" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="parameter" type="{http://itsucks.sourceforge.net/ItSucksJobSchema}serializedJobParameter" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serializedRegExpJobFilterAction", propOrder = {
    "priorityChange",
    "accept",
    "parameter"
})
public class SerializedRegExpJobFilterAction
    extends SerializedJobFilter
{

    protected int priorityChange;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean accept;
    protected List<SerializedJobParameter> parameter;

    /**
     * Gets the value of the priorityChange property.
     * 
     */
    public int getPriorityChange() {
        return priorityChange;
    }

    /**
     * Sets the value of the priorityChange property.
     * 
     */
    public void setPriorityChange(int value) {
        this.priorityChange = value;
    }

    /**
     * Gets the value of the accept property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAccept() {
        return accept;
    }

    /**
     * Sets the value of the accept property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAccept(Boolean value) {
        this.accept = value;
    }

    /**
     * Gets the value of the parameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SerializedJobParameter }
     * 
     * 
     */
    public List<SerializedJobParameter> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<SerializedJobParameter>();
        }
        return this.parameter;
    }

}
