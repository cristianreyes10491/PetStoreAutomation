package com.qa.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.util.TestBase;
import com.qa.util.TestUtil;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PetAvailableStatus extends TestBase{


    @BeforeMethod
    public void setUp(){
        TestBase.init();
    }

    @DataProvider
    public Object[][] getData(){
        Object testData[][] = TestUtil.getDataFromSheet(TestUtil.PetSheetName);

        return testData;
    }



    @Test(dataProvider="getData")
    public void getPetDetailsWithCorrectIdNumber(String id)
    {

        //1. define the URL base

        //https://petstore3.swagger.io
        RestAssured.baseURI = prop.getProperty("serviceurl");

        //2. define the http request:
        RequestSpecification httpRequest = RestAssured.given();

        //3. make a request/execute the request:
        Response response = httpRequest.request(Method.GET, "/");

        //4. get the response body:
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is: "+ responseBody);
        //validate pet name or validate the pet ID
        Assert.assertEquals(responseBody.contains(id), true);

         //5. get the status code and validate it:
        int statusCode = response.getStatusCode();
        System.out.println("the status code is: "+ statusCode);

        Assert.assertEquals(statusCode, TestUtil.RESPONSE_CODE_200);

        System.out.println("the status line is: "+ response.getStatusLine());

        //6. get the headers:
        Headers headers = response.getHeaders();
        System.out.println(headers);

        String contentType = response.getHeader("Content-Type");
        System.out.println("the value of content-type header is: "+ contentType);

        String contentLength = response.getHeader("Content-Length");
        System.out.println("the value of Content-Length header is: "+ contentLength);

        //get the key value by using JsonPath:
        JsonPath jsonPathValue = response.jsonPath();
        String petVal = jsonPathValue.get("Id");
        System.out.println("the name of pet is: "+ petVal);

        Assert.assertEquals(petVal, id);

        String temp = jsonPathValue.get("name_1");
        System.out.println("the name of pet is: "+ temp);
        //Assert.assertEquals(temp, name_1);


        String Category = jsonPathValue.get("category");
        System.out.println("the type of category is: "+ Category);

        String StatusDescription = jsonPathValue.get("StatusDescription");
        System.out.println("the status description is: "+ StatusDescription);

        String PhotoUrl = jsonPathValue.get("photo_url");
        System.out.println("the photourl is: "+ PhotoUrl);

        String PhotoUrl2 = jsonPathValue.get("photo_url2");
        System.out.println("the photo url 2  is: "+ PhotoUrl2);



     }



}
