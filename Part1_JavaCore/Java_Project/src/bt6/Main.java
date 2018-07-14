/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt6;

import java.util.HashMap;

/**
 *
 * @author quancq
 */
public class Main {

    public static void main(String[] args) {
        String url = "https://www.google.com.vn/search?q=string+java+practice&rlz=1C1CHZL_viVN741VN741&oq=string&aqs=chrome.2.69i57j69i60j69i59j69i60.2342j0j1&sourceid=chrome&ie=UTF-8";
        System.out.println("URL = " + url);

        String domainName = getDomainName(url);
        System.out.println("Domain name : " + domainName);

        HashMap<String, String> mapParamToValue = getQueryInformation(url);
        System.out.println("HashMap <param, value> : ");
        printMap(mapParamToValue);
        
        // Change query
        mapParamToValue.put("u", "abc");
        mapParamToValue.put("v", "ee");
        mapParamToValue.put("q", "js+alaba");
        
        // Build new url from map
        String newURL = buildURL(mapParamToValue);
        System.out.println("New url : " + newURL);
    }

    /**
     *
     * @param url
     * @return domain name of url
     */
    private static String getDomainName(String url) {
        // Copy a part of url without protocol (http:// or https://)
        String temp = url.split("://")[1];
        int firstIndex = temp.indexOf("/");
        String hostName = temp.substring(0, firstIndex);
        String prefix = "www.";
        String domainName = hostName;
        if (hostName.startsWith(prefix)) {
            domainName = hostName.substring(prefix.length());
        }
        return domainName;
    }

    /**
     * 
     * @param url
     * @return HashMap contain pairs of (query parameter, query value)
     */
    private static HashMap<String, String> getQueryInformation(String url) {
        HashMap<String, String> mapParamToValue = new HashMap<>();
        String queryStrings = url.substring(url.indexOf("?") + 1);
        String[] queryArr = queryStrings.split("&");
        for (String query : queryArr) {
            String[] temp = query.split("=");
            mapParamToValue.put(temp[0], temp[1]);
        }

        return mapParamToValue;
    }

    /**
     * 
     * @param mapParamToValue
     * @return new url build from map
     */
    private static String buildURL(HashMap<String, String> mapParamToValue){
        StringBuilder url = new StringBuilder("https://www.google.com.vn/search?");
        for(String key : mapParamToValue.keySet()){
            url.append(key + "=" + mapParamToValue.get(key) + "&");
        }
        url.deleteCharAt(url.length() - 1);
                
        return url.toString();
    }
    
    private static void printMap(HashMap<String, String> map) {
        
        map.forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
        
    }
}
