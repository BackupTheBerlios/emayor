/**
 * _whereCondition_status.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.oracle.services.bpel.task;

public class _whereCondition_status implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected _whereCondition_status(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _active = "active";
    public static final java.lang.String _completed = "completed";
    public static final _whereCondition_status active = new _whereCondition_status(_active);
    public static final _whereCondition_status completed = new _whereCondition_status(_completed);
    public java.lang.String getValue() { return _value_;}
    public static _whereCondition_status fromValue(java.lang.String value)
          throws java.lang.IllegalStateException {
        _whereCondition_status enum = (_whereCondition_status)
            _table_.get(value);
        if (enum==null) throw new java.lang.IllegalStateException();
        return enum;
    }
    public static _whereCondition_status fromString(java.lang.String value)
          throws java.lang.IllegalStateException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
}
