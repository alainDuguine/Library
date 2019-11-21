/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package org.alain.library.api.service.api;

import io.swagger.annotations.*;
import org.alain.library.api.service.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-19T09:35:39.403+01:00")

@Api(value = "users", description = "the users API")
public interface UsersApi {

    @ApiOperation(value = "Add a new user", nickname = "addUser", notes = "", response = UserDto.class, tags={ "user", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "User added successfully to database", response = UserDto.class),
        @ApiResponse(code = 400, message = "Parameters are incorrect"),
        @ApiResponse(code = 403, message = "You are not allowed to perform this request"),
        @ApiResponse(code = 409, message = "This email is already registered in database") })
    @RequestMapping(value = "/users",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<UserDto> addUser(@ApiParam(value = "User object that needs to be added to the database", required = true) @Valid @RequestBody UserForm userForm);


    @ApiOperation(value = "Delete a user", nickname = "deleteUser", notes = "", tags={ "user", })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "User successfully deleted"),
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 403, message = "You are not allowed to perform this request"),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/users/{id}",
        produces = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUser(@ApiParam(value = "User id to delete", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "Get user by Id", nickname = "getUser", notes = "", response = UserDto.class, tags={ "user", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User found", response = UserDto.class),
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/users/{id}",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<UserDto> getUser(@ApiParam(value = "Id of user to return", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "Get one user by email", nickname = "getUserByEmail", notes = "", response = UserDto.class, tags={ "user", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User found", response = UserDto.class),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/users/findByEmail",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<UserDto> getUserByEmail(@NotNull @ApiParam(value = "Email of user to return", required = true) @Valid @RequestParam(value = "email", required = true) String email, @ApiParam(value = "User identification", required = true) @RequestHeader(value = "Authorization", required = true) String authorization);


    @ApiOperation(value = "Get a list of all users", nickname = "getUsers", notes = "", response = UserDto.class, responseContainer = "List", tags={ "user", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Users found", response = UserDto.class, responseContainer = "List") })
    @RequestMapping(value = "/users",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<UserDto>> getUsers(@ApiParam(value = "Email of user to return", defaultValue = "") @Valid @RequestParam(value = "email", required = false, defaultValue = "") String email);


    @ApiOperation(value = "Authenticate user", nickname = "login", notes = "", tags={ "user", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Credentials valid"),
        @ApiResponse(code = 400, message = "Invalid credentials supplied") })
    @RequestMapping(value = "/users/login",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> login(@ApiParam(value = "User email and password", required = true) @Valid @RequestBody UserCredentials userCredentials);


    @ApiOperation(value = "Update a user", nickname = "updateUser", notes = "", response = UserDto.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "user", })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "User updated successfully", response = UserDto.class),
        @ApiResponse(code = 400, message = "Parameters are incorrect"),
        @ApiResponse(code = 403, message = "You are not allowed to perform this request"),
        @ApiResponse(code = 404, message = "User not found") })
    @RequestMapping(value = "/users/{id}",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<UserDto> updateUser(@ApiParam(value = "User id to update", required = true) @PathVariable("id") Long id, @ApiParam(value = "User object to update", required = true) @Valid @RequestBody UserFormUpdate userFormUpdate, @ApiParam(value = "User identification", required = true) @RequestHeader(value = "Authorization", required = true) String authorization);

}
