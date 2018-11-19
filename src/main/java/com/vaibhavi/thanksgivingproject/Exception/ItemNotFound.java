package com.vaibhavi.thanksgivingproject.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND,reason="This item does not exist in database")
public class ItemNotFound extends RuntimeException {

}
