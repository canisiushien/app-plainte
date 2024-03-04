/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * 3 bis : Construction des éléments d'entete (headers) des requetes et reponse
 * http
 *
 * @author Canisius <canisiushien@gmail.com>
 */
final class CustomFilter extends HttpServletRequestWrapper {

    private final Map<String, String> headerMap = new HashMap<>();

    public CustomFilter(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        return (header != null) ? header : super.getParameter(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Set<String> set = new HashSet<>();
        Optional.ofNullable(headerMap.get(name)).ifPresent(set::add);
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaders(name);
        while (e.hasMoreElements()) {
            String n = e.nextElement();
            set.add(n);
        }
        Optional.ofNullable(headerMap.get(name)).ifPresent(set::add);
        return Collections.enumeration(set);
    }

    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }
}
