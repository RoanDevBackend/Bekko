package com.example.bookingserver.controller;

import com.example.bookingserver.application.command.specialize.CreateSpecializeCommand;
import com.example.bookingserver.application.command.specialize.UpdateSpecializeCommand;
import com.example.bookingserver.application.handle.specialize.*;
import com.example.bookingserver.application.query.QueryBase;
import com.example.bookingserver.application.reponse.ApiResponse;
import com.example.bookingserver.application.reponse.SpecializeResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/specialize")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecializeController {

    CreateSpecializeHandler createSpecializeHandler;
    UpdateSpecializeHandler updateSpecializeHandler;
    DeleteSpecializeHandler deleteSpecializeHandler;
    FindBySpecializeIdHandler findBySpecializeIdHandler;
    FindByDepartmentHandler findByDepartmentHandler;

    @PostMapping
    public ApiResponse create(@RequestBody @Valid CreateSpecializeCommand command){
        var response= createSpecializeHandler.execute(command);
        return ApiResponse.success(201, "Thêm thành công", response, HttpStatus.CREATED);
    }

    @PutMapping
    public ApiResponse update(@RequestBody @Valid UpdateSpecializeCommand command){
        var response= updateSpecializeHandler.execute(command);
        return ApiResponse.success(200, "Sửa thành công", response);
    }

    @DeleteMapping(value = "/{ids}")
    public ApiResponse delete(@PathVariable("ids") List<String> ids){
        deleteSpecializeHandler.execute(ids);
        return ApiResponse.success(200, "Xoá thành công");
    }

    @GetMapping(value = "/{id}")
    public ApiResponse get(@PathVariable("id") String id){
        var response= findBySpecializeIdHandler.execute(id);
        return ApiResponse.success(200, "Tìm kiếm thành công", response);
    }

    @Operation(summary = "Tìm kiếm chuyên ngành của chuyên khoa")
    @PostMapping(value = "/department/{id}")
    public ApiResponse getByDepartment(@PathVariable("id") String id, @RequestBody(required = false) QueryBase<SpecializeResponse> queryBase){
        if(queryBase == null) queryBase = QueryBase.<SpecializeResponse>builder().build();
        var response= findByDepartmentHandler.execute(id, queryBase);
        return ApiResponse.success(200, "Tìm kiếm thành công" ,response);
    }
}
