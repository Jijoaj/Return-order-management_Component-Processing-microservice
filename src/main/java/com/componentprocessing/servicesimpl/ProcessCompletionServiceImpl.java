package com.componentprocessing.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.componentprocessing.dao.ConfirmReturnRepository;
import com.componentprocessing.exceptions.ConfirmationFailedException;
import com.componentprocessing.model.ConfirmReturn;
import com.componentprocessing.model.ConfirmReturnRequest;
import com.componentprocessing.services.ProcessCompletionService;

@Service
public class ProcessCompletionServiceImpl implements ProcessCompletionService {

	@Autowired
	ConfirmReturnRepository confirmReturnRepository;

	@Override
	public String completeProcess(ConfirmReturnRequest confirmReturnRequest) {
		try {

			ConfirmReturn confirmReturn = new ConfirmReturn(confirmReturnRequest);

			ConfirmReturn save = confirmReturnRepository.saveReturnConfirmation(confirmReturn);
			if (save != null)
				return "success";
			else
				throw new ConfirmationFailedException();

		} catch (Exception e) {
			throw new ConfirmationFailedException();
		}
	}

}
