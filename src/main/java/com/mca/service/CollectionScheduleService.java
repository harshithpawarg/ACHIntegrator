package com.mca.service;

import com.mca.App;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class CollectionScheduleService {

    private static final Logger logger = Logger.getLogger(CollectionScheduleService.class);

    public static final String REQUEST_URL = "https://join.actumprocessing.com/cgi-bin/dbs/man_trans.cgi";

    public CollectionScheduleService() {

    }


    public Map<String, Object> issueDebitInstruction(Map<String, Object> inputMap) {

        Map<String, Object> debitInstructionResult = new HashMap<>();

        System.out.println("inputMap: " + inputMap);

        Object parentId = inputMap.get("parent_id");
        Object subId = inputMap.get("sub_id");
        Object pmtType = inputMap.get("pmt_type");
        Object chkAcct = inputMap.get("chk_acct");
        Object chkAba = inputMap.get("chk_aba");
        Object customerName = inputMap.get("custname");
        Object customerEmail = inputMap.get("custemail");
        Object customerPhone = inputMap.get("custphone");
        Object amount = inputMap.get("initial_amount");
        Object billingCycle = inputMap.get("billing_cycle");
        Object merchantOrderNumber = inputMap.get("merordernumber");
        Object ipForward = inputMap.get("ip_forward");
        Object transModifier = inputMap.get("trans_modifier");

        if (parentId == null || parentId.toString().isEmpty())
            throw new IllegalArgumentException("The argument parent_id cannot be null");
        else if (subId == null || subId.toString().isEmpty())
            throw new IllegalArgumentException("The argument sub_id cannot be null");
        else if (pmtType == null || pmtType.toString().isEmpty())
            throw new IllegalArgumentException("The argument pmt_type cannot be null");
        else if (chkAcct == null || chkAcct.toString().isEmpty())
            throw new IllegalArgumentException("The argument chk_acct cannot be null");
        else if (chkAba == null || chkAba.toString().isEmpty())
            throw new IllegalArgumentException("The argument chk_aba cannot be null");
        else if (customerName == null || customerName.toString().isEmpty())
            throw new IllegalArgumentException("The argument custname cannot be null");
        else if (customerEmail == null || customerEmail.toString().isEmpty())
            throw new IllegalArgumentException("The argument custemail cannot be null");
        else if (customerPhone == null || customerPhone.toString().isEmpty())
            throw new IllegalArgumentException("The argument custphone cannot be null");
        else if (amount == null || amount.toString().isEmpty())
            throw new IllegalArgumentException("The argument initial_amount cannot be null");
        else if (billingCycle == null || billingCycle.toString().isEmpty())
            throw new IllegalArgumentException("The argument billing_cycle cannot be null");
        else if (merchantOrderNumber == null || merchantOrderNumber.toString().isEmpty())
            throw new IllegalArgumentException("The argument merordernumber cannot be null");


        try {

            if (ipForward == null)
                ipForward = "http://smartmca.com";


            parentId = URLEncoder.encode(parentId.toString(), "UTF-8");
            subId = URLEncoder.encode(subId.toString(), "UTF-8");
            pmtType = URLEncoder.encode(pmtType.toString(), "UTF-8");
            chkAcct = URLEncoder.encode(chkAcct.toString(), "UTF-8");
            chkAba = URLEncoder.encode(chkAba.toString(), "UTF-8");
            customerName = URLEncoder.encode(customerName.toString(), "UTF-8");
            customerEmail = URLEncoder.encode(customerEmail.toString(), "UTF-8");
            customerPhone = URLEncoder.encode(customerPhone.toString(), "UTF-8");
            billingCycle = URLEncoder.encode(billingCycle.toString(), "UTF-8");
            merchantOrderNumber = URLEncoder.encode(merchantOrderNumber.toString(), "UTF-8");
            amount = URLEncoder.encode(amount.toString(), "UTF-8");
            ipForward = URLEncoder.encode(ipForward.toString(), "UTF-8");


            System.out.println("Collection schedule request with subId: " + subId + ", customerName: " + customerName +
                    ", customerEmail: " + customerEmail + ", customerPhone: " + customerPhone);

            String urlParameters = "parent_id=" + parentId + "&sub_id=" + subId + "&pmt_type=" + pmtType + "&chk_acct=" + chkAcct +
                    "&chk_aba=" + chkAba + "&custname=" + customerName + "&custemail=" + customerEmail + "&custphone=" + customerPhone +
                    "&initial_amount=" + amount + "&billing_cycle=" + billingCycle + "&merordernumber=" + merchantOrderNumber +
                    "&ip_forward=" + ipForward;

            if (transModifier != null && !transModifier.toString().isEmpty()) {
                transModifier = URLEncoder.encode(transModifier.toString(), "UTF-8");
                urlParameters += "&trans_modifier=" + transModifier;
            }


            debitInstructionResult = issueInstruction(urlParameters);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return debitInstructionResult;
    }


    private Map<String, Object> issueInstruction(String urlParameters) {

        Map<String, Object> debitInstructionResult = new HashMap<>();

        try {
            URL url = new URL(REQUEST_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //add reuqest header
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();


            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {
                System.out.println(inputLine);
                String arr[] = inputLine.split("=");
                debitInstructionResult.put(arr[0], arr[1]);
            }

            bufferedReader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return debitInstructionResult;
    }

    public Map<String, Object> initiateRefundInstruction(Map<String, Object> inputMap) {

        Map<String, Object> debitInstructionResult = new HashMap<>();

        System.out.println("inputMap: " + inputMap);
        Object username = inputMap.get("user_name");
        Object password = inputMap.get("password");
        Object amount = inputMap.get("amount");
        Object actionCode = inputMap.get("action_code");
        Object orderId = inputMap.get("order_id");
        Object prevHistoryId = inputMap.get("prev_history_id");
        Object merchantOrderNumber = inputMap.get("merchant_order_number");
        Object sysPass = inputMap.get("sys_pass");
        Object ipForward = inputMap.get("ip_forward");

        if (username == null || username.toString().isEmpty())
            throw new IllegalArgumentException("The argument user_name cannot be null");
        else if (password == null || password.toString().isEmpty())
            throw new IllegalArgumentException("The argument password cannot be null");
        else if (amount == null || amount.toString().isEmpty())
            throw new IllegalArgumentException("The argument amount cannot be null");
        else if (actionCode == null || actionCode.toString().isEmpty())
            throw new IllegalArgumentException("The argument action_code cannot be null");
        else if (orderId == null || orderId.toString().isEmpty())
            throw new IllegalArgumentException("The argument order_id cannot be null");
        else if (prevHistoryId == null || prevHistoryId.toString().isEmpty())
            throw new IllegalArgumentException("The argument prev_history_id cannot be null");
        else if (merchantOrderNumber == null || merchantOrderNumber.toString().isEmpty())
            throw new IllegalArgumentException("The argument merchant_order_number cannot be null");
        else if (sysPass == null || sysPass.toString().isEmpty())
            throw new IllegalArgumentException("The argument sys_pass cannot be null");

        try {

            if (ipForward == null)
                ipForward = "http://smartmca.com";

            username = URLEncoder.encode(username.toString(), "UTF-8");
            password = URLEncoder.encode(password.toString(), "UTF-8");
            amount = URLEncoder.encode(amount.toString(), "UTF-8");
            actionCode = URLEncoder.encode(actionCode.toString(), "UTF-8");
            orderId = URLEncoder.encode(orderId.toString(), "UTF-8");
            prevHistoryId = URLEncoder.encode(prevHistoryId.toString(), "UTF-8");
            merchantOrderNumber = URLEncoder.encode(merchantOrderNumber.toString(), "UTF-8");
            sysPass = URLEncoder.encode(sysPass.toString(), "UTF-8");
            ipForward = URLEncoder.encode(ipForward.toString(), "UTF-8");


            String urlParameters = "username=" + username +
                    "&password=" + password +
                    "&Initial_amount=" + amount +
                    "&action_code=" + actionCode +
                    "&order_id=" + orderId +
                    "&syspass=" + sysPass +
                    "&prev_history_id=" + prevHistoryId +
                    "&merordernumber=" + merchantOrderNumber +
                    "&ip_forward=" + ipForward;

            debitInstructionResult = issueInstruction(urlParameters);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return debitInstructionResult;
    }

}
