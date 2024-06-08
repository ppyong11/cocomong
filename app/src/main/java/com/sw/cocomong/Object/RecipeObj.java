package com.sw.cocomong.Object;

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
public class RecipeObj {

    private String recipename;
    private String main;
    private String sub1;
    private String sub2;
    private String sub3;
    private String recipeLink;
}