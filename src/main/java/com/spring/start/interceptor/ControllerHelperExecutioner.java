package com.spring.start.interceptor;

import com.spring.start.helper.ControllerHelper;
import lombok.experimental.var;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * Created by Mateusz on 27.03.2017.
 */
@var
@Log4j
@Aspect
@Component
public class ControllerHelperExecutioner {

    @Autowired
    private ControllerHelper controllerHelper;

    @Around("execution(* com.spring.start.controller..*.*(..))")
    public Object userDataSetter(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            var method = ((MethodSignature) joinPoint.getSignature()).getMethod();

            log.info(String.format("*** Found method %s", method));

            var maybeModel = Arrays.stream(joinPoint.getArgs())
                    .filter(a -> a instanceof Model)
                    .map(a -> (Model) a)
                    .findFirst();

            if (maybeModel.isPresent()) {
                var model = maybeModel.get();

                var maybeAnnotation = Arrays.stream(method.getAnnotations())
                        .filter(a -> a instanceof RequestMapping)
                        .map(a -> (RequestMapping) a)
                        .findFirst();

                if (maybeAnnotation.isPresent()) {

                    var annotation = maybeAnnotation.get();

                    log.info(String.format("*** Found method with annotation '%1s' and model parameter '%2s'",
                            annotation,
                            model));

                    log.info("*** All requirements matched - about to set user data in model");
                    controllerHelper.setUserData(model);

                } else {
                    log.warn("*** Annotation not found - skipping");
                }
            } else {
                log.warn("*** Model not found - skipping");
            }
        } catch (Exception e) {
            log.warn("*** Error during setting data: " + e);
        }

        return joinPoint.proceed();
    }

}
