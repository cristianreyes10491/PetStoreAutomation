package com.qa.tests;


import com.qa.pages.petIdAPI;
import com.qa.util.ExcelUtils;
import com.qa.util.TestBase;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

    //This class contains the tests to do to the endpoints
public class UserAPITest extends TestBase {

        petIdAPI userAPI = new petIdAPI();

        @DataProvider(name = "userData")
        public Object[][] userData() throws IOException {
            ExcelUtils excelUtils = new ExcelUtils("src/main/java/com/qa/testdata/APITestData.xlsx", "PetInfo");
            List<Map<String, String>> data = excelUtils.getAllData();
            excelUtils.close();

            Object[][] dataArray = new Object[data.size()][1];
            for (int i = 0; i < data.size(); i++) {
                dataArray[i][0] = data.get(i);
            }
            return dataArray;
        }

        @Test(dataProvider = "userData")
        public void testAPI(Map<String, String> userData) {
            String method = userData.get("method");
            switch (method.toLowerCase()) {
                case "get":
                    testGetPet(userData);
                    break;
                case "post":
                    testCreatePetPost(userData);
                    break;

                default:
                    throw new IllegalArgumentException("Method HTTP non supported: " + method);
            }
        }

        public void testGetPet(Map<String, String> userData) {
            int petId = Integer.parseInt(userData.get("id"));
            Response response = userAPI.getPetById(petId);
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.jsonPath().getString("name"), userData.get("name"));
        }

        public void testCreatePetPost(Map<String, String> userData) {
            String name = userData.get("name");
            String email = userData.get("email");
            String id = userData.get("id");
            Response response = userAPI.createPet(id, name, email);
            Assert.assertEquals(response.getStatusCode(), 200);
        }

    }