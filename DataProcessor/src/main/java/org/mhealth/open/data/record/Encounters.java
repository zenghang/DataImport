package org.mhealth.open.data.record;

import java.text.ParseException;
import java.time.Instant;

public class Encounters extends SRecord{

    public Encounters(String[] line){
        this.encounter= line[0];
        try {
            this.date = dateFormat.parse(line[1]).toInstant();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.userId = line[2];
        this.code = line[3];
        this.description = line[4];
        if(line.length>6) {
            this.rcode = line[5];
            this.reasondescription = line[6];
        }

    }

    public String encounter() {
        return encounter;
    }

    public Instant getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public String getCode() { return code;}

    public String getRCode() { return rcode;}

    public String getDescription() {
        return description;
    }

    public String getResondescription() {
        return reasondescription;
    }
}
