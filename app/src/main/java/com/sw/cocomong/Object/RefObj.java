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
public class RefObj {
    private String refid;
    private String refName;
    private String username;

}
