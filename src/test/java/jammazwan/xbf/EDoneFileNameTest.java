package jammazwan.xbf;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

public class EDoneFileNameTest extends CamelTestSupport {

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedFileExists("target/generated/deleteme.txt.done");
		template.requestBody("file://target/generated/?fileName=deleteme.txt&doneFileName=${file:name}.done","meaningless value");
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce
	protected ProducerTemplate template;

	@Before
	public void setUp() throws Exception {
		deleteDirectory("target/generated");
		super.setUp();
	}


}
