package org.acme.insurance.exception.wih;

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.internal.runtime.Cacheable;

public class ExceptionWorkItemHandler implements WorkItemHandler, Cacheable {
    
    private ReleaseId releaseId;
    private KieContainer kieContainer;
    private KieScanner kieScanner;
    
    public ExceptionWorkItemHandler(String releaseIdStr, ClassLoader classLoader) {
        releaseId = new ReleaseIdImpl(releaseIdStr);
        KieServices ks = KieServices.Factory.get();
        kieContainer = ks.newKieContainer(releaseId, classLoader);
        kieScanner = ks.newKieScanner(kieContainer);
        kieScanner.start(10000L);
        System.out.println("Started KieScanner for releaseId : " + releaseIdStr);
    }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager)  {
               
        System.out.println("Executing rules for releaseId " + releaseId); 
    
        String throwException = (String)workItem.getParameter("throwException");
        
        try
        {
	        if(throwException != null && throwException.equalsIgnoreCase("true"))
	        {
	        	throw new Exception("executeWorkItem threw an exception");
	        }
        }
        catch(Exception e)
        {
        	System.out.println("ExecuteWorkItem Exception caught");
        }
                    
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void close() {
        kieScanner.stop(); 
        
    }

}
