namespace java com.aries.user.gaea.contact.model

struct CompanyDTO{
    1:required string name,
    2:required string password
}

struct ThriftResponse{
    1:required i32 code,
    2:required string message='',
    3:optional string data
}

struct UserRegisterDTO{
    1:optional string account,
    2:optional string phoneNumber,
    3:optional string email,
    4:optional string password,
    5:optional string wechat,
    6:optional string qq,
    7:required i32 bizType,
    8:optional binary image,
    9:optional string nickname,
}

struct UserLoginDTO{
    1:required string loginId,
    2:optional string password,
    3:required i32 loginType,
}