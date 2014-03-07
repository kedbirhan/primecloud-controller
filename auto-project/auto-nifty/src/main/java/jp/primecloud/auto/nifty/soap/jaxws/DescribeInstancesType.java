
package jp.primecloud.auto.nifty.soap.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * <p>Java class for DescribeInstancesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DescribeInstancesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="instancesSet" type="{https://cp.cloud.nifty.com/api/1.3/}DescribeInstancesInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescribeInstancesType", propOrder = {
    "instancesSet"
})
public class DescribeInstancesType {

    @XmlElement(required = true)
    protected DescribeInstancesInfoType instancesSet;

    /**
     * Gets the value of the instancesSet property.
     * 
     * @return
     *     possible object is
     *     {@link DescribeInstancesInfoType }
     *     
     */
    public DescribeInstancesInfoType getInstancesSet() {
        return instancesSet;
    }

    /**
     * Sets the value of the instancesSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link DescribeInstancesInfoType }
     *     
     */
    public void setInstancesSet(DescribeInstancesInfoType value) {
        this.instancesSet = value;
    }

    public DescribeInstancesType withInstancesSet(DescribeInstancesInfoType value) {
        setInstancesSet(value);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
