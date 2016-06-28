/**
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
package jammazwan.xbf;

import java.io.File;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

// code stolen shamelessly from camel-core's FileProducerAllowNullBodyTest
public class FAllowNullBodyFileTest extends CamelTestSupport {

	@Test
	public void testSendMatchingMessage() throws Exception {
		try {
			template.sendBody("file://target/allow?fileName=allowNullBody.txt", null);
			fail("Should have thrown a GenericFileOperationFailedException");
		} catch (CamelExecutionException e) {
			GenericFileOperationFailedException cause = assertIsInstanceOf(GenericFileOperationFailedException.class,
					e.getCause());
			assertTrue(cause.getMessage().endsWith("allowNullBody.txt"));
		}
		assertFalse("allowNullBody set to false with null body should not create a new file",
				new File("target/allow/allowNullBody.txt").exists());
		template.sendBody("file://target/allow?fileName=allowNullBody.txt&allowNullBody=true", null);
		fileEndpoint.expectedFileExists("target/allow/allowNullBody.txt");
		fileEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:file")
	protected MockEndpoint fileEndpoint;

	@Produce
	protected ProducerTemplate template;

	@Before
	public void setUp() throws Exception {
		deleteDirectory("target/allow");
		super.setUp();
	}

}
