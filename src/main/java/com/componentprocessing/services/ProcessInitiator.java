package com.componentprocessing.services;

import com.componentprocessing.model.ProcessRequest;
import com.componentprocessing.model.ProcessResponse;

public interface ProcessInitiator {

	ProcessResponse processReturnOrder(ProcessRequest processRequest);

	boolean validateProcessRequestObject(ProcessRequest processRequestObject);
}
