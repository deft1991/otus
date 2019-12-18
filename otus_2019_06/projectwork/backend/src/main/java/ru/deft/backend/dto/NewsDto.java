package ru.deft.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Created by sgolitsyn on 12/17/19
 */
@NoArgsConstructor
@Getter
@Setter
public class NewsDto {
    private String userName;
    private String newsText;
    private int likeCount;
    private int disLike;
}
