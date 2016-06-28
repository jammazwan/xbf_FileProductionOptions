package jammazwan.xbf;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

public class DMoveOldFileTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:start").to("file://target/generated/?fileName=deleteme.txt").to("mock:result");
			}
		};
	}

	@Test
	public void testSendMatchingMessage() throws Exception {
		template1.requestBody("direct:start", "something");
		resultEndpoint.expectedMessageCount(1);
		template2.requestBody("file://target/generated/?fileName=deleteme.txt&fileExist=Move&moveExisting=previous", "other");
		resultEndpoint.expectedFileExists("target/generated//deleteme.txt","other");
		fileEndpoint.expectedFileExists("target/generated/previous/deleteme.txt","something");
		resultEndpoint.assertIsSatisfied();
		fileEndpoint.assertIsSatisfied();
	}

	@Produce
	protected ProducerTemplate template2;

	@Produce
	protected ProducerTemplate template1;
	
	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;
	
	@EndpointInject(uri = "mock:file")
	protected MockEndpoint fileEndpoint;

	@Before
	public void setUp() throws Exception {
		deleteDirectory("target/generated");
		super.setUp();
	}



}
