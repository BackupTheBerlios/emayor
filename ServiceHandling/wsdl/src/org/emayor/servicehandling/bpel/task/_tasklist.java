/**
 * _tasklist.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.emayor.servicehandling.bpel.task;

public class _tasklist  implements java.io.Serializable {
    private org.emayor.servicehandling.bpel.task._task[] task;

    public _tasklist() {
    }

    public org.emayor.servicehandling.bpel.task._task[] getTask() {
        return task;
    }

    public void setTask(org.emayor.servicehandling.bpel.task._task[] task) {
        this.task = task;
    }

    public org.emayor.servicehandling.bpel.task._task getTask(int i) {
        return task[i];
    }

    public void setTask(int i, org.emayor.servicehandling.bpel.task._task value) {
        this.task[i] = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _tasklist)) return false;
        _tasklist other = (_tasklist) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.task==null && other.getTask()==null) || 
             (this.task!=null &&
              java.util.Arrays.equals(this.task, other.getTask())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTask() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTask());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTask(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_tasklist.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://emayor.org/servicehandling/bpel/task", ">tasklist"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("task");
        elemField.setXmlName(new javax.xml.namespace.QName("http://emayor.org/servicehandling/bpel/task", "task"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://emayor.org/servicehandling/bpel/task", ">task"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
