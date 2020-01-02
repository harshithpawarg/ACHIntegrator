package com.mca.constants;

import com.mca.service.HTTPService;

import java.util.HashMap;
import java.util.Map;

public class ActumACHConstants {

    public static final String PARENT_ID = "parent_id";
    public static final String SUB_ID = "sub_id";
    public static final String PAYMENT_TYPE = "pmt_type";
    public static final String chkAccountNumber = "pmt_type";

    public void debitIns() throws Exception {

        String parentId = "ACTUM";
        String subId = "OOTBTST";
        String chkAccountNumber = "1846220511";
        String chkABANumber = "999999999";
        String customerName = "BobYakuza";
        String customerEmail = "byakuza@gmail.com";
        String customerPhone = "893-555-0893";
        String billingCycle = "-1";
        String merchantOrderNumber = "MCA_N-1234-INF_000000";
        String collectionAmount = "1.00";
        String hostname = "http://localhost:8080";

        Map<String,String> urlParameters = new HashMap<>();
        urlParameters.put("parent_id",parentId);
        urlParameters.put("sub_id",subId);
        urlParameters.put("pmt_type",PAYMENT_TYPE);
        urlParameters.put("chk_acct",chkAccountNumber);
        urlParameters.put("chk_aba",chkABANumber);
        urlParameters.put("custname",customerName);
        urlParameters.put("custemail",customerEmail);
        urlParameters.put("custphone",customerPhone);
        urlParameters.put("initial_amount",collectionAmount);
        urlParameters.put("merordernumber",merchantOrderNumber);
        urlParameters.put("ip_forward",hostname);
        urlParameters.put("billing_cycle",billingCycle);

        HTTPService httpService = new HTTPService();

//        Map<String, Object> debitInstructionResult = httpService.postRequest(REQUEST_URL,urlParameters);
//
//        logger.info(debitInstructionResult);

    }

}
