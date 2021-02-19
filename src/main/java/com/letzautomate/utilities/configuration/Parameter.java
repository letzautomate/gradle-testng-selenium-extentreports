package com.letzautomate.utilities.configuration;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement
@XmlType(propOrder = {"key" , "value"})
public class Parameter {
    private String key;
    private String value;

    @XmlAttribute(name = "key")
    public String getKey(){return key;}
    public void setKey() {this.key = key;}

    @XmlAttribute(name = "value")
    public String getValue(){return key;}
    public void setValue() {this.key = key;}
}
