/**
 *
 */
package org.eclipse.fastide.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ������
 */
public interface XmlModel {
    public Element getXml(Document doc);
}
