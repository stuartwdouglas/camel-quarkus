/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.quarkus.component.file.it;

import java.nio.file.Paths;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
class FileTest {

    private static final String FILE_BODY = "Hello Camel Quarkus";

    @Test
    public void testFileComponent() {
        // Create a new file
        String fileName = RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(FILE_BODY)
                .post("/file/create")
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        // Read the file
        RestAssured
                .get("/file/get/" + Paths.get(fileName).getFileName())
                .then()
                .statusCode(200)
                .body(equalTo(FILE_BODY));
    }

}
