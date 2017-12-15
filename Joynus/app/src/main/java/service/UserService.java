package service;


import dtomodels.userDTO.UserProfileDTO;
import model.User;

public class UserService
{
    public static void UserFromUserProfileDTO(UserProfileDTO profileToParse, User userToFill)
    {
        userToFill.setBirthdate(profileToParse.getBirthdate());
        userToFill.setFirstname(profileToParse.getFirstName());
        userToFill.setLastname(profileToParse.getLastName());
        userToFill.setInterests(CategoryService.categoryNamesListToCategoryList(profileToParse.getInterests()));
    }
}

