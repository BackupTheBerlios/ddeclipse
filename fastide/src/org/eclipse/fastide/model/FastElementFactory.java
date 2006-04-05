/**
 *
 */
package org.eclipse.fastide.model;

import org.eclipse.gef.requests.CreationFactory;

/**
 * @author …Ú»›÷€
 */
public class FastElementFactory implements CreationFactory {
    private String template;

    public FastElementFactory(String template) {
        this.template = template;
    }

    /**
     * @see org.eclipse.gef.requests.CreationFactory#getNewObject()
     */
    public Object getNewObject() {
        if("function node template".equals(template))
            return new FunctionNode();
        if("predicate node template".equals(template))
            return new PredicateNode();
        if("joinpoint node template".equals(template))
            return new JoinpointNode();
        return null;
    }

    public Object getObjectType() {
        return template;
    }
}
