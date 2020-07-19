package com.fline.request;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;



public class TestInterfaceUtils {

	public static String getRes(){  
        
        try {  
            RPCServiceClient ser = new RPCServiceClient ();  
            Options options = ser.getOptions();  
              
            // 指定调用WebService的URL  
            EndpointReference targetEPR = new EndpointReference("http://10.23.0.45/was/lwservices/DeptDataExchange");  
            options.setTo(targetEPR);  
            //options.setAction("命名空间/WS 方法名");   
            options.setAction("http://deptdata.webservice.dataexchange.hzwas.linewell.com/queryService");  
              
            // 指定queryService方法的参数值  
            Object[] opAddEntryArgs = new Object[0];  
            // 指定queryService方法返回值的数据类型的Class对象  
            Class[] classes = new Class[] { String.class };  
            // 指定要调用的queryService方法及WSDL文件的命名空间  
            QName opAddEntry = new QName("http://deptdata.webservice.dataexchange.hzwas.linewell.com/","queryService");  
            // 调用queryService方法并输出该方法的返回值  
            Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);  
            JSONArray jsonArray = JSONArray.fromObject(str); 
            JSONObject jUser = jsonArray.getJSONObject(1).getJSONObject("message");
            
            return str[0].toString();  
        } catch (AxisFault e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    public static void main(String[] args) {  
    	 String jsonStr = "{'result':1,'message':[{'qlsxbm':'0060f8cf-307d-4e6d-b502-33ecefca23bb','servicename':'种畜禽生产经营许可'},{'qlsxbm':'EFA79FA5DB94D12247647C103ED4B2F9','servicename':'民办学校合并审批（含学前教育、初等教育、初级中等教育、高级中等教育、自学考试助学及民办非学历高等教育机构）'}]}";
         		 JSONObject  dataJson=JSONObject.fromObject(jsonStr);
                 //JSONObject  dataList=dataJson.getJSONObject("message");
                 JSONArray list=dataJson.getJSONArray("message");
                for(int i=0;i<list.size();i++){
                	
                	JSONObject info=list.getJSONObject(i);
                	 System.out.println(info.getString("qlsxbm"));
                	 System.out.println(info.getString("servicename"));
                 }
               /*  JSONObject info=list.getJSONObject(1);
                 String name=info.getString("name");
                 String type=info.getString("type");
                  System.out.println(name+type);*/
        /*//xmlString的格式  
        StringBuilder xmlString = new StringBuilder();   
        //根据不同的接口，修改对应的格式及内容。  
        xmlString.append("<Request service="queryService" lang="zh-CN">");  
        xmlString.append("<Head></Head>");    
        xmlString.append("<Body>");    
        xmlString.append("</Body>");     
        xmlString.append("</Request>");    
        System.out.println(TestInterfaceUtils .getRes());  */
    }  

}
