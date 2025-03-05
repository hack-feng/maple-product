package com.maple.es.bean;

import lombok.Data;

@Data
public class Page {

    private Integer page;

    private Integer size;

    private Long total;

}
