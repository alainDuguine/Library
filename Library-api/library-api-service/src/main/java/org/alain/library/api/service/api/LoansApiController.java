package org.alain.library.api.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.alain.library.api.business.contract.LoanManagement;
import org.alain.library.api.business.exceptions.UnauthorizedException;
import org.alain.library.api.business.exceptions.UnknowStatusException;
import org.alain.library.api.business.exceptions.UnknownLoanException;
import org.alain.library.api.model.loan.Loan;
import org.alain.library.api.model.loan.LoanStatus;
import org.alain.library.api.service.dto.LoanDto;
import org.alain.library.api.service.dto.LoanStatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static org.alain.library.api.service.api.Converters.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-31T15:23:24.407+01:00")

@Controller
public class LoansApiController implements LoansApi {

    private static final Logger log = LoggerFactory.getLogger(LoansApiController.class);

    private final ObjectMapper objectMapper;
    private final LoanManagement loanManagement;
    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LoansApiController(ObjectMapper objectMapper, LoanManagement loanManagement, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.loanManagement = loanManagement;
        this.request = request;
    }

    public ResponseEntity<LoanDto> getLoan(@ApiParam(value = "Id of loan to return",required=true) @PathVariable("id") Long id) {
        Optional<Loan> loan = loanManagement.findOne(id);
        if(loan.isPresent()){
            return new ResponseEntity<LoanDto>(convertLoanModelToLoanDto(loan.get()), HttpStatus.OK);
        }
        return new ResponseEntity<LoanDto>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<LoanDto>> getLoans(@ApiParam(value = "Status values as filter in research", allowableValues = "loaned, returned, prolonged")
                                                  @Valid @RequestParam(value = "status", required = false) String status,
                                                  @ApiParam(value = "User id as filter in research") @Valid @RequestParam(value = "user", required = false) Long user) {
        List<Loan> loanList = loanManagement.findLoansByStatusAndUserId(status, user);
        return new ResponseEntity<List<LoanDto>>(convertListLoanModelToListLoanDto(loanList), HttpStatus.OK);
    }

    public ResponseEntity<List<LoanStatusDto>> getLoanHistory(@ApiParam(value = "Id of loan",required=true) @PathVariable("id") Long id) {
        try {
            List<LoanStatus> loanStatusListModel = loanManagement.getLoanStatusList(id);
            return new ResponseEntity<List<LoanStatusDto>>(convertListLoanStatusModelToListLoanStatusDto(loanStatusListModel), HttpStatus.OK);
        }catch (UnknownLoanException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    public ResponseEntity<LoanDto> addLoan(@ApiParam(value = "bookCopy Id To loan", required=true) @RequestParam(value="copyId", required=true)  Long copyId,
                                           @ApiParam(value = "user id to affect the loan to", required=true) @RequestParam(value="userId", required=true)  Long userId) {
        try {
            Loan loanModel = loanManagement.createNewLoan(copyId, userId);
            return new ResponseEntity<LoanDto>(convertLoanModelToLoanDto(loanModel), HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    public ResponseEntity<Void> updateLoan(@ApiParam(value = "Id of loan to update",required=true) @PathVariable("id") Long id,
                                           @ApiParam(value = "Status values to add to loan history" ,required=true )  @Valid @RequestBody String status) {
        try {
            Optional<LoanStatus> loanStatus = loanManagement.updateLoan(id, status);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }catch (UnknowStatusException ex){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());}
    }

    public ResponseEntity<Void> extendLoan(@ApiParam(value = "Id of loan",required=true) @PathVariable("id") Long id,
                                           @ApiParam(value = "User identification" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            Optional<LoanStatus> loanStatus = loanManagement.extendLoan(id, authorization);
            if(loanStatus.isPresent()) {
                return new ResponseEntity<Void>(HttpStatus.OK);
            }else{
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        }catch(UnauthorizedException ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to extend this loan");
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not allowed to extend this loan");
        }
    }
}
