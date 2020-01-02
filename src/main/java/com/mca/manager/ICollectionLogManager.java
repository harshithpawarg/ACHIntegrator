package com.mca.manager;

import java.util.Date;

public interface ICollectionLogManager {

    public abstract String getMerchantOrderNumber();

    public abstract Date getLastDebitInstructionDate();

}
