//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.07.03 at 10:57:57 PM CEST 
//


package de.phleisch.app.itsucks.persistence.jaxb;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serializedDownloadJob complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serializedDownloadJob">
 *   &lt;complexContent>
 *     &lt;extension base="{http://itsucks.sourceforge.net/ItSucksJobSchema}serializedJob">
 *       &lt;sequence>
 *         &lt;element name="downloadJobExtension" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serializedDownloadJob", propOrder = {
    "downloadJobExtension"
})
@Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-03T10:57:57+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
public class SerializedDownloadJob
    extends SerializedJob
{

    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-03T10:57:57+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    protected BigInteger downloadJobExtension;

    /**
     * Gets the value of the downloadJobExtension property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-03T10:57:57+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    public BigInteger getDownloadJobExtension() {
        return downloadJobExtension;
    }

    /**
     * Sets the value of the downloadJobExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2007-07-03T10:57:57+02:00", comments = "JAXB RI v2.0.5-b02-fcs")
    public void setDownloadJobExtension(BigInteger value) {
        this.downloadJobExtension = value;
    }

}