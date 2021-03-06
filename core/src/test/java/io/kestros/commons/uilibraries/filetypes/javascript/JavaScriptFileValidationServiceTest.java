/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.kestros.commons.uilibraries.filetypes.javascript;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class JavaScriptFileValidationServiceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private JavaScriptFileValidationService validationService;

  private JavaScriptFile jsFile;

  @Before
  public void setUp() throws Exception {
    jsFile = context.create().resource("/file.js").adaptTo(JavaScriptFile.class);
    validationService = spy(new JavaScriptFileValidationService());

    doReturn(jsFile).when(validationService).getGenericModel();
  }

  @Test
  public void testGetModel() {
    validationService.registerBasicValidators();
    assertEquals(jsFile, validationService.getModel());
  }

  @Test
  public void testRegisterBasicValidators() {
    validationService.registerBasicValidators();
    assertEquals(1, validationService.getBasicValidators().size());
    assertTrue(validationService.getBasicValidators().get(0).isValid());
    assertEquals("Resource name ends with .js extension.",
        validationService.getBasicValidators().get(0).getMessage());
    assertEquals(ModelValidationMessageType.ERROR,
        validationService.getBasicValidators().get(0).getType());
  }
}