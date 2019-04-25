struct CompanyRegisterDTO{
    1:required string name,
    2:required string password
}

struct CompanyResponse{
    1:required i32 code,
    2:required string message,
    3:required string data
}

service CompanyBaseService{
    CompanyResponse companyRegister(1:CompanyRegisterDTO companyRegisterDTO),
    CompanyResponse getRegisterNO(1:CompanyRegisterDTO companyRegisterDTO)
}

