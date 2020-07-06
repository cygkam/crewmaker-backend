package com.crewmaker.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingOption {
    private int activePage;
    private int size;
}
