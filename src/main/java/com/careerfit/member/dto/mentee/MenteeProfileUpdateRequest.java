package com.careerfit.member.dto.mentee;

import lombok.Getter;

import java.util.List;

@Getter
public class MenteeProfileUpdateRequest {

    private String profileImage;
    private String university;
    private String major;
    private Integer graduationYear;
    private List<String> wishCompany;
    private List<String> wishPosition;
    public void setUniversity(String university) { this.university = university; }
    public void setMajor(String major) { this.major = major; }
    public void setGraduationYear(Integer graduationYear) { this.graduationYear = graduationYear; }
    public void setWishCompany(List<String> wishCompany) { this.wishCompany = wishCompany; }
    public void setWishPosition(List<String> wishPosition) { this.wishPosition = wishPosition; }
}