package soapdust.wsdl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

public class WsdlParserTest extends TestCase {
	
	public void testAssociatesDefinitionWithNs() throws MalformedURLException, SAXException, IOException, ParserConfigurationException {
		WebServiceDescription result = new WsdlParser(new URL("file:test/soapdust/wsdl/with-simple-message.wsdl")).parse();
		
		assertNotNull(result.getDefinition("definitionNS"));
	}
}