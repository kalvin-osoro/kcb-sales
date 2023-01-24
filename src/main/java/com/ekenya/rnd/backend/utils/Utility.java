package com.ekenya.rnd.backend.utils;


import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.locationtech.jts.geom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
@RequiredArgsConstructor
public class Utility {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    static
    IDSRAccountsRepository dsrAccountsRepository;

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    //validate Status
    public static boolean validateStatus(Status status) throws Exception {
        status = status;
        ArrayList<String> arrValidStatus = new ArrayList<>();
        arrValidStatus.add("A");
        arrValidStatus.add("I");
        arrValidStatus.add("D");
        return arrValidStatus.contains(status);
    }

    public static Date getPostgresCurrentTimeStampForInsert() throws Exception {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        return sdf.parse(formattedDate);
    }




    public static String generateUniqueNoByDate() {
        SimpleDateFormat formatter1 = new SimpleDateFormat("ddMMyy");
        String unique = formatter1.format(new Date()) + RandomStringUtils.random(10, false, true);
        return unique;
    }

    //current date and time
    public static Date getCurrentDateAndTime() {
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String unique = formatter1.format(new java.util.Date());
        return new Date();

    }

    public static String merchantTempAccountId() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");

        String accountId = RandomStringUtils.random(4, false, true) +
                formatter.format(new java.util.Date());
        return accountId;
    }

    public static boolean validateGender(String status) throws Exception {
        status = status.trim();
        ArrayList<String> arrValidStatus = new ArrayList<>();
        arrValidStatus.add("M");
        arrValidStatus.add("F");
        return arrValidStatus.contains(status);
    }

    public static boolean validateUserType(String status) throws Exception {
        status = status.trim();
        ArrayList<String> arrValidStatus = new ArrayList<>();
        arrValidStatus.add("A"); // Admin
        arrValidStatus.add("O"); // Operator
        arrValidStatus.add("F"); // Field agent
        return arrValidStatus.contains(status);
    }

    public static boolean validateExpectedResponse(String expectedResponse) throws Exception {
        expectedResponse = expectedResponse.trim();
        ArrayList<String> arrValidStatus = new ArrayList<>();
        arrValidStatus.add("T");
        arrValidStatus.add("C");
        arrValidStatus.add("B");
        return arrValidStatus.contains(expectedResponse);
    }

    //fuction to check weather a point is inside a polygon or not
    public static boolean isPointInPolygon(Coordinate coordinates, Coordinate point) {
        GeometryFactory geometryFactory = new GeometryFactory();//create a geometry factory
        LinearRing linearRing = geometryFactory.createLinearRing((CoordinateSequence) coordinates);//create a linear ring
        Polygon polygon = geometryFactory.createPolygon(linearRing, null);//create a polygon
        return polygon.contains(geometryFactory.createPoint(point));//
    }

    public static String generatePIN() {
        Random rand = new Random();
        int randInt = rand.nextInt(10000);
        String password = String.format("%04d", randInt);
        return password;
    }

    public static int generateRandomNumber(int i, int i1) {
        Random rand = new Random();
        int randInt = rand.nextInt(i1);
        return randInt;
    }

    public static String generatePassword() {
        String password = RandomStringUtils.random(8, true, true);
        return password;
    }


    public static String getFileExtension(String originalFilename) {
        String extension = "";
        int i = originalFilename.lastIndexOf('.');
        if (i > 0) {
            extension = originalFilename.substring(i + 1);
        }
        return extension;
    }

    public static String getTodayDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(new java.util.Date());
        return date;
    }

    public static String getPreviousDate(int i) {
        Date date = new Date();
        long t = date.getTime();
        Date previousDate = new Date(t - (i * 24 * 3600 * 1000));
        return previousDate.toString();
    }

    public static String generateRandomNumber() {
        String password = RandomStringUtils.random(6, false, true);
        return password;
    }

    public static String getDsrNameFromDsrId(Long dsrId) {
        String dsrName = "";
        Optional<DSRAccountEntity> dsrAccount = dsrAccountsRepository.findById(dsrId);
        if (dsrAccount.isPresent()) {
            dsrName = dsrAccount.get().getFullName();
        } else {
            dsrName = "N/A";
        }
        return dsrName;
    }

    public static String getSubFolder() {
        String folderName = "salesEffective";
        return folderName;
    }



}

