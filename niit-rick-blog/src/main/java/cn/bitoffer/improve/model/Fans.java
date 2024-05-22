package cn.bitoffer.improve.model;

import lombok.Data;

import java.util.Date;


@Data
public class Fans {

    private int id;

    private String userId;

    private String fansId;

    private Date createdTime;
}
