/*
 *
 * CODENVY CONFIDENTIAL
 * ________________
 *
 * [2012] - [2014] Codenvy, S.A.
 * All Rights Reserved.
 * NOTICE: All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.analytics.pig.scripts;

import javax.xml.bind.annotation.*;

/**
 * @author Alexander Reshetnyak
 */
public class Parameter {

    private String name;
    private boolean empty;

    @XmlValue
    public void setName(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    @XmlAttribute(name = "allow-empty-value")
    public void setAllowEmptyValue(boolean empty) {
        this.empty = empty;
    }

    public boolean isAllowEmptyValue() { return empty; }
}
