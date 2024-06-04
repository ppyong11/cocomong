package com.sw.cocomong.Object;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Data
@Builder
@ToString
public class FoodResObj {
    private String idx;
    private String username;
    private String refname;
    private String foodname;
    private String expiredate;
    private String category;
    private String memo;
    private String favorite;

}
