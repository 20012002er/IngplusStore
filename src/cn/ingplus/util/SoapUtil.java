package cn.ingplus.util;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import cn.ingplus.entity.Constance;

/**
 * soap协议封装工具类
 * 
 * @author lv
 * 
 */
public class SoapUtil {

	/**
	 * 获得一个envelope对象
	 * 
	 * @param url
	 * @param namespace
	 * @param methodName
	 * @param param
	 * @return
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 */
	public static SoapSerializationEnvelope getEnvelope(String url,
			String namespace, String methodName, Object... param) throws IOException, XmlPullParserException {
		String soapAction = namespace + methodName;
		Element[] header = new Element[1];
		header[0] = new Element().createElement("http://ingplus.com/ws/auth",
				"MyAuthHeader");
		Element eToken = new Element().createElement("", "token");
		eToken.addChild(Node.TEXT, Constance.AUTH_TOKEN);
		header[0].addChild(Node.ELEMENT, eToken);
		SoapObject request = new SoapObject(namespace, methodName);
		int index = 0;
		for (Object p : param) {
			request.addProperty("arg" + index, p);
			index++;
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.headerOut = header;
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.debug = true;
		ht.call(soapAction, envelope);
		System.out.println("DUMP>> " + ht.requestDump);
		System.out.println("DUMP<< " + ht.responseDump);
		return envelope;
	}
}
