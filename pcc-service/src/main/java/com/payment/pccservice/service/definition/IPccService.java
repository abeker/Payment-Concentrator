package com.payment.pccservice.service.definition;

import com.payment.pccservice.dto.request.RequestPcc;
import com.payment.pccservice.dto.response.ResponsePcc;

public interface IPccService {

    ResponsePcc forwardRequest(RequestPcc requestPcc);

}
