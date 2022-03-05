package com.componentprocessing.services;

import com.componentprocessing.model.ConfirmReturnRequest;

public interface ProcessCompletionService {
	String completeProcess(ConfirmReturnRequest confirmReturnRequest);
}
