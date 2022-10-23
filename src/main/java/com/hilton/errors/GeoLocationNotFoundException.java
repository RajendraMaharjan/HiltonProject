package com.hilton.errors;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;

public class GeoLocationNotFoundException extends FileNotFoundException {
    public GeoLocationNotFoundException() {
    }

    public GeoLocationNotFoundException(String s) {
        super(s);
    }
}
