struct UserRegisterDTO{
    1:required string companyName
    2:optional string account,
    3:optional string phoneNumber,
    4:optional string email,
    5:optional string password,
    6:optional string wechat,
    7:optional string qq,
    8:required i32 bizType,
    9:required i64 bizId
}

struct UserLoginDTO{
    1:required string companyName
    2:required string loginId,
    3:required string password,
    4:required i32 loginType,
}

struct UserResponse{
    1:required i32 code,
    2:optional string message
}

service UserBaseService{
    UserResponse userRegister(1:UserRegisterDTO userRegisterDTO);
    UserResponse userLogin(1:UserLoginDTO userLoginDTO);
    UserResponse userLogout(1:string companyName, 2:string account);
    i32 checkLoginType(1:string companyName, 2:string account);
    i32 checkOnline(1:string companyName, 2:string account);
}