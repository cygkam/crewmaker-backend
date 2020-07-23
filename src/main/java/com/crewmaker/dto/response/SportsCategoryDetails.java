package com.crewmaker.dto.response;

import com.crewmaker.entity.SportsCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SportsCategoryDetails {
    private int sportsCategoryID;
    private String sportsCategoryName;

    public SportsCategoryDetails(SportsCategory sportsCategory) {
        this.sportsCategoryID = sportsCategory.getSportsCategoryId();
        this.sportsCategoryName = sportsCategory.getSportCategoryName();
    }

}