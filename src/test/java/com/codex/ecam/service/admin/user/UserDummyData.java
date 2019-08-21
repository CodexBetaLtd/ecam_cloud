package com.codex.ecam.service.admin.user;

import java.util.Arrays;

import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.asset.Asset;

/**
 * Created by neo89 on 12/19/16.
 */
public class UserDummyData {

    private static UserDummyData instance = null;

    private UserDummyData(){
    }

    public static UserDummyData getInstance() {
        if (instance == null) {
            instance = new UserDummyData();
        }
        return instance;
    }

    //public enum UserData { INITIALIZING, IN_PROGRESS, COMPLETE };


/*
    public enum UserData {

        USER_DUMMY(UserDummyData.getDummyUserDTO()),
        USER_DUMMY_1(UserDummyData.getDummyUserDTOOne()),
        USER_DUMMY_2(UserDummyData.getDummyUserDTOTwo()),
        USER_DUMMY_3(UserDummyData.getDummyUserDTOThree());


        private User user;
        private UserDTO userDTO;

        UserData(User user) {
            this.user = user;
        }
        UserData(UserDTO userDTO) {
            this.userDTO = userDTO;
        }

    }






    public List<UserDTO> getUserList(){
        List<UserDTO> list = new ArrayList<UserDTO>();
        list.add(UserData.USER_DUMMY_1.userDTO);
        list.add(UserData.USER_DUMMY_2.userDTO);
        list.add(UserData.USER_DUMMY_3.userDTO);
        return list;
    }

*/













    public UserDTO getDummyUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFullName("TestUser");
        userDTO.setAddress("TestAddress");
        userDTO.setEmailAddress("TestEmail");
        userDTO.setAssignedUserSites(Arrays.asList(1));
        return userDTO;
    }

    public UserDTO getDummyUserDTOOne(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFullName("TestUserOne");
        userDTO.setAddress("TestAddressOne");
        userDTO.setEmailAddress("TestEmailOne");
        userDTO.setAssignedUserSites(Arrays.asList(1));
        return userDTO;
    }

    public UserDTO getDummyUserDTOTwo(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFullName("TestUserTwo");
        userDTO.setAddress("TestAddressTwo");
        userDTO.setEmailAddress("TestEmailTwo");
        userDTO.setAssignedUserSites(Arrays.asList(1));
        return userDTO;
    }

    public UserDTO getDummyUserDTOThree(){
        UserDTO userDTO = new UserDTO();
        userDTO.setFullName("TestUserThree");
        userDTO.setAddress("TestAddressThree");
        userDTO.setEmailAddress("TestEmailThree");
        userDTO.setAssignedUserSites(Arrays.asList(1));
        return userDTO;
    }






    public User getDummyUser(){
        User user= new User();
        user.setFullName("TestDomainUser");
        user.setAddress("TestDomainUserAddress");
        user.setEmailAddress("TestDomainUserEmail");
        return user;
    }





    //Todo: remove this from here
    public  Asset getDummyAsset(){
        Asset asset = new Asset();
        asset.setId (1);
        asset.setVersion (1);
        asset.setAddress("TestAssetAddress");
        return asset;
    }



}
