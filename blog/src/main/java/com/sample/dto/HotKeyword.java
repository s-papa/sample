package com.sample.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HotKeyword {
    private String keyword;
    private int count;

    public String getKeyword() {
        return keyword;
    }

    public int getCount() {
        return count;
    }
}
