package com.mca.manager;

import com.mca.model.ACHCollectionSchedule;
import com.mca.model.ScheduleType;
import com.mca.service.DateHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.script.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ActumDebitInstruction {

//    private static final Logger logger = Logger.getLogger(ActumDebitInstruction.class);


    public Integer getNumberOfDebitInstructionsToBeIssued_X(Date debitInstructionDate, Date startDate, Date endDate, Date pauseDate,
                                                          Date resumeDate, Date lastDebitInstructionDate) {

        System.out.println("Debit Instruction Date: " + debitInstructionDate);
        System.out.println();

        Integer debitInstructionCount = 0;

        DateHelper helper = new DateHelper();
        Boolean isCurrentlyHoliday = helper.isHoliday(helper.format(debitInstructionDate));

        if (!isCurrentlyHoliday && debitInstructionDate.compareTo(startDate) >= 0) {

            if(lastDebitInstructionDate != null && debitInstructionDate.compareTo(lastDebitInstructionDate) == 0){
                debitInstructionCount = 0;
            }
            else if (debitInstructionDate.compareTo(startDate) == 0) {
                debitInstructionCount = 1;
            } else {

                if (lastDebitInstructionDate != null) {
                    lastDebitInstructionDate = helper.format(lastDebitInstructionDate);
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(startDate);
                    calendar.add(Calendar.DAY_OF_MONTH, -1); //going one day back
                    lastDebitInstructionDate = calendar.getTime();
                }

                List<Date> daysBtwDates = helper.getDatesBetweenDates(lastDebitInstructionDate, debitInstructionDate); //excluding saturday and sunday
                System.out.println("No of days between " + lastDebitInstructionDate + " and " + debitInstructionDate + " is " + daysBtwDates.size());

                for (Date date : daysBtwDates) {
                    if (((endDate != null && date.compareTo(endDate) <= 0) || (endDate == null)) && !isInPause(pauseDate, resumeDate, date)) {
                        debitInstructionCount = debitInstructionCount + 1;
                    }
                }

            }

            System.out.println("Total Debit Instruction Count: " + debitInstructionCount);
        }

        return debitInstructionCount;
    }

    private Boolean isInPause(Date pauseDate, Date resumeDate, Date date) {
        Boolean isPaused = false;
        if (pauseDate != null && date.compareTo(pauseDate) > 0) {
            if(resumeDate != null) {
                if (date.compareTo(resumeDate) < 0) {
                    isPaused = true;
                }
            } else {
                isPaused = true;
            }
        }
        return isPaused;
    }


    public Map<String, Object> getNumberOfDebitInstructionsToBeIssued(ACHCollectionSchedule achCollectionSchedule, ScheduleType scheduleType) {

        Integer debitInstructionCount = 0;

        System.out.println(achCollectionSchedule.toString());

        System.out.println(scheduleType.toString());

        ScriptEngine engine = getScriptEngine();

        ScriptEngineManager manager = new ScriptEngineManager();

        for (ScriptEngineFactory se : new ScriptEngineManager().getEngineFactories()) {
            System.out.println("se = " + se.getEngineName());
            System.out.println("se = " + se.getEngineVersion());
            System.out.println("se = " + se.getLanguageName());
            System.out.println("se = " + se.getLanguageVersion());
            System.out.println("se = " + se.getNames());
        }


        System.out.println(engine);


        Map<String, Object> result = null;

        try {
            String scheduleTypeScript = scheduleType.getScheduleScript();
            String collectionScheduleType = scheduleType.getScheduleType();
            try {
                engine.eval(scheduleTypeScript);
                Invocable inv = (Invocable) engine;
                Object[] parameters = {achCollectionSchedule, achCollectionSchedule.getCurrentDate()};
                Object returnResult = inv.invokeFunction("getNumberOfDebitInstructionCountsForACH", parameters);
                result = (Map<String, Object>) returnResult;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return result;

    }

    public ScriptEngine getScriptEngine() {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("groovy");
        return engine;
    }

}
