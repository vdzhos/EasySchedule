package com.easyschedule.services.interfaces;

import java.io.InputStream;
public interface ScheduleReaderSaverService {
    void readSaveSchedule(InputStream inputStream, long specialtyId) throws Exception;

}
