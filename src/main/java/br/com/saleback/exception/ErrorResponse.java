package br.com.saleback.exception;

import lombok.*;

import java.util.Date;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Date timestamp;
    private Integer status;
    private String statusText;
    private String path;

}
