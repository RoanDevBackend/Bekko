package com.example.bookingserverquery.application.reponse;

import com.example.bookingserverquery.application.query.QueryBase;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
public class GetAllResponse<T> extends QueryBase<T> {

    int totalPage;
    List<T> contentResponse;

}