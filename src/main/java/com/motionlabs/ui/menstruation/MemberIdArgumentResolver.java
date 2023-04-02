package com.motionlabs.ui.menstruation;


import com.motionlabs.common.exception.NeedAuthorizationException;
import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberId.class) &&
            Long.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest nativeRequest = Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class));

        return Arrays.stream(nativeRequest.getCookies())
            .filter(cookie -> cookie.getName().equals("memberId"))
            .mapToLong(cookie -> Long.parseLong(cookie.getValue()))
            .findFirst()
            .orElseThrow(NeedAuthorizationException::new);

    }
}
