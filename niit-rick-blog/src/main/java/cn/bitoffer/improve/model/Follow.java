package cn.bitoffer.improve.model;

import lombok.Data;

import java.util.Date;


@Data
public class Follow {

    private int id;

    private String userId;

    private String followId;

    private Date createdTime;
}
