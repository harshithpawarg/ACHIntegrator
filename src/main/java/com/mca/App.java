package com.mca;

import com.mca.manager.ActumDebitInstruction;
import com.mca.model.ACHCollectionSchedule;
import com.mca.model.ScheduleType;
import com.mca.service.HTTPService;
import com.mca.utils.ActumACHParameter;
import com.mca.utils.ScheduleTypes;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ACHIntegrator
 *
 */
public class App {

//    private static final Logger logger = Logger.getLogger(App.class);

    private static final String REQUEST_URL = "https://join.actumprocessing.com/cgi-bin/dbs/man_trans.cgi";
    private static final String CHK_PAYMENT_TYPE = "chk";

    public static void main( String[] args ) throws Exception {
        System.out.println("ACHIntegrator");

        ActumDebitInstruction actumDebitInstruction = new ActumDebitInstruction();

        Date debitInstructionDate = new Date();
        Date startDate = new Date();
        Date endDate = null;
        Date pauseDate = null;
        Date resumeDate = null;
        Date lastDebitInstructionDate = null;

        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
        startDate = formatter1.parse("28/11/2019");
        pauseDate = formatter1.parse("02/12/2019");
        resumeDate = formatter1.parse("05/12/2019");
        lastDebitInstructionDate = formatter1.parse("02/12/2019");

//        Integer count = actumDebitInstruction.getNumberOfDebitInstructionsToBeIssued(debitInstructionDate, startDate, endDate , pauseDate, resumeDate, lastDebitInstructionDate);
//        System.out.println("Count: " + count);

        ACHCollectionSchedule achCollectionSchedule = new ACHCollectionSchedule();

        achCollectionSchedule.setCurrentDate(debitInstructionDate);
        achCollectionSchedule.setStartDate(startDate);
        achCollectionSchedule.setEndDate(endDate);
        achCollectionSchedule.setPauseDate(pauseDate);
        achCollectionSchedule.setResumeDate(resumeDate);
        achCollectionSchedule.setLastDebitInstructionDate(lastDebitInstructionDate);

        ScheduleType scheduleType = new ScheduleType();

        String script = "import com.mca.service.DateHelper; def getNumberOfDebitInstructionCountsForACH(collectionSchedule, currentDate) {\n" +
                "    def helper = new DateHelper();\n" +
                "" +
                "    def isCurrentlyHoliday = helper.isHoliday(currentDate);\n" +
                "    def isDebitOn = true;\n" +
                "    def startDate = collectionSchedule.startDate;\n" +
                "    def endDate = collectionSchedule.endDate;\n" +
                "    def pauseDate = collectionSchedule.pauseDate;\n" +
                "    def resumeDate = collectionSchedule.resumeDate;\n" +
                "     def debitInstructionCnt = 0;\n" +
                "    if (startDate) {\n" +
                "        if(currentDate.compareTo(startDate) < 0)\n" +
                "            isDebitOn = false;\n" +
                "        else if (endDate && currentDate.compareTo(endDate) > 0)\n" +
                "             isDebitOn = false;\n" +
                "        else if (isInPause(pauseDate, resumeDate, currentDate)) {\n" +
                "            isDebitOn = false;\n" +
                "        }\n" +
                "\n" +
                "        println(\"startDate: \" + startDate + \", endDate: \" + endDate + \", currentDate\" + currentDate);\n" +
                "\n" +
                "        if (!isCurrentlyHoliday && currentDate.compareTo(startDate) >= 0) {\n" +
                "\n" +
                "            def lastDebitInstructionDate = collectionSchedule.lastDebitInstructionDate;\n" +
                "\n" +
                "            if(lastDebitInstructionDate && currentDate.compareTo(helper.format(lastDebitInstructionDate)) == 0){\n" +
                "                debitInstructionCnt = 0;\n" +
                "            }\n" +
                "            else if (currentDate.compareTo(startDate) == 0) {\n" +
                "                debitInstructionCnt = 1;\n" +
                "            } else {\n" +
                "\n" +
                "                if (lastDebitInstructionDate) {\n" +
                "                    println(\"lastDebitInstructionDate: \" + lastDebitInstructionDate);\n" +
                "                    lastDebitInstructionDate = helper.format(lastDebitInstructionDate);\n" +
                "                } else {\n" +
                "                    def calendar = Calendar.getInstance();\n" +
                "                    calendar.setTime(startDate);\n" +
                "                    calendar.add(Calendar.DAY_OF_MONTH, -1); //going one day back\n" +
                "                    lastDebitInstructionDate = calendar.getTime();\n" +
                "                    println(\"lastDebitInstructionDate in else\" + lastDebitInstructionDate);\n" +
                "                }\n" +
                "                println(\"calculated lastDebitInstructionDate\" + lastDebitInstructionDate);\n" +
                "\n" +
                "                def DaysBtwDates = helper.getDatesBetweenDates(lastDebitInstructionDate, currentDate); //excluding saturday and sunday\n" +
                "                println(\"NoOfDays Between\" + DaysBtwDates);\n" +
                "                DaysBtwDates.each { date ->\n" +
                "                    if (((endDate && date <= endDate) || (!endDate)) && !isInPause(pauseDate, resumeDate, date)) {\n" +
                "                        debitInstructionCnt = debitInstructionCnt + 1;\n" +
                "                        println(\"Incrementing count to \" + debitInstructionCnt );\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "\n" +
                "            println(\"debitInstructionCount: \" + debitInstructionCnt);\n" +
                "        }\n" +
                "    }\n" +
                "    return [\"isDebitOn\": isDebitOn, \"debitInstructionCount\": debitInstructionCnt];\n" +
                "}\n" +
                "\n" +
                "\n" +
                "def isInPause(pauseDate, resumeDate, date) {\n" +
                "    def paused = false;\n" +
                "    if (pauseDate && date.compareTo(pauseDate) > 0) {\n" +
                "        if(resumeDate) {\n" +
                "            if (date.compareTo(resumeDate) < 0) {\n" +
                "                paused = true;\n" +
                "            }\n" +
                "        } else {\n" +
                "            paused = true;\n" +
                "        }\n" +
                "    }\n" +
                "    return paused;\n" +
                "}\n";

        scheduleType.setScheduleScript(script);
        scheduleType.setScheduleType(ScheduleTypes.WEEKLY.toString());

        Map<String, Object> result = actumDebitInstruction.getNumberOfDebitInstructionsToBeIssued(achCollectionSchedule, scheduleType);

        System.out.println("RRRRRRRRRRRRRRr: " + result);

    }

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
        urlParameters.put("pmt_type",CHK_PAYMENT_TYPE);
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

        Map<String, Object> debitInstructionResult = httpService.postRequest(REQUEST_URL,urlParameters);

        System.out.println(debitInstructionResult);

    }

}
