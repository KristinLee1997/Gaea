namespace java com.aries.user.gaea.contact.service
include "model.thrift"

service UserBaseService{
    model.ThriftResponse userRegister(1:model.CompanyDTO companyDTO, 2:model.UserRegisterDTO userRegisterDTO);
    model.ThriftResponse userLogin(1:model.CompanyDTO companyDTO, 2:model.UserLoginDTO userLoginDTO);
    model.ThriftResponse userLogout(1:model.CompanyDTO companyDTO, 2:string loginId);
    model.ThriftResponse checkLoginType(1:model.CompanyDTO companyDTO, 2:string loginId);
    model.ThriftResponse checkOnline(1:model.CompanyDTO companyDTO, 2:string loginId);
    model.ThriftResponse getUserInfoById(1:model.CompanyDTO companyDTO, 2:i64 id);
    model.ThriftResponse getUserInfoByCookie(1:model.CompanyDTO companyDTO, 2:string cookie);
    model.UserInfoResponse getUserInfoByIdList(1:model.CompanyDTO companyDTO, 2:list<i64> idList);
}

service CompanyBaseService{
    string ping();
    model.ThriftResponse companyRegister(1:model.CompanyDTO companyRegisterDTO),
    model.ThriftResponse getRegisterNO(1:model.CompanyDTO companyRegisterDTO)
}
