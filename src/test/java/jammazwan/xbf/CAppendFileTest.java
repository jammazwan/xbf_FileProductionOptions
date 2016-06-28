package jammazwan.xbf;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

public class CAppendFileTest extends CamelTestSupport {

	@Test
	public void testSendMatchingMessage() throws Exception {
		template1.requestBody("file://target/generated/?fileName=deleteme.txt","something");
		template2.requestBody("file://target/generated/?fileName=deleteme.txt&fileExist=Append"," more");
		fileEndpoint.expectedFileExists("target/generated/deleteme.txt","something more");
		fileEndpoint.assertIsSatisfied();
	}

	@Produce
	protected ProducerTemplate template2;

	@Produce
	protected ProducerTemplate template1;

	
	@EndpointInject(uri = "mock:file")
	protected MockEndpoint fileEndpoint;

	@Before
	public void setUp() throws Exception {
		deleteDirectory("target/generated");
		super.setUp();
	}


}
