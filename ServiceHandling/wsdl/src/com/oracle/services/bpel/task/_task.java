/**
 * _task.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.oracle.services.bpel.task;

public class _task  implements java.io.Serializable {
    private java.lang.String taskId;
    private java.lang.String title;
    private java.util.Calendar creationDate;
    private java.lang.String creator;
    private java.util.Calendar modifyDate;
    private java.lang.String modifier;
    private java.lang.String assignee;
    private com.oracle.services.bpel.task._task_status status;
    private java.lang.Boolean expired;
    private java.util.Calendar expirationDate;
    private org.apache.axis.types.Duration duration;
    private java.lang.Integer priority;
    private java.lang.String template;
    private java.lang.String customKey;
    private java.lang.String conclusion;
    private java.lang.Object attachment;

    public _task() {
    }

    public java.lang.String getTaskId() {
        return taskId;
    }

    public void setTaskId(java.lang.String taskId) {
        this.taskId = taskId;
    }

    public java.lang.String getTitle() {
        return title;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public java.util.Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.util.Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public java.lang.String getCreator() {
        return creator;
    }

    public void setCreator(java.lang.String creator) {
        this.creator = creator;
    }

    public java.util.Calendar getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(java.util.Calendar modifyDate) {
        this.modifyDate = modifyDate;
    }

    public java.lang.String getModifier() {
        return modifier;
    }

    public void setModifier(java.lang.String modifier) {
        this.modifier = modifier;
    }

    public java.lang.String getAssignee() {
        return assignee;
    }

    public void setAssignee(java.lang.String assignee) {
        this.assignee = assignee;
    }

    public com.oracle.services.bpel.task._task_status getStatus() {
        return status;
    }

    public void setStatus(com.oracle.services.bpel.task._task_status status) {
        this.status = status;
    }

    public java.lang.Boolean getExpired() {
        return expired;
    }

    public void setExpired(java.lang.Boolean expired) {
        this.expired = expired;
    }

    public java.util.Calendar getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(java.util.Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public org.apache.axis.types.Duration getDuration() {
        return duration;
    }

    public void setDuration(org.apache.axis.types.Duration duration) {
        this.duration = duration;
    }

    public java.lang.Integer getPriority() {
        return priority;
    }

    public void setPriority(java.lang.Integer priority) {
        this.priority = priority;
    }

    public java.lang.String getTemplate() {
        return template;
    }

    public void setTemplate(java.lang.String template) {
        this.template = template;
    }

    public java.lang.String getCustomKey() {
        return customKey;
    }

    public void setCustomKey(java.lang.String customKey) {
        this.customKey = customKey;
    }

    public java.lang.String getConclusion() {
        return conclusion;
    }

    public void setConclusion(java.lang.String conclusion) {
        this.conclusion = conclusion;
    }

    public java.lang.Object getAttachment() {
        return attachment;
    }

    public void setAttachment(java.lang.Object attachment) {
        this.attachment = attachment;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _task)) return false;
        _task other = (_task) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.taskId==null && other.getTaskId()==null) || 
             (this.taskId!=null &&
              this.taskId.equals(other.getTaskId()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.creationDate==null && other.getCreationDate()==null) || 
             (this.creationDate!=null &&
              this.creationDate.equals(other.getCreationDate()))) &&
            ((this.creator==null && other.getCreator()==null) || 
             (this.creator!=null &&
              this.creator.equals(other.getCreator()))) &&
            ((this.modifyDate==null && other.getModifyDate()==null) || 
             (this.modifyDate!=null &&
              this.modifyDate.equals(other.getModifyDate()))) &&
            ((this.modifier==null && other.getModifier()==null) || 
             (this.modifier!=null &&
              this.modifier.equals(other.getModifier()))) &&
            ((this.assignee==null && other.getAssignee()==null) || 
             (this.assignee!=null &&
              this.assignee.equals(other.getAssignee()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.expired==null && other.getExpired()==null) || 
             (this.expired!=null &&
              this.expired.equals(other.getExpired()))) &&
            ((this.expirationDate==null && other.getExpirationDate()==null) || 
             (this.expirationDate!=null &&
              this.expirationDate.equals(other.getExpirationDate()))) &&
            ((this.duration==null && other.getDuration()==null) || 
             (this.duration!=null &&
              this.duration.equals(other.getDuration()))) &&
            ((this.priority==null && other.getPriority()==null) || 
             (this.priority!=null &&
              this.priority.equals(other.getPriority()))) &&
            ((this.template==null && other.getTemplate()==null) || 
             (this.template!=null &&
              this.template.equals(other.getTemplate()))) &&
            ((this.customKey==null && other.getCustomKey()==null) || 
             (this.customKey!=null &&
              this.customKey.equals(other.getCustomKey()))) &&
            ((this.conclusion==null && other.getConclusion()==null) || 
             (this.conclusion!=null &&
              this.conclusion.equals(other.getConclusion()))) &&
            ((this.attachment==null && other.getAttachment()==null) || 
             (this.attachment!=null &&
              this.attachment.equals(other.getAttachment())));
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
        if (getTaskId() != null) {
            _hashCode += getTaskId().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getCreationDate() != null) {
            _hashCode += getCreationDate().hashCode();
        }
        if (getCreator() != null) {
            _hashCode += getCreator().hashCode();
        }
        if (getModifyDate() != null) {
            _hashCode += getModifyDate().hashCode();
        }
        if (getModifier() != null) {
            _hashCode += getModifier().hashCode();
        }
        if (getAssignee() != null) {
            _hashCode += getAssignee().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getExpired() != null) {
            _hashCode += getExpired().hashCode();
        }
        if (getExpirationDate() != null) {
            _hashCode += getExpirationDate().hashCode();
        }
        if (getDuration() != null) {
            _hashCode += getDuration().hashCode();
        }
        if (getPriority() != null) {
            _hashCode += getPriority().hashCode();
        }
        if (getTemplate() != null) {
            _hashCode += getTemplate().hashCode();
        }
        if (getCustomKey() != null) {
            _hashCode += getCustomKey().hashCode();
        }
        if (getConclusion() != null) {
            _hashCode += getConclusion().hashCode();
        }
        if (getAttachment() != null) {
            _hashCode += getAttachment().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_task.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", ">task"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "taskId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "creationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "creator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifyDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "modifyDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "modifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assignee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "assignee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", ">task>status"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expired");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "expired"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expirationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "expirationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("duration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "duration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "duration"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("template");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "template"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "customKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conclusion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "conclusion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "attachment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
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
