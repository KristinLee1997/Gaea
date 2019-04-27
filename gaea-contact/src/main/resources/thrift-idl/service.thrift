namespace java com.aries.user.gaea.contact.service
include "model.thrift"

service UserBaseService{
    model.UserResponse userRegister(1:model.UserRegisterDTO userRegisterDTO);
    model.UserResponse userLogin(1:model.UserLoginDTO userLoginDTO);
    model.UserResponse userLogout(1:string companyName, 2:string loginId);
    i32 checkLoginType(1:string companyName, 2:string loginId);
    i32 checkOnline(1:string companyName, 2:string loginId);
}

service CompanyBaseService{
    string ping();
    model.CompanyResponse companyRegister(1:model.CompanyRegisterDTO companyRegisterDTO),
    model.CompanyResponse getRegisterNO(1:model.CompanyRegisterDTO companyRegisterDTO)
}
