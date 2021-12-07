package com.nopain.livetv.decorator;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Secured("ROLE_USER")
public @interface JWTSecured {
}
