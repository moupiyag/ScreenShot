package com.detectify.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by moupiya on 10/16/17.
 */
@Getter
@Setter
public class KafkaMessage {
    private List<String> urls;
    private String uuid;
}