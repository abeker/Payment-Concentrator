package com.sep.Zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sep.Zuul.dto.PaymentTypes;
import feign.FeignException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"IfStatementWithIdenticalBranches", "SpellCheckingInspection"})
@Component
public class AuthFilter extends ZuulFilter {

    private final EurekaClient eurekaClient;

    public AuthFilter(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    private void setFailedRequest(String body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if(request.getHeader("Auth-Token") == null) {
            return null;
        }

        boolean hasPaymentType = eurekaClient.hasPaymentType("1", "BITCOIN");
        PaymentTypes paymentTypes = new PaymentTypes();
        List<String> paymentTypeNames = new ArrayList<>();
        paymentTypeNames.add("BITCOIN");
        paymentTypes.setPaymentTypeNames(paymentTypeNames);
        eurekaClient.registerLiteraryAssociation("1", paymentTypes);

//        String token = request.getHeader("Auth-Token");
//        try {
//            String username = eurekaClient.verify(token);
//            if(username != null) {
//                String permissionList = eurekaClient.getPermission(token);
//                ctx.addZuulRequestHeader("roles", permissionList);
//                ctx.addZuulRequestHeader("username", username);
//            }
//        } catch (FeignException.NotFound e) {
//            setFailedRequest("User does not exist!", 403);
//        }


        return null;
    }


}
