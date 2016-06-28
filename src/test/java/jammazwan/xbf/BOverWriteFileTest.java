package jammazwan.xbf;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

public class BOverWriteFileTest extends CamelTestSupport {

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedFileExists("target/generated/deleteme.txt", "meaningless value");
		template1.requestBody("file://target/generated/?fileName=deleteme.txt", "value to be overwritten");
		template2.requestBody("file://target/generated/?fileName=deleteme.txt", "meaningless value");
		template3.requestBody("file://target/generated/?fileName=deleteme.txt&fileExist=Ignore",
				"value that should never be written, because fileExist=Ignore");
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce
	protected ProducerTemplate template1;

	@Produce
	protected ProducerTemplate template3;

	@Produce
	protected ProducerTemplate template2;

	@Before
	public void setUp() throws Exception {
		deleteDirectory("target/generated");
		super.setUp();
	}

}
