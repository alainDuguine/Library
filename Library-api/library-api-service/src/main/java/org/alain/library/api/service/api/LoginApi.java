/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package org.alain.library.api.service.api;

import io.swagger.annotations.*;
import org.alain.library.api.service.dto.UserCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-31T08:40:05.054+01:00")

@Api(value = "login", description = "the login API")
public interface LoginApi {

    @ApiOperation(value = "Authenticate user", nickname = "login", notes = "", tags={ "users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Credentials valid"),
        @ApiResponse(code = 400, message = "Invalid credentials supplied") })
    @RequestMapping(value = "/login",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> login(@ApiParam(value = "User mail and password", required = true) @Valid @RequestBody UserCredentials userCredentials);

}
