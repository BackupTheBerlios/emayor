/**
 * XincoSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

/**
 *Copyright 2004 blueCubs.com
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 *
 *************************************************************
 * This project supports the blueCubs vision of giving back
 * to the community in exchange for free software!
 * More information on: http://www.bluecubs.org
 *************************************************************
 *
 * Name:            XincoSoapBindingSkeleton
 *
 * Description:     -
 *
 * Original Author: Alexander Manes
 * Date:            2004
 *
 * Modifications:
 * 
 * Who?             When?             What?
 * -                -                 -
 *
 *************************************************************
 */

package com.bluecubs.xinco.service;

public class XincoSoapBindingSkeleton implements com.bluecubs.xinco.service.Xinco, org.apache.axis.wsdl.Skeleton {
    private com.bluecubs.xinco.service.Xinco impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getXincoServerVersion", _params, new javax.xml.namespace.QName("", "getXincoServerVersionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoVersion"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "getXincoServerVersion"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getXincoServerVersion") == null) {
            _myOperations.put("getXincoServerVersion", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getXincoServerVersion")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCurrentXincoCoreUser", _params, new javax.xml.namespace.QName("", "getCurrentXincoCoreUserReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "getCurrentXincoCoreUser"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCurrentXincoCoreUser") == null) {
            _myOperations.put("getCurrentXincoCoreUser", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCurrentXincoCoreUser")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getAllXincoCoreGroups", _params, new javax.xml.namespace.QName("", "getAllXincoCoreGroupsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Vector"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "getAllXincoCoreGroups"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getAllXincoCoreGroups") == null) {
            _myOperations.put("getAllXincoCoreGroups", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getAllXincoCoreGroups")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getAllXincoCoreLanguages", _params, new javax.xml.namespace.QName("", "getAllXincoCoreLanguagesReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Vector"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "getAllXincoCoreLanguages"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getAllXincoCoreLanguages") == null) {
            _myOperations.put("getAllXincoCoreLanguages", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getAllXincoCoreLanguages")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getAllXincoCoreDataTypes", _params, new javax.xml.namespace.QName("", "getAllXincoCoreDataTypesReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Vector"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "getAllXincoCoreDataTypes"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getAllXincoCoreDataTypes") == null) {
            _myOperations.put("getAllXincoCoreDataTypes", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getAllXincoCoreDataTypes")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreNode"), com.bluecubs.xinco.core.XincoCoreNode.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getXincoCoreNode", _params, new javax.xml.namespace.QName("", "getXincoCoreNodeReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreNode"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "getXincoCoreNode"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getXincoCoreNode") == null) {
            _myOperations.put("getXincoCoreNode", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getXincoCoreNode")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"), com.bluecubs.xinco.core.XincoCoreData.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getXincoCoreData", _params, new javax.xml.namespace.QName("", "getXincoCoreDataReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "getXincoCoreData"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getXincoCoreData") == null) {
            _myOperations.put("getXincoCoreData", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getXincoCoreData")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"), com.bluecubs.xinco.core.XincoCoreData.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("doXincoCoreDataCheckout", _params, new javax.xml.namespace.QName("", "doXincoCoreDataCheckoutReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "doXincoCoreDataCheckout"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("doXincoCoreDataCheckout") == null) {
            _myOperations.put("doXincoCoreDataCheckout", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("doXincoCoreDataCheckout")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"), com.bluecubs.xinco.core.XincoCoreData.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("undoXincoCoreDataCheckout", _params, new javax.xml.namespace.QName("", "undoXincoCoreDataCheckoutReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "undoXincoCoreDataCheckout"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("undoXincoCoreDataCheckout") == null) {
            _myOperations.put("undoXincoCoreDataCheckout", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("undoXincoCoreDataCheckout")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"), com.bluecubs.xinco.core.XincoCoreData.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("doXincoCoreDataCheckin", _params, new javax.xml.namespace.QName("", "doXincoCoreDataCheckinReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "doXincoCoreDataCheckin"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("doXincoCoreDataCheckin") == null) {
            _myOperations.put("doXincoCoreDataCheckin", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("doXincoCoreDataCheckin")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"), com.bluecubs.xinco.core.XincoCoreData.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("downloadXincoCoreData", _params, new javax.xml.namespace.QName("", "downloadXincoCoreDataReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "downloadXincoCoreData"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("downloadXincoCoreData") == null) {
            _myOperations.put("downloadXincoCoreData", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("downloadXincoCoreData")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"), com.bluecubs.xinco.core.XincoCoreData.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"), byte[].class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("uploadXincoCoreData", _params, new javax.xml.namespace.QName("", "uploadXincoCoreDataReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "uploadXincoCoreData"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("uploadXincoCoreData") == null) {
            _myOperations.put("uploadXincoCoreData", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("uploadXincoCoreData")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreLanguage"), com.bluecubs.xinco.core.XincoCoreLanguage.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("findXincoCoreNodes", _params, new javax.xml.namespace.QName("", "findXincoCoreNodesReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Vector"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "findXincoCoreNodes"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("findXincoCoreNodes") == null) {
            _myOperations.put("findXincoCoreNodes", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("findXincoCoreNodes")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreLanguage"), com.bluecubs.xinco.core.XincoCoreLanguage.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in2"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("findXincoCoreData", _params, new javax.xml.namespace.QName("", "findXincoCoreDataReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Vector"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "findXincoCoreData"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("findXincoCoreData") == null) {
            _myOperations.put("findXincoCoreData", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("findXincoCoreData")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreNode"), com.bluecubs.xinco.core.XincoCoreNode.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setXincoCoreNode", _params, new javax.xml.namespace.QName("", "setXincoCoreNodeReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreNode"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "setXincoCoreNode"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setXincoCoreNode") == null) {
            _myOperations.put("setXincoCoreNode", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setXincoCoreNode")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"), com.bluecubs.xinco.core.XincoCoreData.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setXincoCoreData", _params, new javax.xml.namespace.QName("", "setXincoCoreDataReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreData"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "setXincoCoreData"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setXincoCoreData") == null) {
            _myOperations.put("setXincoCoreData", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setXincoCoreData")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreACE"), com.bluecubs.xinco.core.XincoCoreACE.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setXincoCoreACE", _params, new javax.xml.namespace.QName("", "setXincoCoreACEReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreACE"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "setXincoCoreACE"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setXincoCoreACE") == null) {
            _myOperations.put("setXincoCoreACE", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setXincoCoreACE")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreACE"), com.bluecubs.xinco.core.XincoCoreACE.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("removeXincoCoreACE", _params, new javax.xml.namespace.QName("", "removeXincoCoreACEReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "removeXincoCoreACE"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("removeXincoCoreACE") == null) {
            _myOperations.put("removeXincoCoreACE", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("removeXincoCoreACE")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreLog"), com.bluecubs.xinco.core.XincoCoreLog.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setXincoCoreLog", _params, new javax.xml.namespace.QName("", "setXincoCoreLogReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreLog"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "setXincoCoreLog"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setXincoCoreLog") == null) {
            _myOperations.put("setXincoCoreLog", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setXincoCoreLog")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setXincoCoreUser", _params, new javax.xml.namespace.QName("", "setXincoCoreUserReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "setXincoCoreUser"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setXincoCoreUser") == null) {
            _myOperations.put("setXincoCoreUser", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setXincoCoreUser")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreGroup"), com.bluecubs.xinco.core.XincoCoreGroup.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setXincoCoreGroup", _params, new javax.xml.namespace.QName("", "setXincoCoreGroupReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreGroup"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "setXincoCoreGroup"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setXincoCoreGroup") == null) {
            _myOperations.put("setXincoCoreGroup", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setXincoCoreGroup")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreLanguage"), com.bluecubs.xinco.core.XincoCoreLanguage.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setXincoCoreLanguage", _params, new javax.xml.namespace.QName("", "setXincoCoreLanguageReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreLanguage"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "setXincoCoreLanguage"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setXincoCoreLanguage") == null) {
            _myOperations.put("setXincoCoreLanguage", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setXincoCoreLanguage")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreDataTypeAttribute"), com.bluecubs.xinco.core.XincoCoreDataTypeAttribute.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getXincoCoreDataTypeAttribute", _params, new javax.xml.namespace.QName("", "getXincoCoreDataTypeAttributeReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreDataTypeAttribute"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "getXincoCoreDataTypeAttribute"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getXincoCoreDataTypeAttribute") == null) {
            _myOperations.put("getXincoCoreDataTypeAttribute", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getXincoCoreDataTypeAttribute")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://add.xinco.bluecubs.com", "XincoAddAttribute"), com.bluecubs.xinco.add.XincoAddAttribute.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://core.xinco.bluecubs.com", "XincoCoreUser"), com.bluecubs.xinco.core.XincoCoreUser.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getXincoAddAttribute", _params, new javax.xml.namespace.QName("", "getXincoAddAttributeReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://add.xinco.bluecubs.com", "XincoAddAttribute"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Xinco", "getXincoAddAttribute"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getXincoAddAttribute") == null) {
            _myOperations.put("getXincoAddAttribute", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getXincoAddAttribute")).add(_oper);
    }

    public XincoSoapBindingSkeleton() {
        this.impl = new com.bluecubs.xinco.service.XincoSoapBindingImpl();
    }

    public XincoSoapBindingSkeleton(com.bluecubs.xinco.service.Xinco impl) {
        this.impl = impl;
    }
    public com.bluecubs.xinco.core.XincoVersion getXincoServerVersion() throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoVersion ret = impl.getXincoServerVersion();
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreUser getCurrentXincoCoreUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreUser ret = impl.getCurrentXincoCoreUser(in0, in1);
        return ret;
    }

    public java.util.Vector getAllXincoCoreGroups(com.bluecubs.xinco.core.XincoCoreUser in0) throws java.rmi.RemoteException
    {
        java.util.Vector ret = impl.getAllXincoCoreGroups(in0);
        return ret;
    }

    public java.util.Vector getAllXincoCoreLanguages(com.bluecubs.xinco.core.XincoCoreUser in0) throws java.rmi.RemoteException
    {
        java.util.Vector ret = impl.getAllXincoCoreLanguages(in0);
        return ret;
    }

    public java.util.Vector getAllXincoCoreDataTypes(com.bluecubs.xinco.core.XincoCoreUser in0) throws java.rmi.RemoteException
    {
        java.util.Vector ret = impl.getAllXincoCoreDataTypes(in0);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreNode getXincoCoreNode(com.bluecubs.xinco.core.XincoCoreNode in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreNode ret = impl.getXincoCoreNode(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreData getXincoCoreData(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreData ret = impl.getXincoCoreData(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreData doXincoCoreDataCheckout(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreData ret = impl.doXincoCoreDataCheckout(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreData undoXincoCoreDataCheckout(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreData ret = impl.undoXincoCoreDataCheckout(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreData doXincoCoreDataCheckin(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreData ret = impl.doXincoCoreDataCheckin(in0, in1);
        return ret;
    }

    public byte[] downloadXincoCoreData(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        byte[] ret = impl.downloadXincoCoreData(in0, in1);
        return ret;
    }

    public int uploadXincoCoreData(com.bluecubs.xinco.core.XincoCoreData in0, byte[] in1, com.bluecubs.xinco.core.XincoCoreUser in2) throws java.rmi.RemoteException
    {
        int ret = impl.uploadXincoCoreData(in0, in1, in2);
        return ret;
    }

    public java.util.Vector findXincoCoreNodes(java.lang.String in0, com.bluecubs.xinco.core.XincoCoreLanguage in1, com.bluecubs.xinco.core.XincoCoreUser in2) throws java.rmi.RemoteException
    {
        java.util.Vector ret = impl.findXincoCoreNodes(in0, in1, in2);
        return ret;
    }

    public java.util.Vector findXincoCoreData(java.lang.String in0, com.bluecubs.xinco.core.XincoCoreLanguage in1, com.bluecubs.xinco.core.XincoCoreUser in2) throws java.rmi.RemoteException
    {
        java.util.Vector ret = impl.findXincoCoreData(in0, in1, in2);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreNode setXincoCoreNode(com.bluecubs.xinco.core.XincoCoreNode in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreNode ret = impl.setXincoCoreNode(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreData setXincoCoreData(com.bluecubs.xinco.core.XincoCoreData in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreData ret = impl.setXincoCoreData(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreACE setXincoCoreACE(com.bluecubs.xinco.core.XincoCoreACE in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreACE ret = impl.setXincoCoreACE(in0, in1);
        return ret;
    }

    public boolean removeXincoCoreACE(com.bluecubs.xinco.core.XincoCoreACE in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        boolean ret = impl.removeXincoCoreACE(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreLog setXincoCoreLog(com.bluecubs.xinco.core.XincoCoreLog in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreLog ret = impl.setXincoCoreLog(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreUser setXincoCoreUser(com.bluecubs.xinco.core.XincoCoreUser in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreUser ret = impl.setXincoCoreUser(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreGroup setXincoCoreGroup(com.bluecubs.xinco.core.XincoCoreGroup in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreGroup ret = impl.setXincoCoreGroup(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreLanguage setXincoCoreLanguage(com.bluecubs.xinco.core.XincoCoreLanguage in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreLanguage ret = impl.setXincoCoreLanguage(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.core.XincoCoreDataTypeAttribute getXincoCoreDataTypeAttribute(com.bluecubs.xinco.core.XincoCoreDataTypeAttribute in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.core.XincoCoreDataTypeAttribute ret = impl.getXincoCoreDataTypeAttribute(in0, in1);
        return ret;
    }

    public com.bluecubs.xinco.add.XincoAddAttribute getXincoAddAttribute(com.bluecubs.xinco.add.XincoAddAttribute in0, com.bluecubs.xinco.core.XincoCoreUser in1) throws java.rmi.RemoteException
    {
        com.bluecubs.xinco.add.XincoAddAttribute ret = impl.getXincoAddAttribute(in0, in1);
        return ret;
    }

}
