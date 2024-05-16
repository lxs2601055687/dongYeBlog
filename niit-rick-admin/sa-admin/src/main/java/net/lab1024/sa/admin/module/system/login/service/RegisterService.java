package net.lab1024.sa.admin.module.system.login.service;

import cn.bitoffer.api.dto.lottery.UserInfoParam;
import net.lab1024.sa.admin.module.system.employee.domain.form.EmployeeUpdateForm;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.login.VO.LoginResultVO;
import net.lab1024.sa.admin.module.system.login.domain.RegisterForm;
import net.lab1024.sa.admin.module.system.login.feign.LotterylocalClient;
import net.lab1024.sa.base.common.code.UserErrorCode;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RegisterService {

    @Autowired
    private LotterylocalClient lotterylocalClient;
    @Autowired
    private EmployeeService employeeService;

    public ResponseDTO<LoginResultVO> register(RegisterForm registerForm, String ip, String userAgent) {
        //注册方法就是调用添加员工的方法
        EmployeeUpdateForm employeeAddForm = new EmployeeUpdateForm();
        employeeAddForm.setActualName(registerForm.getLoginName());
        employeeAddForm.setLoginName(registerForm.getLoginName());
        employeeAddForm.setDepartmentId(6L);
        employeeAddForm.setPhone(registerForm.getPhone());
        employeeAddForm.setPhone(registerForm.getLoginName());
        employeeAddForm.setDisabledFlag(false);
        employeeAddForm.setRoleIdList(Arrays.asList(58L));
        ResponseDTO<Long> longResponseDTO = employeeService.addCuser(employeeAddForm, registerForm);

        if(longResponseDTO.getOk()){
            UserInfoParam userInfoParam = new UserInfoParam();
            userInfoParam.setUserName(registerForm.getLoginName());
            userInfoParam.setEmployeeId(Math.toIntExact(longResponseDTO.getData()));
            lotterylocalClient.updateOrInsertUserInfo(userInfoParam);
            return ResponseDTO.ok();
        }
        return ResponseDTO.error(UserErrorCode.PARAM_ERROR, "注册失败");
    }
}
