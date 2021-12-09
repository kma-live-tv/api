package com.nopain.livetv.decorator;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

import static com.nopain.livetv.configuration.SpringDocConfiguration.JWT_SECURITY_SCHEME_NAME;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Secured("ROLE_USER")
@SecurityRequirement(name = JWT_SECURITY_SCHEME_NAME)
public @interface JWTSecured {
}
