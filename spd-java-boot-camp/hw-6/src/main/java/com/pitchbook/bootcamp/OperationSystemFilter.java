package com.pitchbook.bootcamp;

import com.pitchbook.bootcamp.exception.OSNotSupportedException;
import eu.bitwalker.useragentutils.UserAgent;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class OperationSystemFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
       if(isBrowserAllowed(req)){
           chain.doFilter(req, res);
       }else{
           throw new OSNotSupportedException("Your OS does not support this operation!");
       }
    }

    private boolean isBrowserAllowed(HttpServletRequest request){
        UserAgent userAgent = UserAgent.parseUserAgentString(( request)
                .getHeader("User-Agent"));
        return !userAgent.getOperatingSystem().getName().equals("WINDOWS_VISTA") ||
                !userAgent.getOperatingSystem().getName().equals("WINDOWS_XP");
    }

}
