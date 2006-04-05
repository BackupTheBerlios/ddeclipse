/**
 *
 */
package org.eclipse.fastide;

import java.util.MissingResourceException;

import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.Platform;

/**
 * @author …Ú»›÷€
 */
public interface FastMessages {
    static class Helper {
        static String getMessage(String key) {
            IPluginDescriptor desc = Platform.getPluginRegistry()
                    .getPluginDescriptor("org.eclipse.fastide");
            try {
                return desc.getResourceString(key);
            } catch (MissingResourceException e) {
                return key;
            }
        }
    }

    String PropertyDescriptor_FastSubPart_Size             = Helper
                                                                   .getMessage("%PropertyDescriptor.FastSubPart.Size");

    String PropertyDescriptor_FastSubPart_Location         = Helper
                                                                   .getMessage("%PropertyDescriptor.FastSubPart.Location");

    String PropertyDescriptor_SimpleNode_Name              = Helper
                                                                   .getMessage("%PropertyDescriptor.SimpleNode.Name");

    String PropertyDescriptor_FastDiagram_ConnectionRouter = Helper
                                                                   .getMessage("%PropertyDescriptor.FastDiagram.ConnectionRouter");

    String PropertyDescriptor_FastDiagram_Manhattan        = Helper
                                                                   .getMessage("%PropertyDescriptor.FastDiagram.Manhattan");

    String PropertyDescriptor_FastDiagram_Manual           = Helper
                                                                   .getMessage("%PropertyDescriptor.FastDiagram.Manual");

    String PropertyDescriptor_FastDiagram_ShortestPath     = Helper
                                                                   .getMessage("%PropertyDescriptor.FastDiagram.ShortestPath");

    String FastDiagram_LabelText                           = Helper
                                                                   .getMessage("%FastDiagram.LabelText");
}
